package com.example.elderhealth.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Bilan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomTest;
    private String valeur;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonBackReference
    private User user;

    // Constructeur par défaut
    public Bilan() {
    }

    // Constructeur avec paramètres
    public Bilan(Long id, String nomTest, String valeur, User user) {
        this.id = id;
        this.nomTest = nomTest;
        this.valeur = valeur;
        this.user = user; // Mise à jour pour utiliser User
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomTest() {
        return nomTest;
    }

    public void setNomTest(String nomTest) {
        this.nomTest = nomTest;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
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
        Bilan bilan = (Bilan) o;
        return id != null ? id.equals(bilan.id) : bilan.id == null;
    }

    @Override
    public String toString() {
        return "Bilan{" +
                "id=" + id +
                ", nomTest='" + nomTest + '\'' +
                ", valeur='" + valeur + '\'' +
                ", user=" + user +
                '}';
    }
}