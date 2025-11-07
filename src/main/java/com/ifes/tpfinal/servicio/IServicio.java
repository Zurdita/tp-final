package com.ifes.tpfinal.servicio;

import java.util.List;

public interface IServicio<T> {
    
    T guardar(T entidad); 
    
    List<T> listar(); 
    
    T buscarPorId(Object id); 
    
    void eliminar(Object id);
}