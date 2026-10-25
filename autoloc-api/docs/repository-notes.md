# Note — Atelier 3

## Choix d'interface

Les neuf repositories étendent `JpaRepository<Entité, Long>` : le type de la clé primaire est `Long` pour toutes les entités. `JpaRepository` réunit le CRUD, les listes, le tri, la pagination et `flush`. Spring Data génère l'implémentation à l'exécution, donc aucune classe ni `@Repository` n'est nécessaire.

| Interface | Étend | Justification |
|---|---|---|
| IContratRepository | `JpaRepository<Contrat, Long>` | CRUD complet, `findAll` renvoie une `List`, `saveAndFlush` disponible. Les suppressions passent par le contexte de persistance, donc la cascade et `orphanRemoval` s'appliquent aux paiements. |
| IPaiementRepository | `JpaRepository<Paiement, Long>` | Permet de lire les paiements. Le code métier crée ou retire un paiement via le Contrat. |
| IAgenceRepository | `JpaRepository<Agence, Long>` | CRUD complet avec `List`. |
| IEmployeRepository | `JpaRepository<Employe, Long>` | CRUD complet avec `List`. |
| IVehiculeRepository | `JpaRepository<Vehicule, Long>` | CRUD complet avec `List`, tri et pagination utiles pour la flotte. |
| IEquipementRepository | `JpaRepository<Equipement, Long>` | CRUD complet avec `List`. |
| IClientRepository | `JpaRepository<Client, Long>` | CRUD complet avec `List`, tri et pagination. |
| IReservationRepository | `JpaRepository<Reservation, Long>` | CRUD complet avec `List`, tri et pagination. |
| IMaintenanceRepository | `JpaRepository<Maintenance, Long>` | CRUD complet avec `List`. |

Au démarrage, les logs affichent `Found 9 JPA repository interfaces`.

## Anomalies SonarQube for IDE

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| Import inutilisé `java.util.List` dans `IAgenceRepository` | S1128 : un import inutile nuit à la lisibilité et à la maintenance du code | Import supprimé |
| Attribut `Ilbella` dans `Equipement` | S116 : un nom de champ doit respecter la convention camelCase (minuscule au début) | Renommé en `libelle` |
| `@Column(length = 255)` dans `Maintenance` | Valeur de paramètre redondante : 255 est déjà la longueur par défaut d'un `String` | Annotation supprimée |