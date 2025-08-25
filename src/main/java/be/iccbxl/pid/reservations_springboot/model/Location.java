package be.iccbxl.pid.reservations_springboot.model;

import com.github.slugify.Slugify;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="locations")
public class Location {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String slug;

    private String designation;
    private String address;
    private String website;
    private String phone;

    //Liste des spectacles créés dans ce lieu
    @OneToMany(targetEntity=Show.class, mappedBy="location")
    private List<Show> shows = new ArrayList<>();

    //Liste des représentations créées dans ce lieu
    @OneToMany(targetEntity=Representation.class, mappedBy="location")
    private List<Representation> representations = new ArrayList<>();


    //Localité (commune) où se situe le lieu
    @ManyToOne
    @JoinColumn(name="locality_id", nullable=false)
    private Locality locality;

    // --- Constructeur ---
    public Location(String slug, String designation, String address, Locality locality, String website, String phone) {
        Slugify slg = new Slugify();
        if (slug == null || slug.isBlank()) {
            this.slug = slg.slugify(designation);
        } else {
            this.slug = slug;
        }
        this.slug = slg.slugify(designation);
        this.designation = designation;
        this.address = address;
        this.locality = locality;
        this.website = website;
        this.phone = phone;
    }

    private void setSlug(String slug) {
        this.slug = slug;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
        Slugify slg = new Slugify();
        this.setSlug(slg.slugify(designation));
    }

    public void setLocality(Locality locality) {
        if (this.locality != null) {
            this.locality.removeLocation(this);
        }
        this.locality = locality;
        if (locality != null) {
            locality.addLocation(this);
        }
    }

    public Location addShow(Show show) {
        if(!this.shows.contains(show)) {
            this.shows.add(show);
            show.setLocation(this);
        }
        return this;
    }

    public Location removeShow(Show show) {
        if(this.shows.contains(show)) {
            this.shows.remove(show);
            if(show.getLocation().equals(this)) {
                show.setLocation(null);
            }
        }
        return this;
    }

    public Location addRepresentation(Representation representation) {
        if(!this.representations.contains(representation)) {
            this.representations.add(representation);
            representation.setLocation(this);
        }
        return this;
    }

    public Location removeRepresentation(Representation representation) {
        if(this.representations.contains(representation)) {
            this.representations.remove(representation);
            if(representation.getLocation().equals(this)) {
                representation.setLocation(null);
            }
        }
        return this;
    }


    @Override
    public String toString() {
        return "Location [id=" + id + ", slug=" + slug + ", designation=" + designation
                + ", address=" + address	+ ", locality=" + locality + ", website="
                + website + ", phone=" + phone + ", shows=" + shows.size()
                + ", representations=" + representations.size() + "]";
    }
}