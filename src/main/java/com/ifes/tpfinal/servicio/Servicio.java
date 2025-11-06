package com.ifes.tpfinal.servicio;

import com.ifes.tpfinal.repositorio.IRepositorio;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Servicio<T> implements IServicio<T> {

    private final IRepositorio<T> repo;

    public Servicio(IRepositorio<T> repo) {
        this.repo = repo;
    }

    @Override
    public T guardar(T entidad) { return repo.guardar(entidad); }

    @Override
    public T buscarPorId(Class<T> clazz, Object id) { return repo.buscarPorId(clazz, id); }

    @Override
    public List<T> listar(Class<T> clazz) { return repo.listar(clazz); }

    @Override
    public void eliminar(Class<T> clazz, Object id) { repo.eliminar(clazz, id); }
}
