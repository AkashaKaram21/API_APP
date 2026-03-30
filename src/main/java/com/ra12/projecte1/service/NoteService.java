package com.ra12.projecte1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ra12.projecte1.dto.NotaResponseDTO;
import com.ra12.projecte1.logging.UserLogging;
import com.ra12.projecte1.model.Nota;
import com.ra12.projecte1.repository.NotaRepository;

@Service
public class NotaService {

    @Autowired
    NotaRepository notaRepository;

    @Autowired
    UserLogging userLogging;

    // Creació d'una nota a partir d'un objecte Nota
    public Nota createNota(Nota nota) {
        userLogging.logInfo("Creant nova nota amb títol: " + nota.getTitle());

        if (nota.getTitle() == null || nota.getTitle().trim().isEmpty()) {
            userLogging.logInfo("Error: El títol no pot estar buit");
            throw new IllegalArgumentException("El títol no pot estar buit");
        }

        if (nota.getSubtitle() == null || nota.getSubtitle().trim().isEmpty()) {
            userLogging.logInfo("Error: El subtítol no pot estar buit");
            throw new IllegalArgumentException("El subtítol no pot estar buit");
        }

        if (nota.getText() == null || nota.getText().trim().isEmpty()) {
            userLogging.logInfo("Error: El text no pot estar buit");
            throw new IllegalArgumentException("El text no pot estar buit");
        }

        try {
            notaRepository.insertNota(nota);
            userLogging.logInfo("Nota creada exitosament: " + nota.getTitle());
            return nota;
        } catch (Exception e) {
            userLogging.logError("Error al crear la nota: ", e);
            throw new RuntimeException("Error al crear la nota", e);
        }
    }

    // Obtencio de totes les notes
    public List<Nota> getAllNotas() {
        userLogging.logInfo("Obtenint totes les notes");
        try {
            List<Nota> notas = notaRepository.findAll();
            userLogging.logInfo("S'han trobat " + notas.size() + " notes");
            return notas;
        } catch (Exception e) {
            userLogging.logError("Error al obtenir les notes: ", e);
            throw new RuntimeException("Error al obtenir les notes", e);
        }
    }

    // Obté una nota a partir de la seva id
    public ResponseEntity<String> getNota(long notaId) {
        userLogging.logInfo("Accedint a la nota amb id: " + notaId);

        Nota nota = notaRepository.getNotaById(notaId);

        if (nota == null) {
            userLogging.logError("No existeix cap nota amb la id: " + notaId, null);
        }

        return ResponseEntity.status(nota == null ? HttpStatus.NOT_FOUND : HttpStatus.FOUND)
                .body(nota == null ? "No s'ha trobat cap nota" : "Nota trobada: " + nota);
    }

    // Funció per actualitzar una nota per la seva id i una "nota actualitzada"
    // Si la nota existeix i els canvis no són nulls i són diferents als valors actuals, es canviaran
    public ResponseEntity<String> updateNota(long notaId, Nota updatedNota) throws Exception {
        userLogging.logInfo("Actualitzant nota amb la id: " + notaId);

        Nota nota = notaRepository.getNotaById(notaId);

        if (nota == null) {
            userLogging.logError("La nota amb id: " + notaId + " no existeix", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No s'ha pogut trobar la nota.");
        }

        if (updatedNota.getTitle() != null && !updatedNota.getTitle().equals(nota.getTitle())) {
            nota.setTitle(updatedNota.getTitle());
        }

        if (updatedNota.getSubtitle() != null && !updatedNota.getSubtitle().equals(nota.getSubtitle())) {
            nota.setSubtitle(updatedNota.getSubtitle());
        }

        if (updatedNota.getText() != null && !updatedNota.getText().equals(nota.getText())) {
            nota.setText(updatedNota.getText());
        }

        notaRepository.updateNota(nota);

        return ResponseEntity.status(HttpStatus.OK).body("Nota actualitzada!");
    }

    // Funció per borrar una nota per la seva id
    public ResponseEntity<String> deleteNota(long notaId) {
        userLogging.logInfo("Borrant la nota amb id: " + notaId);

        try {
            notaRepository.deleteNota(notaId);
        } catch (Exception exception) {
            userLogging.logError("La nota amb id: " + notaId + " no existeix", exception);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No s'ha pogut esborrar la nota amb id: \"" + notaId + "\", error: " + exception.getLocalizedMessage());
        }

        userLogging.logInfo("La nota amb id: " + notaId + " s'ha borrat correctament");
        return ResponseEntity.status(HttpStatus.OK).body("S'ha esborrat la nota amb id: " + notaId);
    }

    // Funció per borrar totes les notes
    public ResponseEntity<String> deleteAllNotas() {
        userLogging.logInfo("Borrant totes les notes");
        notaRepository.deleteAllNotas();
        return ResponseEntity.status(HttpStatus.OK).body("S'han esborrat totes les notes");
    }
}