package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.web.dto.ReservationDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(
        name = "Réservations",
        description = "Gestion des réservations AutoLoc (créer, consulter, modifier et supprimer des réservations)"
)
public class ReservationController {

    private final IReservationService reservationService;

    // Créer une réservation
    @PostMapping
    @Operation(summary = "Créer une réservation")
    @ApiResponse(responseCode = "201", description = "Réservation créée")
    @ApiResponse(responseCode = "400", description = "Dates ou données invalides")
    public ResponseEntity<ReservationDTO> ajouter(
            @Valid @RequestBody ReservationDTO reservationDTO) {

        ReservationDTO nouvelleReservation =
                reservationService.create(reservationDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nouvelleReservation);
    }

    // Afficher toutes les réservations
    @GetMapping
    @Operation(summary = "Lister les réservations")
    @ApiResponse(responseCode = "200", description = "Liste des réservations")
    public ResponseEntity<List<ReservationDTO>> afficherToutes() {

        return ResponseEntity.ok(reservationService.findAll());
    }

    // Afficher une réservation par ID
    @GetMapping("/{id}")
    @Operation(summary = "Consulter une réservation")
    @ApiResponse(responseCode = "200", description = "Réservation trouvée")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    public ResponseEntity<ReservationDTO> afficher(
            @Parameter(description = "Identifiant de la réservation")
            @PathVariable Long id) {

        return ResponseEntity.ok(reservationService.findById(id));
    }

    // Modifier une réservation
    @PutMapping("/{id}")
    @Operation(summary = "Modifier une réservation")
    @ApiResponse(responseCode = "200", description = "Réservation modifiée")
    @ApiResponse(responseCode = "400", description = "Dates ou données invalides")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    public ResponseEntity<ReservationDTO> modifier(
            @PathVariable Long id,
            @Valid @RequestBody ReservationDTO reservationDTO) {

        ReservationDTO reservationModifiee =
                reservationService.update(id, reservationDTO);

        return ResponseEntity.ok(reservationModifiee);
    }

    // Supprimer une réservation
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une réservation")
    @ApiResponse(responseCode = "204", description = "Réservation supprimée")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        reservationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}