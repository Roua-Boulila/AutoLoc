# Note — Atelier 4

## A. Modes d'injection comparés

| Critère | Constructeur | Attribut |
|---|---|---|
| Champ `final` possible | Oui | Non |
| Dépendance visible dans la signature de la classe | Oui | Non |
| Utilisable avec `new` hors conteneur sans dépendance `null` | Oui, le constructeur impose le repository | Non, le champ reste `null` |
| Signalé par SonarQube for IDE | Non | Oui (règle S6813) |

## B. Services

| Service | Dépendances injectées | Mode | Justification |
|---|---|---|---|
| ContratServiceImpl | IContratRepository | Constructeur | Dépendance obligatoire, champ `final`, typée par interface ; refuse un montant négatif. |
| AgenceServiceImpl | IAgenceRepository | Constructeur | Même convention ; refuse un nom, une ville ou une adresse vide. |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Même convention ; refuse un nom, un prénom vide ou un rôle absent. |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | Même convention ; refuse une immatriculation vide ou un tarif journalier négatif. |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Même convention ; refuse un libellé vide. |
| ClientServiceImpl | IClientRepository | Constructeur | Même convention ; refuse un nom ou un e-mail vide. |
| ReservationServiceImpl | IReservationRepository | Constructeur | Même convention ; refuse une date de fin antérieure à la date de début. |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Lecture seule (`findById`, `findAll`) : les paiements passent par le Contrat. |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Même convention ; refuse une date de fin antérieure à la date de début. |

## C. Messages d'erreur

**Message 1** — `NoSuchBeanDefinitionException` (dans une `UnsatisfiedDependencyException`) : `ContratController` demande un bean `IContratService` introuvable. Causes probables côté `ContratServiceImpl` : l'annotation `@Service` est oubliée, ou la classe est hors du package `tn.esprit.autoloc` (le scan ne la détecte pas). Correction : ajouter `@Service` ou déplacer la classe dans le bon package.

**Message 2** — `NoUniqueBeanDefinitionException` : `NotificationController` demande un bean et deux existent (`emailNotificateur`, `smsNotificateur`). Correction 1 : `@Primary` sur la classe du bean choisi par défaut. Correction 2 : `@Qualifier("emailNotificateur")` sur le paramètre du constructeur du contrôleur.

**Message 3** — `BeanCurrentlyInCreationException` : `clientServiceImpl` et `reservationServiceImpl` se demandent mutuellement. Avec l'injection par constructeur, chaque bean doit recevoir l'autre au moment de sa construction, donc aucun ne peut être créé en premier et le cycle ne peut pas être contourné. Piste : extraire la responsabilité commune dans un troisième service dont dépendent les deux autres.

## D. Anomalies SonarQube for IDE

| Anomalie | Règle | Explication | Correction |
|---|---|---|---|
| Injection par attribut dans `ContratServiceImpl` | S6813 | Dépendance cachée, champ non `final`, `null` hors conteneur | Retour à `@RequiredArgsConstructor` et au champ `final` |
| `RuntimeException` dans `findById` | S112 | Exception générique : la cause n'est pas explicite | Retour à `ResourceNotFoundException` |
| `@Column(length = 255)` dans `Maintenance` | Redundant default parameter value assignment | 255 est déjà la longueur par défaut d'un `String` : code inutile | Annotation supprimée |

## E. Questions de compréhension

1. **Où est le `new` de `ContratServiceImpl` et qui l'exécute ?** Il n'est écrit nulle part : le conteneur Spring l'exécute au démarrage, après avoir détecté la classe grâce à `@Service` et au scan de `tn.esprit.autoloc`, et lui fournit le proxy de `IContratRepository`.
2. **Pourquoi un contrôleur dépendra-t-il de `IContratService` et non de `ContratServiceImpl` ?** Pour dépendre d'une abstraction : on peut changer l'implémentation, ou fournir un faux service, sans modifier le contrôleur.
3. **Pourquoi un service singleton doit-il rester sans état ?** Une seule instance est partagée par tous les threads. Exemple de bug : avec un attribut « contrat courant », la requête de l'utilisateur A est écrasée par celle de B, et A enregistre le contrat de B.
4. **Pourquoi `update` charge-t-il l'entité existante avant `save` ?** Passer l'objet reçu directement écraserait toutes les colonnes (les champs absents deviendraient `null`) et, avec `orphanRemoval`, pourrait vider la collection de paiements.
5. **Différences.** `@Component` se pose sur une classe détectée par le scan ; `@Bean` se pose sur une méthode de `@Configuration` pour déclarer un bean qu'on ne peut pas annoter (bibliothèque, JDK). `@Primary` se pose sur la classe et désigne le candidat par défaut ; `@Qualifier` se pose à l'injection et choisit un bean par son 
6. . `IPaiementService` n'expose ni création, ni modification, ni suppression car un paiement n'existe que rattaché à son contrat : ces opérations passent par le Contrat (cascade `ALL` et `orphanRemoval`).
