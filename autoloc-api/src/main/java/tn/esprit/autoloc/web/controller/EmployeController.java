package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import tn.esprit.autoloc.service.IEmployeService;
import tn.esprit.autoloc.web.dto.EmployeDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Tag(
        name = "Employés",
        description = "Gestion des employés AutoLoc (créer, consulter, modifier et supprimer des employés)"
)
public class EmployeController {

    private final IEmployeService employeService;

    // Ajouter un employé
    @PostMapping
    @Operation(summary = "Créer un employé")
    @ApiResponse(responseCode = "201", description = "Employé créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<EmployeDTO> ajouter(
            @Valid @RequestBody EmployeDTO employeDTO) {

        EmployeDTO nouvelEmploye = employeService.create(employeDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nouvelEmploye);
    }

    // Afficher tous les employés
    @GetMapping
    @Operation(summary = "Lister les employés")
    @ApiResponse(responseCode = "200", description = "Liste des employés")
    public ResponseEntity<List<EmployeDTO>> afficherTous() {

        return ResponseEntity.ok(employeService.findAll());
    }

    // Afficher un employé par ID
    @GetMapping("/{id}")
    @Operation(summary = "Consulter un employé")
    @ApiResponse(responseCode = "200", description = "Employé trouvé")
    @ApiResponse(responseCode = "404", description = "Employé introuvable")
    public ResponseEntity<EmployeDTO> afficher(
            @Parameter(description = "Identifiant de l'employé")
            @PathVariable Long id) {

        return ResponseEntity.ok(employeService.findById(id));
    }

    // Modifier un employé
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un employé")
    @ApiResponse(responseCode = "200", description = "Employé modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Employé introuvable")
    public ResponseEntity<EmployeDTO> modifier(
            @PathVariable Long id,
            @Valid @RequestBody EmployeDTO employeDTO) {

        EmployeDTO employeModifie = employeService.update(id, employeDTO);

        return ResponseEntity.ok(employeModifie);
    }

    // Supprimer un employé
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un employé")
    @ApiResponse(responseCode = "204", description = "Employé supprimé")
    @ApiResponse(responseCode = "404", description = "Employé introuvable")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        employeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}