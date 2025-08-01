package be.iccbxl.pid.reservations_springboot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="artists")
public class Artist {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    private Long id;
    private String firstname;
    private String lastname;

    @Override
    public String toString() {
        return (firstname != null ? firstname : "") + " " + (lastname != null ? lastname : "");
    }
}
