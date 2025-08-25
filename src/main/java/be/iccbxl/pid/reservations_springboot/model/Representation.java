package be.iccbxl.pid.reservations_springboot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="representations")
public class Representation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    //Spectacle de la représentation
    @ManyToOne
    @JoinColumn(name="show_id", nullable=false)
    private Show show;

    // Date de création de la représentation
    private LocalDateTime scheduledAt;

    //Lieu de prestation de la représentation
    @ManyToOne
    @JoinColumn(name="location_id", nullable=true)
    private Location location;


    public Representation(Show show, LocalDateTime scheduledAt, Location location) {
        this.show = show;
        this.scheduledAt = scheduledAt;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Representation [id=" + id + ", show=" + show + ", scheduledAt=" + scheduledAt
                + ", location=" + location + "]";
    }

}
