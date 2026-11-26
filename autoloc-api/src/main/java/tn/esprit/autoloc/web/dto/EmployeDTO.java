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
public class EmployeDTO {
    private Long idEmploye;
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    @Pattern(regexp = "^[\\p{L}][\\p{L} .'-]*$", message = "Le nom contient des caractères invalides")
    private String nom;
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    @Pattern(regexp = "^[\\p{L}][\\p{L} .'-]*$", message = "Le prénom contient des caractères invalides")
    private String prenom;
    @NotBlank(message = "Le role est obligatoire")
    private tn.esprit.autoloc.domain.RoleEmploye role;
    private Long idAgence;
}
