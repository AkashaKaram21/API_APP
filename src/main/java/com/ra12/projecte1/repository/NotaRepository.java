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

    // Custom RowMapper per tornar tots els resultats d'una query de SQL en una llista de notas
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

    // Inserta una nota en la base de datos
    public void insertNota(Nota nota) {
        jdbcTemplate.update("insert into nota (title, subtitle, text) values (?, ?, ?)", nota.getTitle(), nota.getSubtitle(), nota.getText());
    }

    // Obtiene una nota por su ID
    public Nota getNotaById(long notaId) {        
        List<Nota> notas = jdbcTemplate.query("select * from nota where id = ?", new NotaRowMapper(), notaId);
            
        return notas.isEmpty() ? null : notas.get(0);
    }

    // Actualiza una nota existente
    public void updateNota(Nota nota) {
        jdbcTemplate.update(String.format("update nota set title = ?, subtitle = ?, text = ? where id = %s", nota.getId()), nota.getTitle(), nota.getSubtitle(), nota.getText());
    }

    // Borra todas las notas
    public void deleteAllNotas() {
        jdbcTemplate.update("delete from nota");
    }

    // Borra una nota por su ID
    public void deleteNota(long notaId) {
        jdbcTemplate.update("delete from nota where id = ?", notaId);
    }

    // Obtiene una lista de todas las notas
    public List<Nota> findAll() {
        String sql = "select * from nota";
        return jdbcTemplate.query(sql, new NotaRowMapper());
    }
}