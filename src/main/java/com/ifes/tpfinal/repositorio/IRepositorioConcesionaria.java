package com.ifes.tpfinal.repositorio;

import com.ifes.tpfinal.dom.Concesionaria;
import java.util.List;

public interface IRepositorioConcesionaria extends IRepositorio<Concesionaria> {
    List<Concesionaria> findByDomicilio(String domicilio);
}
