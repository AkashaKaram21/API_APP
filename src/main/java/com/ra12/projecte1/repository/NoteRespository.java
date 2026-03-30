package com.ra12.projecte1.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ra12.projecte1.model.Nota;

@Repository
public class NotaRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // Custom RowMapper per tornar tots els resultats d'una query de SQL en una llista de notes
    private static final class NotaRowMapper implements RowMapper<Nota> {

        @Override
        public Nota mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Nota nota = new Nota();
            nota.setId(resultSet.getLong("id"));
            nota.setTitle(resultSet.getString("title"));
            nota.setSubtitle(resultSet.getString("subtitle"));
            nota.setText(resultSet.getString("text"));
            return nota;
        }
    }

    // Inserció d'una nova nota
    public void insertNota(Nota nota) {
        jdbcTemplate.update(
            "insert into notas (title, subtitle, text) values (?, ?, ?)",
            nota.getTitle(), nota.getSubtitle(), nota.getText()
        );
    }

    // Obtencio d'una nota a través de la seva id
    public Nota getNotaById(long notaId) {
        List<Nota> notas = jdbcTemplate.query(
            "select * from notas where id = ?",
            new NotaRowMapper(), notaId
        );
        return notas.isEmpty() ? null : notas.get(0);
    }

    // Actualitzem la nota ja existent amb els seus atributs
    public void updateNota(Nota nota) {
        jdbcTemplate.update(
            String.format("update notas set title = ?, subtitle = ?, text = ? where id = %s", nota.getId()),
            nota.getTitle(), nota.getSubtitle(), nota.getText()
        );
    }

    // Borrem una nota a partir de la seva id
    public void deleteNota(long notaId) {
        jdbcTemplate.update("delete from notas where id = ?", notaId);
    }

    // Borrem totes les notes
    public void deleteAllNotas() {
        jdbcTemplate.update("delete from notas");
    }

    // Obtencio de totes les notes
    public List<Nota> findAll() {
        return jdbcTemplate.query("select * from notas", new NotaRowMapper());
    }
}