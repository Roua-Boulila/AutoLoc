package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgenceDTO {
    private Long idAgence;
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 80, message = "Le nom doit contenir entre 2 et 80 caractères")
    @Pattern(regexp = "^[\\p{L}][\\p{L} .'-]*$", message = "Le nom contient des caractères invalides")
    private String nom;
    @NotBlank(message = "La ville est obligatoire")
    @Size(min = 2, max = 50, message = "La ville doit contenir entre 2 et 50 caractères")
    @Pattern(regexp = "^[\\p{L}][\\p{L} .'-]*$", message = "La ville contient des caractères invalides")
    private String ville;
    @NotBlank(message = "L'adresse est obligatoire")
    @Size(min = 5, max = 150, message = "L'adresse doit contenir entre 5 et 150 caractères")
    private String adresse;
    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(
            regexp = "^(?:\\+216[\\s-]?)?[24579]\\d[\\s-]?\\d{3}[\\s-]?\\d{3}$",
            message = "Le téléphone doit être un numéro tunisien valide"
    )
    private String telephone;
}
