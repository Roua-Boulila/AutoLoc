# Notes Atelier 5 : API REST AutoLoc

Spring MVC, Postman et SpringDoc/Swagger. Base de l'API : `http://localhost:8080/api` (`server.servlet.context-path=/api`).
Swagger UI : `/api/swagger-ui.html` ; description OpenAPI : `/api/v3/api-docs`.

## A. Endpoints

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs |
|---|---|---|---|---|
| AgenceController | GET /api/agences | agenceService.findAll() | 200 | — |
| AgenceController | GET /api/agences/{id} | agenceService.findById(id) | 200 | 404 |
| AgenceController | POST /api/agences | agenceService.create(agence) | 201 | 400 |
| AgenceController | PUT /api/agences/{id} | agenceService.update(id, agence) | 200 | 404, 400 |
| AgenceController | DELETE /api/agences/{id} | agenceService.deleteById(id) | 204 | 404 |
| EmployeController | GET /api/employes | employeService.findAll() | 200 | — |
| EmployeController | GET /api/employes/{id} | employeService.findById(id) | 200 | 404 |
| EmployeController | POST /api/employes | employeService.create(employe) | 201 | 400 |
| EmployeController | PUT /api/employes/{id} | employeService.update(id, employe) | 200 | 404, 400 |
| EmployeController | DELETE /api/employes/{id} | employeService.deleteById(id) | 204 | 404 |
| VehiculeController | GET /api/vehicules | vehiculeService.findAll() | 200 | — |
| VehiculeController | GET /api/vehicules/{id} | vehiculeService.findById(id) | 200 | 404 |
| VehiculeController | POST /api/vehicules | vehiculeService.create(vehicule) | 201 | 400 |
| VehiculeController | PUT /api/vehicules/{id} | vehiculeService.update(id, vehicule) | 200 | 404, 400 |
| VehiculeController | DELETE /api/vehicules/{id} | vehiculeService.deleteById(id) | 204 | 404 |
| EquipementController | GET /api/equipements | equipementService.findAll() | 200 | — |
| EquipementController | GET /api/equipements/{id} | equipementService.findById(id) | 200 | 404 |
| EquipementController | POST /api/equipements | equipementService.create(equipement) | 201 | 400 |
| EquipementController | PUT /api/equipements/{id} | equipementService.update(id, equipement) | 200 | 404, 400 |
| EquipementController | DELETE /api/equipements/{id} | equipementService.deleteById(id) | 204 | 404 |
| ClientController | GET /api/clients | clientService.findAll() | 200 | — |
| ClientController | GET /api/clients/{id} | clientService.findById(id) | 200 | 404 |
| ClientController | POST /api/clients | clientService.create(client) | 201 | 400 |
| ClientController | PUT /api/clients/{id} | clientService.update(id, client) | 200 | 404, 400 |
| ClientController | DELETE /api/clients/{id} | clientService.deleteById(id) | 204 | 404 |
| ReservationController | GET /api/reservations | reservationService.findAll() | 200 | — |
| ReservationController | GET /api/reservations/{id} | reservationService.findById(id) | 200 | 404 |
| ReservationController | POST /api/reservations | reservationService.create(reservation) | 201 | 400 |
| ReservationController | PUT /api/reservations/{id} | reservationService.update(id, reservation) | 200 | 404, 400 |
| ReservationController | DELETE /api/reservations/{id} | reservationService.deleteById(id) | 204 | 404 |
| ContratController | GET /api/contrats | contratService.findAll() | 200 | — |
| ContratController | GET /api/contrats/{id} | contratService.findById(id) | 200 | 404 |
| ContratController | POST /api/contrats | contratService.create(contrat) | 201 | 400 |
| ContratController | PUT /api/contrats/{id} | contratService.update(id, contrat) | 200 | 404, 400 |
| ContratController | DELETE /api/contrats/{id} | contratService.deleteById(id) | 204 | 404 |
| PaiementController | GET /api/paiements | paiementService.findAll() | 200 | — |
| PaiementController | GET /api/paiements/{id} | paiementService.findById(id) | 200 | 404 |
| PaiementController | POST /api/paiements | (aucun : non exposé) | — | 405 |
| MaintenanceController | GET /api/maintenances | maintenanceService.findAll() | 200 | — |
| MaintenanceController | GET /api/maintenances/{id} | maintenanceService.findById(id) | 200 | 404 |
| MaintenanceController | POST /api/maintenances | maintenanceService.create(maintenance) | 201 | 400 |
| MaintenanceController | PUT /api/maintenances/{id} | maintenanceService.update(id, maintenance) | 200 | 404, 400 |
| MaintenanceController | DELETE /api/maintenances/{id} | maintenanceService.deleteById(id) | 204 | 404 |

Les 404 viennent de `ResourceNotFoundException` et les 400 de `IllegalArgumentException`, traduites par le `GlobalExceptionHandler` : les contrôleurs ne produisent aucune erreur eux-mêmes. `PaiementController` est en lecture seule (le paiement est porté par le contrat).

## B. Annotations Jackson

