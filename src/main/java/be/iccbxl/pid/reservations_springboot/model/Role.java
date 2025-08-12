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
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Using UserRole enum to define the role names
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Role name cannot be blank")
    @Size(min = 2, max = 60, message = "Role name must be between 2 and 60 characters")
    @Column(name = "name", nullable = false, unique = true)
    private UserRole name;

    @Override
    public String toString() {
        return name == null ? "ROLE_NULL" : name.toString();
    }
}
