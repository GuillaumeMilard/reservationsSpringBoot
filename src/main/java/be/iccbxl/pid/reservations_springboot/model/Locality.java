package be.iccbxl.pid.reservations_springboot.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="localities")
public class Locality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The postal code must not be empty.")
    @Size(min = 4, max = 6, message = "The postal code must be between 4 and 6 characters long.")
    @Column(name = "postal_code", length = 6, nullable = false, unique= true)
    private String postalCode;

    @NotBlank(message = "The name must not be empty.")
    @Size(min = 2, max = 60, message = "The name must be between 2 and 60 characters long.")
    @Column(name = "name", length = 60, nullable = false, unique = true)
    private String name;

    @PrePersist
    @PreUpdate
    private void trimSpaces() {
        if (postalCode != null) {
            postalCode = postalCode.trim();
        }
        if (name != null) {
            name = name.trim();
        }
    }

    @Override
    public String toString() {
        return postalCode + " " + name;
    }
}
