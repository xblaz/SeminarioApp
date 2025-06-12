package ar.edu.ues21.seminario.service;

import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.model.aplicacion.EsquemaFinanciacion;
import ar.edu.ues21.seminario.repository.aplicacion.EsquemaFinanciacionRepository;

import java.util.List;

public class ConfiguracionService {
    private final EsquemaFinanciacionRepository esquemaRepository;

    public ConfiguracionService(EsquemaFinanciacionRepository esquemaRepo) {
        this.esquemaRepository = esquemaRepo;
    }

    public List<EsquemaFinanciacion> obtenerTodosEsquemaFinanciacion() throws RepositoryException {
        List<EsquemaFinanciacion> lista = esquemaRepository.findAll();

        System.out.println(lista.size());

        return esquemaRepository.findAll();
    }
}
