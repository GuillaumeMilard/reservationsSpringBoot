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
@Table(name ="types")
public class Type {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "The name must not be empty.")
    @Size(min=2, max=60, message = "The name must be between 2 and 60 characters long.")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // Constructeur  pratique pour créer rapidement des instances
    public Type(String name) {
        this.name = name;
    }

    // Redéfinition de la méthode toString pour afficher le nom du type
    @Override
    public String toString() {
        return name;
    }
}
