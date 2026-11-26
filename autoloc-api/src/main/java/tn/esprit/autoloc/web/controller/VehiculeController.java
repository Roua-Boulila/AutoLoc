package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(
        name = "Véhicules",
        description = "Gestion des véhicules AutoLoc (créer, consulter, modifier et supprimer des véhicules)"
)
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    // Créer un véhicule
    @PostMapping
    @Operation(summary = "Créer un véhicule")
    @ApiResponse(responseCode = "201", description = "Véhicule créé")
    @ApiResponse(responseCode = "400", description = "Valeur numérique invalide")
    public ResponseEntity<VehiculeDTO> ajouter(
            @Valid @RequestBody VehiculeDTO vehiculeDTO) {

        VehiculeDTO nouveauVehicule = vehiculeService.create(vehiculeDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nouveauVehicule);
    }

    // Afficher tous les véhicules
    @GetMapping
    @Operation(summary = "Lister les véhicules")
    @ApiResponse(responseCode = "200", description = "Liste des véhicules")
    public ResponseEntity<List<VehiculeDTO>> afficherTous() {

        return ResponseEntity.ok(vehiculeService.findAll());
    }

    // Afficher un véhicule par ID
    @GetMapping("/{id}")
    @Operation(summary = "Consulter un véhicule")
    @ApiResponse(responseCode = "200", description = "Véhicule trouvé")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    public ResponseEntity<VehiculeDTO> afficher(
            @Parameter(description = "Identifiant du véhicule")
            @PathVariable Long id) {

        return ResponseEntity.ok(vehiculeService.findById(id));
    }

    // Modifier un véhicule
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un véhicule")
    @ApiResponse(responseCode = "200", description = "Véhicule modifié")
    @ApiResponse(responseCode = "400", description = "Valeur numérique invalide")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    public ResponseEntity<VehiculeDTO> modifier(
            @PathVariable Long id,
            @Valid @RequestBody VehiculeDTO vehiculeDTO) {

        VehiculeDTO vehiculeModifie =
                vehiculeService.update(id, vehiculeDTO);

        return ResponseEntity.ok(vehiculeModifie);
    }

    // Supprimer un véhicule
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un véhicule")
    @ApiResponse(responseCode = "204", description = "Véhicule supprimé")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        vehiculeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{idVehicule}/agence/{idAgence}")
    @Operation(
            summary = "Affecter un véhicule à une agence",
            description = "Permet d'associer un véhicule existant à une agence existante. "
    )
    @ApiResponse(responseCode = "200", description = "véhicule affecté a une agence avec succès")
    @ApiResponse(responseCode = "400", description = "Identifiants invalides")
    @ApiResponse(responseCode = "404", description = "Véhicule ou agence introuvable")
    @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    public ResponseEntity<VehiculeDTO> affecterAgence(
            @PathVariable Long idVehicule,
            @PathVariable Long idAgence) {

        return ResponseEntity.ok(
                vehiculeService.affecterVehiculeAAgence(
                        idVehicule, idAgence));
    }

    @PutMapping("/{idVehicule}/equipement/{idEquipement}")
    @Operation(
            summary = "Affecter un équipement à un véhicule",
            description = "Permet d'associer un équipement existant à un véhicule existant. "
                    + "L'association sera enregistrée dans la table de jointure entre vehicule et equipement."
    )
    @ApiResponse(responseCode = "200", description = "Équipement affecté au véhicule avec succès")
    @ApiResponse(responseCode = "400", description = "Identifiants invalides")
    @ApiResponse(responseCode = "404", description = "Véhicule ou équipement introuvable")
    @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    public ResponseEntity<VehiculeDTO> affecterEquipement(
            @PathVariable Long idVehicule,
            @PathVariable Long idEquipement) {

        return ResponseEntity.ok(
                vehiculeService.affecterEquipementAVehicule(
                        idVehicule, idEquipement));
    }

    @GetMapping("/contrat/{idContrat}")
    @Operation(summary = "Lister les véhicules d'un contrat")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    public ResponseEntity<List<VehiculeDTO>> afficherParContrat(
            @Parameter(description = "Identifiant du contrat")
            @PathVariable Long idContrat) {

        return ResponseEntity.ok(vehiculeService.findByContratId(idContrat));
    }

    @GetMapping("/maintenance/{idMaintenance}")
    @Operation(summary = "Lister les véhicules d'une maintenance")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    public ResponseEntity<List<VehiculeDTO>> afficherParMaintenance(
            @Parameter(description = "Identifiant de la maintenance")
            @PathVariable Long idMaintenance) {

        return ResponseEntity.ok(vehiculeService.findByMaintenanceId(idMaintenance));
    }
}