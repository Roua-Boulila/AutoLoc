package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.service.IAffectationService;
import tn.esprit.autoloc.web.dto.AffectationEquipementRequestDTO;
import tn.esprit.autoloc.web.dto.AffectationMaintenanceRequestDTO;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

@RestController
@RequestMapping("/affectations")
@RequiredArgsConstructor
@Tag(name = "Affectations", description = "Affectation d'une maintenance à un véhicule et d'un équipement à un véhicule")
public class AffectationController {

    private final IAffectationService affectationService;

    @Operation(summary = "Affecter une maintenance à un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance affectée"),
            @ApiResponse(responseCode = "400", description = "Champs invalides ou maintenance déjà affectée à ce véhicule"),
            @ApiResponse(responseCode = "404", description = "Maintenance ou véhicule introuvable")
    })
    @PostMapping("/maintenance-vehicule")
    public MaintenanceDTO affecterMaintenanceAVehicule(
            @Valid @RequestBody AffectationMaintenanceRequestDTO demande) {
        return affectationService.affecterMaintenanceAVehicule(demande);
    }

    @Operation(summary = "Affecter un équipement à un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Équipement affecté"),
            @ApiResponse(responseCode = "400", description = "Champs invalides ou équipement déjà affecté à ce véhicule"),
            @ApiResponse(responseCode = "404", description = "Véhicule ou équipement introuvable")
    })
    @PostMapping("/vehicule-equipement")
    public VehiculeDTO affecterEquipementAVehicule(
            @Valid @RequestBody AffectationEquipementRequestDTO demande) {
        return affectationService.affecterEquipementAVehicule(demande);
    }
}
