package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AffectationMaintenanceRequestDTO(
        @NotNull(message = "L'identifiant de la maintenance est obligatoire")
        @Positive(message = "L'identifiant de la maintenance doit être positif")
        Long idMaintenance,

        @NotNull(message = "L'identifiant du véhicule est obligatoire")
        @Positive(message = "L'identifiant du véhicule doit être positif")
        Long idVehicule) {
}