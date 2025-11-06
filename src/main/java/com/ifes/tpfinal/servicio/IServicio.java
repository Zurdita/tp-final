package com.ifes.tpfinal.servicio;

import java.util.List;

public interface IServicio<T> {
    T guardar(T entidad);
    T buscarPorId(Class<T> clazz, Object id);
    List<T> listar(Class<T> clazz);
    void eliminar(Class<T> clazz, Object id);
}
