# Stratégie de fetch et de cascade — AutoLoc

Atelier 2 — Associations JPA : Cascade et Fetch

## 1. Principes retenus

- **Fetch LAZY partout.** Par défaut, `@ManyToOne` et `@OneToOne` sont en EAGER : charger une entité chargerait toute la chaîne d'objets liés. On force donc `LAZY` pour ne charger une association que lorsque le code y accède. Les collections (`@OneToMany`, `@ManyToMany`) sont déjà LAZY par défaut ; on le déclare explicitement pour la lisibilité.
- **Cascade seulement en cas de composition.** Une cascade n'est justifiée que si l'enfant ne peut pas vivre sans son parent. Dans les autres cas, on évite `REMOVE` pour ne pas supprimer des données métier par effet de bord.
- **Côté propriétaire** : toujours le côté `@ManyToOne` (porteur de la clé étrangère). Le côté inverse utilise `mappedBy` et ne crée aucune colonne.
- **Lombok ciblé** (`@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`), sans `@Data`, pour éviter les boucles infinies de `toString` / `hashCode` sur les associations bidirectionnelles.

## 2. Tableau récapitulatif

| Association | Annotations | Côté propriétaire (clé étrangère) | Fetch | Cascade | Justification |
|---|---|---|---|---|---|
| Contrat → Paiement | `@OneToMany` / `@ManyToOne` | Paiement (`contrat_id_contrat`) | LAZY | ALL + `orphanRemoval = true` | Un paiement n'existe que rattaché à son contrat (composition). Supprimer le contrat supprime ses paiements, et retirer un paiement de la liste le supprime en base. |
| Agence → Vehicule | `@OneToMany` / `@ManyToOne` | Vehicule (`agence_id_agence`) | LAZY | Aucune | Un véhicule survit à la suppression de son agence : il fait partie de la flotte et peut être réaffecté. |
| Agence → Employe | `@OneToMany` / `@ManyToOne` | Employe (`agence_id_agence`) | LAZY | Aucune | Supprimer une agence ne doit pas supprimer ses employés automatiquement : ils doivent être réaffectés ou traités explicitement. |
| Vehicule ↔ Equipement | `@ManyToMany` | Vehicule (table de jointure `vehicule_equipement`) | LAZY | Aucune | Les équipements sont partagés entre plusieurs véhicules : supprimer un véhicule ne doit pas supprimer un équipement utilisé ailleurs. `Set` pour interdire les doublons et limiter les réécritures de la table de jointure. |
| Client → Reservation | `@OneToMany` / `@ManyToOne` | Reservation (`client_id_client`) | LAZY | PERSIST | Enregistrer un client avec ses réservations les enregistre aussi. Pas de REMOVE : l'historique des réservations (et les contrats et paiements liés) doit être conservé. |
| Reservation → Vehicule | `@ManyToOne` | Reservation (`vehicule_id_vehicule`) | LAZY | Aucune | Un véhicule existe indépendamment de ses réservations. Association unidirectionnelle : les réservations d'un véhicule s'obtiennent par requête. |
| Reservation ↔ Contrat | `@OneToOne` | Contrat (`reservation_id_reservation`) | LAZY | ALL côté Reservation | Un contrat n'a pas de sens sans sa réservation. La cascade est sur le parent (Reservation). Une contrainte `unique` garantit un seul contrat par réservation. |
| Vehicule → Maintenance | `@OneToMany` / `@ManyToOne` | Maintenance (`vehicule_id_vehicule`) | LAZY | PERSIST | Enregistrer un véhicule avec ses maintenances les enregistre aussi. Pas de REMOVE : l'historique d'entretien est une trace à conserver. |

## 3. Points d'attention

- **LazyInitializationException** : accéder à une collection LAZY hors transaction lève cette exception. On la traitera avec des requêtes dédiées dans les ateliers suivants.
- **`@OneToOne` côté inverse** : sur le côté `mappedBy`, Hibernate doit vérifier l'existence de l'objet lié, donc le LAZY y est moins efficace que côté propriétaire (Contrat).
- **ddl-auto=update** : adapté au développement uniquement. Hibernate ajoute des colonnes mais ne supprime jamais celles d'une ancienne configuration ; en cas de mapping erroné, il faut supprimer la table à la main avant de relancer.
