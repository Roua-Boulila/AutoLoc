package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import tn.esprit.autoloc.service.IPaiementService;
import tn.esprit.autoloc.web.dto.PaiementDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
@Tag(
        name = "Paiements",
        description = "Consultation des paiements associés aux contrats"
)
public class PaiementController {

    private final IPaiementService paiementService;

    // Afficher tous les paiements
    @GetMapping
    @Operation(
            summary = "Lister les paiements",
            description = "Retourne tous les paiements enregistrés."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Liste des paiements récupérée avec succès"
    )
    public ResponseEntity<List<PaiementDTO>> afficherTous() {

        return ResponseEntity.ok(paiementService.findAll());
    }

    // Afficher un paiement par ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Consulter un paiement",
            description = "Recherche un paiement par son identifiant."
    )
    @ApiResponse(responseCode = "200", description = "Paiement trouvé")
    @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    public ResponseEntity<PaiementDTO> afficher(
            @Parameter(description = "Identifiant du paiement")
            @PathVariable Long id) {

        return ResponseEntity.ok(paiementService.findById(id));
    }
}