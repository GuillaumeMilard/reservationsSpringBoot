package be.iccbxl.pid.reservations_springboot.dto;

import be.iccbxl.pid.reservations_springboot.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateDTO {

    @NotBlank
    @Size(max = 100)
    private String firstname;

    @NotBlank @Size(max = 100)
    private String lastname;

    @NotBlank @Email
    @Size(max = 255)
    private String email;

    @NotBlank
    private String langue;

    // construire le DTO à partir d’un User
    public static ProfileUpdateDTO fromUser(User user) {
        ProfileUpdateDTO dto = new ProfileUpdateDTO();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setLangue(user.getLangue()); // attention au champ de User
        return dto;
    }

    // Appliquer les valeurs du DTO à l'entité User
    public void applyToUser(User user) {
        user.setFirstname(this.firstname);
        user.setLastname(this.lastname);
        user.setEmail(this.email);
        user.setLangue(this.langue);
    }
}
