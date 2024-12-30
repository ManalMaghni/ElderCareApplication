package com.example.elderhealth.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import com.example.elderhealth.entities.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "dosage")
    private String dosage;

    private String heure;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonBackReference
    private User user;

    // Constructeur par défaut
    public Medicine() {
    }

    // Constructeur avec paramètres
    public Medicine(String nom, String dosage, String heure, User user) {
        this.nom = nom;
        this.dosage = dosage;
        this.heure = heure;
        this.user = user;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() { // Renommé pour correspondre au champ "nom"
        return nom;
    }

    public void setNom(String nom) { // Renommé pour correspondre au champ "nom"
        this.nom = nom;
    }

    public String getDosage() { // Renommé pour correspondre au champ "dosage"
        return dosage;
    }

    public void setDosage(String dosage) { // Renommé pour correspondre au champ "dosage"
        this.dosage = dosage;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return id != null ? id.equals(medicine.id) : medicine.id == null;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", dosage='" + dosage + '\'' +
                ", heure='" + heure + '\'' +
                ", user=" + user +
                '}';
    }
}
