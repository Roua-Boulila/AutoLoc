package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import tn.esprit.autoloc.service.IClientService;
import tn.esprit.autoloc.web.dto.ClientDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(
        name = "Clients",
        description = "Gestion des clients AutoLoc (créer, consulter, modifier et supprimer des clients)"
)
public class ClientController {

    private final IClientService clientService;

    // Ajouter un client
    @PostMapping
    @Operation(summary = "Créer un client")
    @ApiResponse(responseCode = "201", description = "Client créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<ClientDTO> ajouter(
            @Valid @RequestBody ClientDTO clientDTO) {

        ClientDTO nouveauClient = clientService.create(clientDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nouveauClient);
    }

    // Afficher tous les clients
    @GetMapping
    @Operation(summary = "Lister les clients")
    @ApiResponse(responseCode = "200", description = "Liste des clients")
    public ResponseEntity<List<ClientDTO>> afficherTous() {

        return ResponseEntity.ok(clientService.findAll());
    }

    // Afficher un client par ID
    @GetMapping("/{id}")
    @Operation(summary = "Consulter un client")
    @ApiResponse(responseCode = "200", description = "Client trouvé")
    @ApiResponse(responseCode = "404", description = "Client introuvable")
    public ResponseEntity<ClientDTO> afficher(
            @Parameter(description = "Identifiant du client")
            @PathVariable Long id) {

        return ResponseEntity.ok(clientService.findById(id));
    }

    // Modifier un client
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un client")
    @ApiResponse(responseCode = "200", description = "Client modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Client introuvable")
    public ResponseEntity<ClientDTO> modifier(
            @PathVariable Long id,
            @Valid @RequestBody ClientDTO clientDTO) {

        ClientDTO clientModifie = clientService.update(id, clientDTO);

        return ResponseEntity.ok(clientModifie);
    }

    // Supprimer un client
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un client")
    @ApiResponse(responseCode = "204", description = "Client supprimé")
    @ApiResponse(responseCode = "404", description = "Client introuvable")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        clientService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}