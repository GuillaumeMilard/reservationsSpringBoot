package be.iccbxl.pid.reservations_springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationCreateDTO {

    @NotBlank(message = "La désignation est obligatoire")
    private String designation;

    @NotBlank(message = "L'adresse est obligatoire")
    private String address;

    private String website;

    private String phone;

    @NotNull(message = "La localité est obligatoire")
    private Long localityId; // On passe juste l'id de la Locality
}