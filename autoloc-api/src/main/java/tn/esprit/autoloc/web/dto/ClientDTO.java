package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private Long idClient;
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    @Pattern(regexp = "^[\\p{L}][\\p{L} .'-]*$", message = "Le nom contient des caractères invalides")
    private String nom;
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    @Pattern(regexp = "^[\\p{L}][\\p{L} .'-]*$", message = "Le prénom contient des caractères invalides")
    private String prenom;
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email est invalide")
    @Size(max = 120, message = "L'email ne peut pas dépasser 120 caractères")
    private String email;
    @NotBlank(message = "Le téléphone est obligatoire")
    private String telephone;
    @NotBlank(message = "Le numéro de permis est obligatoire")
    @Size(min = 5, max = 30, message = "Le numéro de permis doit contenir entre 5 et 30 caractères")
    private String numPermis;
    @PastOrPresent(message = "La date d'inscription ne peut pas être future")
    private java.time.LocalDate dateInscription;
}
