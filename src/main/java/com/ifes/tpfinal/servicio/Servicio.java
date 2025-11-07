package com.ifes.tpfinal.servicio;

import com.ifes.tpfinal.dom.Concesionaria;
import com.ifes.tpfinal.repositorio.Repositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Servicio<T> implements IServicio<T> {

    @Autowired
    private Repositorio repo;

    @Override
    public T guardar(T entidad) {
         this.repo.guardar(entidad);
         return entidad;
    }

    @Override
    public List<T> listar() {
        return this.repo.listar();
    }

    @Override
    public T buscarPorId(Object id) {
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
        // return this.repo.buscarPorId(Concesionaria.class, id);
    }

    @Override
    public void eliminar(Object id) {
        this.repo.eliminar(Concesionaria.class);
    }


}
