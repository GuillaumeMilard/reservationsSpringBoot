package be.iccbxl.pid.reservationsSpringBoot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "types")
public class Type {


    @Id // Propriété contenent l’identifiant unique, clé primaire de la table,
    @GeneratedValue(strategy= GenerationType.IDENTITY) // Indique que Valeur générée automatiquement
    private Long id;
    private String typename;

    public Type() { }

    public Type(String typename) {
        this.typename = typename;
    }

    public Long getId() {
        return id;
    }

    public String getTypename() {
        return typename;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    @Override
    public String toString() { return typename ; }
}
