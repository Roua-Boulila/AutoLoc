package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContratDTO {
    private Long idContrat;
    @NotNull(message = "La date de signature est obligatoire")
    private java.time.LocalDate dateSignature;
    @NotNull(message = "Le montant total est obligatoire")
    @PositiveOrZero(message = "Le montant total ne peut pas être négatif")
    private java.math.BigDecimal montantTotal;
    @NotNull(message = "L'état de validation du contrat est obligatoire")
    private Boolean valide;
    private Long idReservation;
}
