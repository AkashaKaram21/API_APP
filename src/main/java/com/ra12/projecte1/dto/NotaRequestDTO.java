package com.ra12.projecte1.dto;

// DTO para una request de creación de nota
// Contiene los datos necesarios para crear una nota
public class NotaRequestDTO {
    private String title;
    private String subtitle;
    private String text;

    public NotaRequestDTO() {

    }

    public NotaRequestDTO(String title, String subtitle, String text) {
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
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
        return "NotaRequestDTO [title=" + title + ", subtitle=" + subtitle + ", text=" + text + "]";
    }
}