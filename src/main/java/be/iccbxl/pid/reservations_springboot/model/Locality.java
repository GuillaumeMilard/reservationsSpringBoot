package be.iccbxl.pid.reservations_springboot.model;


import jakarta.persistence.*;
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

    private String postalCode;

    private String name;

    @Override
    public String toString() {
        return postalCode + " " + name;
    }
}
