package be.iccbxl.pid.reservations_springboot.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="localities")
@Getter @Setter @NoArgsConstructor
public class Locality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "postal_code", length = 6, nullable = false, unique= true)
    private String postalCode;

    @Column(name = "name", length = 60, nullable = false, unique = true)
    private String name;

    @OneToMany( targetEntity=Location.class, mappedBy="locality" )
    @Setter(AccessLevel.NONE)
    private List<Location> locations = new ArrayList<>();

    public Locality addLocation(Location location) {
        if(!this.locations.contains(location)) {
            this.locations.add(location);
            location.setLocality(this);
        }
        return this;
    }

    public Locality removeLocation(Location location) {
        if(this.locations.contains(location)) {
            this.locations.remove(location);
            if(location.getLocality().equals(this)) {
                location.setLocality(null);// si tu acceptes un Location sans Locality
            }
        }
        return this;
    }

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
        return "Locality [id=" + id + ", postalCode=" + postalCode + ", locality=" + name + "]";
    }

}
