package ar.edu.ues21.seminario.repository;

import ar.edu.ues21.seminario.exception.RepositoryException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GenericRepository<T, ID> {
    List<T> findAll() throws RepositoryException;
    Optional<T> findById(ID id) throws RepositoryException;
    void save(T entity) throws RepositoryException;
    void delete(ID id) throws RepositoryException;
    List<T> findByCriteria(Map<String, Object> criteria) throws RepositoryException;
}