| Association | Annotation retenue | Justification |
|---|---|---|
| `Contrat.paiements` / `Paiement.contrat` | `@JsonManagedReference("contrat-paiements")` côté liste, `@JsonBackReference("contrat-paiements")` côté parent | Composition (`cascade = ALL`) : les paiements se créent dans le JSON de leur contrat. |
| `Agence.employes`, `Agence.vehicules` | `@JsonIgnore` sur la liste | Employés et véhicules se créent seuls et désignent leur agence par un identifiant (`"agence": {"idAgence": 1}`). |
| `Reservation.contrat` | `@JsonIgnore` | Évite la boucle Reservation ↔ Contrat (relation `@OneToOne`). |
| `Vehicule.maintenances` | `@JsonIgnore` | La maintenance désigne son véhicule par un identifiant ; la liste inverse est ignorée. |
| `Reservation.client`, `Reservation.vehicule`, `Employe.agence`, `Maintenance.vehicule` | Aucune | Côté propriétaire : une annotation ignorerait la propriété et créerait des enregistrements sans lien. |

**Différence entre `@JsonBackReference` et `@JsonIgnore`.** `@JsonBackReference` fait partie d'une paire : la propriété est omise à la sérialisation, mais Jackson reconstruit le lien à la désérialisation grâce au `@JsonManagedReference` du même nom (le paiement retrouve son contrat). `@JsonIgnore` ignore simplement la propriété dans les deux sens, sans reconstruire de lien. On l'utilise donc sur la liste inverse d'un enfant créé seul, et jamais sur l'attribut propriétaire.

## C. Postman et Swagger

- **Postman** sert à **tester** : requêtes rejouables, scripts de vérification (`pm.test`), variables d'environnement (`baseUrl`, `contratId`...). Exemple rencontré : le `POST /api/reservations` a renvoyé un 500 (`foreign key constraint fails`) car `"client": {"idClient": 1}` désignait un client inexistant ; Postman a permis de le constater, de relever les vrais identifiants avec les requêtes « Lister » et de corriger le corps.
- **Swagger UI** sert à **documenter et explorer** : le contrat de l'API est généré à partir du code (`@Tag`, `@Operation`, `@ApiResponse`) et reste synchronisé avec lui. Exemple : la section Schemas décrit les entités, et « Try it out » permet d'essayer un endpoint sans écrire de requête.
- Les deux sont complémentaires : Postman vérifie le comportement, Swagger décrit l'API pour ses utilisateurs.

## D. Anomalies SonarQube

Analyse pré-commit avec SonarQube for IDE (22 fichiers, 51 anomalies détectées).

| Règle | Explication | Correction |
|---|---|---|
| java:S1710 (9 contrôleurs, 33 occurrences) | `@ApiResponse` est une annotation répétable : le wrapper `@ApiResponses` est inutile. | `@ApiResponse` placées directement sur chaque méthode, import `ApiResponses` supprimé. |
| Fuseau horaire manquant (`GlobalExceptionHandler`) | `LocalDateTime.now()` dépend du fuseau de la machine : l'horodatage des erreurs varie selon l'environnement. | `LocalDateTime.now(ZoneId.of("Africa/Tunis"))`. |
| java:S1128 (`Maintenance.java`) | Import inutile de `JsonIgnore` : il alourdit le code et n'est pas utilisé dans cette classe. | Import supprimé. |
| java:S4684 (contrôleurs, 16 occurrences) | Une entité JPA est utilisée comme paramètre ou retour d'un endpoint : couplage API/base, champs internes exposés. | Conservée volontairement à ce stade ; remplacée par des DTO au chapitre 10. |
## E. Questions

1. **Quel composant reçoit les requêtes ?** Le `DispatcherServlet`, point d'entrée unique. Le `HandlerMapping` retrouve le contrôleur et la méthode à appeler à partir du verbe HTTP et de l'URL.
2. **Pourquoi dépendre de `IContratService` ?** Le contrôleur reste mince : il délègue au service sans logique métier ni accès aux données. Dépendre de l'interface (et non de `ContratServiceImpl` ou du repository) respecte les couches et permet de remplacer l'implémentation sans toucher au contrôleur.
3. **Pourquoi 201, 204 et 404 ?** Un POST crée une ressource : 201 (Created). Un DELETE réussi n'a rien à renvoyer : 204 (No Content). Une ressource absente est une erreur du client (identifiant inconnu) et non une panne du serveur : `ResourceNotFoundException` est traduite en 404 par le `GlobalExceptionHandler`, alors qu'une exception non gérée donnerait un 500.
4. **Pourquoi `PaiementController` n'expose que des GET ?** Le paiement est porté par le contrat (`cascade = ALL`) : on le crée, modifie et supprime via son contrat. Un `POST /api/paiements` reçoit un **405 Method Not Allowed**, car aucune méthode n'est mappée sur ce verbe.
5. **Risques de l'exposition des entités JPA ?** Couplage entre l'API et le modèle de base, champs internes exposés, boucles de sérialisation entre associations bidirectionnelles, proxies LAZY non sérialisables (`ByteBuddyInterceptor`) et création possible de données invalides. `Contrat.paiements` utilise `@JsonManagedReference` car c'est une composition : les paiements naissent dans le JSON du contrat. `Agence.vehicules` utilise `@JsonIgnore` car les véhicules se créent seuls et désignent leur agence par un identifiant.