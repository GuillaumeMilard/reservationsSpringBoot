package be.iccbxl.pid.reservationsSpringBoot.model;

import jakarta.persistence.*;

// Définir la classe comme entité
@Entity
// Spécifier le nom de la table
@Table(name="artists")
public class Artist {
    // Propriété contenent l’identifiant unique, clé primaire de la table,
    @Id
    // Indique que Valeur générée automatiquement
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String firstname;
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
    public String toString() {
        return firstname + " " + lastname ;
    }
}



