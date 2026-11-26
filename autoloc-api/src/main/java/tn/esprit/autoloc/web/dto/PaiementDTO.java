package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import tn.esprit.autoloc.domain.ModePaiement;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaiementDTO {

    private Long idPaiement;

    @NotNull(message = "Le montant du paiement est obligatoire")
    @Positive(message = "Le montant du paiement doit être supérieur à zéro")
    private BigDecimal montant;

    @NotNull(message = "La date du paiement est obligatoire")
    private LocalDate datePaiement;

    @NotNull(message = "Le mode de paiement est obligatoire")
    private ModePaiement modePaiement;

    @NotNull(message = "L'identifiant du contrat est obligatoire")
    @Positive(message = "L'identifiant du contrat doit être positif")
    private Long idContrat;
}
