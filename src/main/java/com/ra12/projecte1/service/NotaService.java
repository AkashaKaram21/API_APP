package com.ra12.projecte1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ra12.projecte1.logging.NotaLogging;
import com.ra12.projecte1.model.Nota;
import com.ra12.projecte1.repository.NotaRepository;

@Service
public class NotaService {

    @Autowired
    NotaRepository notaRepository;

    @Autowired
    NotaLogging appLogging; // Cambiado para apuntar a NotaLogging

    // Creació d'una nota a partir d'un objecte Nota
    public Nota createNota(Nota nota) {
        appLogging.logInfo("Creant nova nota amb títol: " + nota.getTitle());

        if (nota.getTitle() == null || nota.getTitle().trim().isEmpty()) {
            appLogging.logInfo("Error: El títol no pot estar buit");
            throw new IllegalArgumentException("El títol no pot estar buit");
        }

        if (nota.getSubtitle() == null || nota.getSubtitle().trim().isEmpty()) {
            appLogging.logInfo("Error: El subtítol no pot estar buit");
            throw new IllegalArgumentException("El subtítol no pot estar buit");
        }

        if (nota.getText() == null || nota.getText().trim().isEmpty()) {
            appLogging.logInfo("Error: El text no pot estar buit");
            throw new IllegalArgumentException("El text no pot estar buit");
        }

        try {
            notaRepository.insertNota(nota);
            appLogging.logInfo("Nota creada exitosament: " + nota.getTitle());
            return nota;
        } catch (Exception e) {
            appLogging.logError("Error al crear la nota: ", e);
            throw new RuntimeException("Error al crear la nota", e);
        }
    }

    // Obtencio de totes les notes
    public List<Nota> getAllNotas() {
        appLogging.logInfo("Obtenint totes les notes");
        try {
            List<Nota> notas = notaRepository.findAll();
            appLogging.logInfo("S'han trobat " + notas.size() + " notes");
            return notas;
        } catch (Exception e) {
            appLogging.logError("Error al obtenir les notes: ", e);
            throw new RuntimeException("Error al obtenir les notes", e);
        }
    }

    // Obté una nota a partir de la seva id
    public Nota getNotaById(long notaId) {
        appLogging.logInfo("Accedint a la nota amb id: " + notaId);

        Nota nota = notaRepository.getNotaById(notaId);

        if (nota == null) {
            appLogging.logError("No existeix cap nota amb la id: " + notaId, null);
        }

        return nota;
    }

    // Funció per actualitzar una nota per la seva id (Devuelve boolean)
    public boolean updateNota(long notaId, Nota updatedNota) {
        appLogging.logInfo("Actualitzant nota amb la id: " + notaId);

        Nota nota = notaRepository.getNotaById(notaId);

        if (nota == null) {
            appLogging.logError("La nota amb id: " + notaId + " no existeix", null);
            return false;
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
        return true;
    }

    // Funció per borrar una nota per la seva id (Devuelve boolean)
    public boolean deleteNota(long notaId) {
        appLogging.logInfo("Borrant la nota amb id: " + notaId);

        try {
            notaRepository.deleteNota(notaId);
            appLogging.logInfo("La nota amb id: " + notaId + " s'ha borrat correctament");
            return true;
        } catch (Exception exception) {
            appLogging.logError("Error al esborrar la nota amb id: " + notaId, exception);
            return false;
        }
    }
}