package ar.edu.ues21.seminario.repository;

import ar.edu.ues21.seminario.exception.LogicaException;

import java.util.ArrayList;
import java.util.List;

public abstract class ObservableRepository<T, ID> implements GenericRepository<T, ID> {
    private final List<DataChangeListener<T>> listeners = new ArrayList<>();
    public void addDataChangeListener(DataChangeListener<T> listener) {
        listeners.add(listener);
    }
    protected void notifyDataChanged(List<T> newData) {
        listeners.forEach(l -> l.onDataChanged(newData));
    }
    @Override
    public List<T> findAll() throws LogicaException {
        List<T> data = internalFindAll();
        notifyDataChanged(data);
        return data;
    }
    protected abstract List<T> internalFindAll() throws LogicaException;
    public interface DataChangeListener<T> {
        void onDataChanged(List<T> newData);
    }
}