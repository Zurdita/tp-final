package com.ifes.tpfinal.servicio;

import com.ifes.tpfinal.dom.Concesionaria;
import java.util.List;

public interface IServicioConcesionaria extends IServicio<Concesionaria> {
    List<Concesionaria> consultarPorDomicilio(String domicilio);
}
