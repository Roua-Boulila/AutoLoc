package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import tn.esprit.autoloc.service.IMaintenanceService;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
@Tag(
        name = "Maintenances",
        description = "Gestion des maintenances des véhicules (créer, consulter, modifier et supprimer des maintenances)"
)
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    // Créer une maintenance
    @PostMapping
    @Operation(summary = "Créer une maintenance")
    @ApiResponse(responseCode = "201", description = "Maintenance créée")
    @ApiResponse(responseCode = "400", description = "Dates ou données invalides")
    public ResponseEntity<MaintenanceDTO> ajouter(
            @Valid @RequestBody MaintenanceDTO maintenanceDTO) {

        MaintenanceDTO nouvelleMaintenance =
                maintenanceService.create(maintenanceDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nouvelleMaintenance);
    }

    // Afficher toutes les maintenances
    @GetMapping
    @Operation(summary = "Lister les maintenances")
    @ApiResponse(responseCode = "200", description = "Liste des maintenances")
    public ResponseEntity<List<MaintenanceDTO>> afficherToutes() {

        return ResponseEntity.ok(maintenanceService.findAll());
    }

    // Afficher une maintenance par ID
    @GetMapping("/{id}")
    @Operation(summary = "Consulter une maintenance")
    @ApiResponse(responseCode = "200", description = "Maintenance trouvée")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    public ResponseEntity<MaintenanceDTO> afficher(
            @Parameter(description = "Identifiant de la maintenance")
            @PathVariable Long id) {

        return ResponseEntity.ok(maintenanceService.findById(id));
    }

    // Modifier une maintenance
    @PutMapping("/{id}")
    @Operation(summary = "Modifier une maintenance")
    @ApiResponse(responseCode = "200", description = "Maintenance modifiée")
    @ApiResponse(responseCode = "400", description = "Dates ou données invalides")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    public ResponseEntity<MaintenanceDTO> modifier(
            @PathVariable Long id,
            @Valid @RequestBody MaintenanceDTO maintenanceDTO) {

        MaintenanceDTO maintenanceModifiee =
                maintenanceService.update(id, maintenanceDTO);

        return ResponseEntity.ok(maintenanceModifiee);
    }

    // Supprimer une maintenance
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une maintenance")
    @ApiResponse(responseCode = "204", description = "Maintenance supprimée")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        maintenanceService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idMaintenance}/vehicule/{idVehicule}")
    @Operation(
            summary = "Affecter une maintenance à un véhicule",
            description = "Permet d'associer une maintenance existante à un véhicule existant. "
                    + "La clé étrangère id_vehicule sera mise à jour dans la table maintenance."
    )
    @ApiResponse(responseCode = "200", description = "Maintenance affectée au véhicule avec succès")
    @ApiResponse(responseCode = "400", description = "Identifiants invalides")
    @ApiResponse(responseCode = "404", description = "Véhicule ou maintenance introuvable")
    @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    public ResponseEntity<MaintenanceDTO> affecterVehicule(
            @PathVariable Long idMaintenance,
            @PathVariable Long idVehicule) {

        return ResponseEntity.ok(
                maintenanceService.affecterMaintenanceAVehicule(
                        idMaintenance, idVehicule
                )
        );
    }

    @GetMapping("/vehicule/{idVehicule}")
    @Operation(summary = "Lister les maintenances d'un véhicule")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    public ResponseEntity<List<MaintenanceDTO>> afficherParVehicule(
            @Parameter(description = "Identifiant du véhicule")
            @PathVariable Long idVehicule) {

        return ResponseEntity.ok(maintenanceService.findByVehiculeId(idVehicule));
    }
}