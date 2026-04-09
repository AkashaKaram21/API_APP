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
import com.ra12.projecte1.logging.NotaLogging;
import com.ra12.projecte1.model.Nota;
import com.ra12.projecte1.service.NotaService;

@RestController
@RequestMapping("/api/notes")
public class NotaController {

    @Autowired
    NotaService notaService;

    @Autowired
    NotaLogging appLogging; // Cambiado para apuntar a NotaLogging

    // POST /api/notes
    @PostMapping
    public ResponseEntity<NotaResponseDTO> createNota(@RequestBody NotaRequestDTO notaRequestDTO) {
        appLogging.logInfo("Endpoint POST /api/notes llamado");

        try {
            Nota nota = new Nota(notaRequestDTO.getTitle(), notaRequestDTO.getSubtitle(), notaRequestDTO.getText());
            notaService.createNota(nota);

            NotaResponseDTO response = new NotaResponseDTO(nota);
            appLogging.logInfo("Nota creada: " + response);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            appLogging.logError("Error de validació: ", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            appLogging.logError("Error al crear la nota: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/notes
    @GetMapping
    public ResponseEntity<List<NotaResponseDTO>> getAllNotas() {
        appLogging.logInfo("Endpoint GET /api/notes llamado");

        try {
            List<Nota> notas = notaService.getAllNotas();

            List<NotaResponseDTO> response = notas.stream()
                    .map(nota -> new NotaResponseDTO(nota))
                    .collect(Collectors.toList());

            appLogging.logInfo("Retornant " + response.size() + " notes");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            appLogging.logError("Error al obtenir les notes: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/notes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<NotaResponseDTO> getNotaById(@PathVariable long id) {
        appLogging.logInfo("Endpoint GET /api/notes/" + id + " llamado");

        Nota nota = notaService.getNotaById(id);

        if (nota == null) {
            appLogging.logError("No existeix cap nota amb la id: " + id, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(new NotaResponseDTO(nota));
    }

    // PUT /api/notes/{id} -> Limpio y RESTful
    @PutMapping("/{id}")
    public ResponseEntity<String> updateNota(@PathVariable long id, @RequestBody Nota nota) {
        boolean updated = notaService.updateNota(id, nota);
        
        if (!updated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No s'ha pogut trobar la nota.");
        }
        
        return ResponseEntity.status(HttpStatus.OK).body("Nota actualitzada!");
    }

    // DELETE /api/notes/{id} -> Limpio y RESTful
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNota(@PathVariable long id) {
        boolean deleted = notaService.deleteNota(id);
        
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No s'ha pogut esborrar la nota amb id: " + id);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body("S'ha esborrat la nota amb id: " + id);
    }
}