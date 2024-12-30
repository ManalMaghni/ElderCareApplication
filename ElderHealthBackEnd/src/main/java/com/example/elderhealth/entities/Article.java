package com.example.elderhealth.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String theme;
    private String contenu;

    private LocalDate publicationDate;

    // Constructeur par défaut
    public Article() {
    }

    // Constructeur avec paramètres
    public Article(Long id, String theme, String contenu, LocalDate publicationDate) {
        this.id = id;
        this.theme = theme;
        this.contenu = contenu;
        this.publicationDate = publicationDate;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
}
