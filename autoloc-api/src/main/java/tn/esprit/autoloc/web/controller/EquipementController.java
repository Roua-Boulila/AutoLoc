package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import tn.esprit.autoloc.service.IEquipementService;
import tn.esprit.autoloc.web.dto.EquipementDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
@Tag(
        name = "Équipements",
        description = "Gestion des équipements AutoLoc (créer, consulter, modifier et supprimer des équipements)"
)
public class EquipementController {

    private final IEquipementService equipementService;

    // Ajouter un équipement
    @PostMapping
    @Operation(summary = "Créer un équipement")
    @ApiResponse(responseCode = "201", description = "Équipement créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<EquipementDTO> ajouter(
            @Valid @RequestBody EquipementDTO equipementDTO) {

        EquipementDTO nouvelEquipement = equipementService.create(equipementDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nouvelEquipement);
    }

    // Afficher tous les équipements
    @GetMapping
    @Operation(summary = "Lister les équipements")
    @ApiResponse(responseCode = "200", description = "Liste des équipements")
    public ResponseEntity<List<EquipementDTO>> afficherTous() {

        return ResponseEntity.ok(equipementService.findAll());
    }

    // Afficher un équipement par ID
    @GetMapping("/{id}")
    @Operation(summary = "Consulter un équipement")
    @ApiResponse(responseCode = "200", description = "Équipement trouvé")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    public ResponseEntity<EquipementDTO> afficher(
            @Parameter(description = "Identifiant de l'équipement")
            @PathVariable Long id) {

        return ResponseEntity.ok(equipementService.findById(id));
    }

    // Modifier un équipement
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un équipement")
    @ApiResponse(responseCode = "200", description = "Équipement modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    public ResponseEntity<EquipementDTO> modifier(
            @PathVariable Long id,
            @Valid @RequestBody EquipementDTO equipementDTO) {

        EquipementDTO equipementModifie =
                equipementService.update(id, equipementDTO);

        return ResponseEntity.ok(equipementModifie);
    }

    // Supprimer un équipement
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un équipement")
    @ApiResponse(responseCode = "204", description = "Équipement supprimé")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        equipementService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}