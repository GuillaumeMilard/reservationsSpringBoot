package be.iccbxl.pid.reservationsSpringBoot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity // Définir la classe comme entité
@Table(name="artists") // Spécifier le nom de la table
public class Artist {

    @Id // Propriété contenent l’identifiant unique, clé primaire de la table,
    @GeneratedValue(strategy= GenerationType.IDENTITY) // Indique que Valeur générée automatiquement
    private Long id;

    @NotBlank(message = "The firstname must not be empty.")
    @Size(min=2, max=60, message = "The firstname must be between 2 and 60 characters long.")
    private String firstname;

    @NotBlank(message = "The lastname must not be empty.")
    @Size(min=2, max=60, message = "The lastname must be between 2 and 60 characters long.")
    private String lastname;

    public Artist() { }

    public Artist(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() { return firstname + " " + lastname ;
    }
}



