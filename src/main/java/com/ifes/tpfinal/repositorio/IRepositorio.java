package com.ifes.tpfinal.repositorio;


import java.util.List;

public interface IRepositorio<T> {
    void guardar(T o);
    List<T> listar();
    void eliminar(T o);
}
