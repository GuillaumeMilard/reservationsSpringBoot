package be.iccbxl.pid.reservations_springboot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {

    @NotBlank(message = "Le login est obligatoire")
    @Size(max = 60, message = "Le login ne peut pas dépasser 60 caractères")
    public String login;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    public String password;

    @NotBlank(message = "La confirmation du mot de passe est obligatoire")
    @Size(min = 8, message = "La confirmation du mot de passe doit contenir au moins 8 caractères")
    private String passwordConfirm;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 60, message = "Le prénom ne peut pas dépasser 60 caractères")
    public String firstname;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 60, message = "Le nom ne peut pas dépasser 60 caractères")
    public String lastname;

    @Email(message = "Email non valide")
    @NotBlank(message = "Email est obligatoire")
    @Size(max = 255, message = "L'email ne peut pas dépasser 255 caractères")
    public String email;

    @NotBlank(message = "La langue est obligatoire")
    @Size(min = 2, max = 2, message = "La langue doit contenir 2 caractères")
    private String langue;

}
