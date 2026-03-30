package com.ra12.projecte1.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ra12.projecte1.dto.NotaRequestDTO;
import com.ra12.projecte1.dto.NotaResponseDTO;
import com.ra12.projecte1.logging.UserLogging;
import com.ra12.projecte1.model.Nota;
import com.ra12.projecte1.service.NotaService;

@RestController
@RequestMapping("/api")
public class NotaController {

    @Autowired
    NotaService notaService;

    @Autowired
    UserLogging userLogging;

    // Creació d'una nota
    @PostMapping("/notas")
    public ResponseEntity<NotaResponseDTO> createNota(@RequestBody NotaRequestDTO notaRequestDTO) {
        userLogging.logInfo("Endpoint POST /api/notas llamado");

        try {
            Nota nota = new Nota(notaRequestDTO.getTitle(), notaRequestDTO.getSubtitle(), notaRequestDTO.getText());
            notaService.createNota(nota);

            NotaResponseDTO response = new NotaResponseDTO(nota);
            userLogging.logInfo("Nota creada: " + response);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            userLogging.logError("Error de validació: ", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            userLogging.logError("Error al crear la nota: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtenim totes les notes
    @GetMapping("/notas")
    public ResponseEntity<List<NotaResponseDTO>> getAllNotas() {
        userLogging.logInfo("Endpoint GET /api/notas llamado");

        try {
            List<Nota> notas = notaService.getAllNotas();

            List<NotaResponseDTO> response = notas.stream()
                    .map(nota -> new NotaResponseDTO(nota))
                    .collect(Collectors.toList());

            userLogging.logInfo("Retornant " + response.size() + " notes");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            userLogging.logError("Error al obtenir les notes: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtenim una nota a través de la seva id
    @GetMapping("/notas/{notaId}")
    public ResponseEntity<String> getNotaById(@PathVariable long notaId) {
        return notaService.getNota(notaId);
    }

    // Endpoint per actualitzar una nota a través de la seva id
    @PutMapping("/notas/{notaId}/update")
    public ResponseEntity<String> updateNota(@RequestBody Nota nota, @PathVariable long notaId) throws Exception {
        return notaService.updateNota(notaId, nota);
    }

    // Endpoint per borrar una nota a través de la seva id
    @DeleteMapping("/notas/{notaId}/delete")
    public ResponseEntity<String> deleteNota(@PathVariable long notaId) throws Exception {
        return notaService.deleteNota(notaId);
    }

    // Endpoint per borrar totes les notes
    @DeleteMapping("/notas")
    public ResponseEntity<String> deleteAllNotas() throws Exception {
        return notaService.deleteAllNotas();
    }
}