package be.iccbxl.pid.reservationsSpringBoot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id // Propriété contenent l’identifiant unique, clé primaire de la table,
    @GeneratedValue(strategy= GenerationType.IDENTITY) // Indique que Valeur générée automatiquement
    private Long id;
    private String rolename;

    public Role() {  }

    public Role(String rolename) { this.rolename = rolename; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getRolename() { return rolename; }

    public void setRolename(String rolename) { this.rolename = rolename; }

    @Override
    public String toString() { return rolename ; }
}
