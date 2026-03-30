package com.ra12.projecte1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

// Representació en forma d'objecte d'una nota
@Entity
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String subtitle;
    private String text;

    public Nota() {

    }

    public Nota(String title, String subtitle, String text) {
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSubtitle() {
        return subtitle;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Nota [id=" + id + ", title=" + title + ", subtitle=" + subtitle + ", text=" + text + "]";
    }
}