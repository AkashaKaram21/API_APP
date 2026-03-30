package com.ra12.projecte1.dto;

import com.ra12.projecte1.model.Nota;

// Tota l'informació sobre una nota que podem retornar en una resposta
public class NotaResponseDTO {
    private long id;
    private String title;
    private String subtitle;
    private String text;

    public NotaResponseDTO() {

    }

    public NotaResponseDTO(long id, String title, String subtitle, String text) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
    }

    public NotaResponseDTO(Nota nota) {
        this.id = nota.getId();
        this.title = nota.getTitle();
        this.subtitle = nota.getSubtitle();
        this.text = nota.getText();
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
        return "NotaResponseDTO [id=" + id + ", title=" + title + ", subtitle=" + subtitle + ", text=" + text + "]";
    }
}