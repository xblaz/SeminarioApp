package ar.edu.ues21.seminario.repository.aplicacion;

import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.model.aplicacion.EsquemaFinanciacion;
import ar.edu.ues21.seminario.repository.GenericMapper;
import ar.edu.ues21.seminario.repository.GenericRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EsquemaFinanciacionRepository implements GenericRepository<EsquemaFinanciacion, Long> {
    private final Connection conexion;
    public EsquemaFinanciacionRepository(Connection conn){
        this.conexion = conn;
    }
    @Override
    public List<EsquemaFinanciacion> findAll() throws RepositoryException {
        String sql = "SELECT * FROM esquema_financiacion;";
        try (PreparedStatement stmt = this.conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            return GenericMapper.mapperList(rs, EsquemaFinanciacion.class, new EsquemaFinanciacionFactory());
        } catch (SQLException e) {
            e.getStackTrace();
            throw new RepositoryException(String.format("Obteniendo esquemas de financiación %s", e.getMessage()));
        }
    }
    @Override
    public Optional<EsquemaFinanciacion> findById(Long aLong) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public void save(EsquemaFinanciacion entity) throws RepositoryException {

    }

    @Override
    public void delete(Long aLong) throws RepositoryException {

    }

    @Override
    public List<EsquemaFinanciacion> findByCriteria(Map<String, Object> criteria) throws RepositoryException {
        return null;
    }
}
