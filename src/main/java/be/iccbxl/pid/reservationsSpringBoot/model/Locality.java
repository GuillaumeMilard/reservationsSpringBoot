package be.iccbxl.pid.reservationsSpringBoot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "localities")
public class Locality {

    @Id // Propriété contenent l’identifiant unique, clé primaire de la table,
    @GeneratedValue(strategy= GenerationType.IDENTITY) // Indique que Valeur générée automatiquement
    private Long id;
    private String postal_code;
    private String localityname;

    public Locality() { }

    public Locality(String postal_code, String localityname) {
        this.postal_code = postal_code;
        this.localityname = localityname;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getPostal_code() { return postal_code; }

    public void setPostal_code(String postal_code) { this.postal_code = postal_code; }

    public String getLocalityname() { return localityname; }

    public void setLocalityname(String localityname) { this.localityname = localityname; }

    @Override
    public String toString() {
        return postal_code + " " + localityname ; }
}
