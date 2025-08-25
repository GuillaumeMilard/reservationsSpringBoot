package be.iccbxl.pid.reservations_springboot.model;

import com.github.slugify.Slugify;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="shows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false, unique = true)
    private String slug;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "poster_url", nullable = true)
    private String posterUrl;

    //Lieu de création du spectacle
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = true)
    private Location location;

    private boolean bookable;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    //Date de création du spectacle
    @Column(name = "created_at", nullable = true, updatable = false)
    private LocalDateTime createdAt;

    //Date de modification du spectacle
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    //Liste des représentations de ce spectacle
    @OneToMany(targetEntity=Representation.class, mappedBy="show")
    private List<Representation> representations = new ArrayList<>();

    /**
     * -- GETTER --
     *  Get the performances (artists in a type of collaboration) for the show
     */
    //Liste des artistes liés à ce spectacle via ArtistType
    @ManyToMany(mappedBy = "shows")
    private List<ArtistType> artistTypes = new ArrayList<>();

    // --- Slug basé sur title ---
    public void setTitle(String title) {
        this.title = title;
        Slugify slg = new Slugify();
        this.slug = slg.slugify(title);
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // --- Gestion de la relation bidirectionnelle avec Location ---
    public void setLocation(Location location) {
        if (this.location != null) {
            this.location.removeShow(this);
        }
        this.location = location;
        if (location != null) {
            location.addShow(this);
        }
    }

    public Show addRepresentation(Representation representation) {
        if(!this.representations.contains(representation)) {
            this.representations.add(representation);
            representation.setShow(this);
        }
        return this;
    }

    public Show removeRepresentation(Representation representation) {
        if (this.representations.contains(representation)) {
            this.representations.remove(representation);
            if (representation.getLocation().equals(this)) {
                representation.setLocation(null);
            }
        }
        return this;
    }

    public Show addArtistType(ArtistType artistType) {
        if(!this.artistTypes.contains(artistType)) {
            this.artistTypes.add(artistType);
            artistType.addShow(this);
        }
        return this;
    }

    public Show removeArtistType(ArtistType artistType) {
        if(this.artistTypes.contains(artistType)) {
            this.artistTypes.remove(artistType);
            artistType.getShows().remove(this);
        }
        return this;
    }


    @Override
    public String toString() {
        return "Show{" +
                "id=" + id +
                ", slug='" + slug + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", bookable=" + bookable +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", representations=" + representations.size() +
                '}';
    }

}
