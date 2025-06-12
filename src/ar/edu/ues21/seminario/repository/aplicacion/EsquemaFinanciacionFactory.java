package ar.edu.ues21.seminario.repository.aplicacion;

import ar.edu.ues21.seminario.model.aplicacion.*;
import ar.edu.ues21.seminario.repository.GenericMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EsquemaFinanciacionFactory implements GenericMapper.InstanceFactory<EsquemaFinanciacion> {
    @Override
    public EsquemaFinanciacion create(ResultSet rs) throws SQLException {
        TipoEsquema tipo = TipoEsquema.valueOf(rs.getString("tipo"));
        Long id = rs.getLong("id");
        String descripcion = rs.getString("descripcion");
        Double tasa = rs.getDouble("tasa_interes_anual");
        Integer cuotas = rs.getInt("cantidad_cuotas");
        Boolean requiereGarante = rs.getBoolean("requiere_garante");
        LocalDate fecha = rs.getDate("fecha_creacion").toLocalDate();
        EstadoEsquema estadoEsquema = EstadoEsquema.valueOfCodigo(rs.getString("estado"));
        switch (tipo) {
            case FRANCES:
                return new SistemaFrances(id, descripcion, tasa, cuotas,
                        requiereGarante, fecha, estadoEsquema, tipo);
            case ALEMAN:
                return new SistemaAleman(id, descripcion, tasa, cuotas,
                        requiereGarante, fecha, estadoEsquema, tipo);
            case AMERICANO:
                return new SistemaAmericano(id, descripcion, tasa, cuotas,
                        requiereGarante, fecha, estadoEsquema, tipo);
            default:
                throw new IllegalArgumentException("Tipo no soportado: " + tipo);
        }
    }
}
