package com.ifes.tpfinal.servicio;

import com.ifes.tpfinal.dom.Concesionaria;
import com.ifes.tpfinal.repositorio.IRepositorioConcesionaria;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioConcesionaria extends Servicio<Concesionaria> implements IServicioConcesionaria {

    private final IRepositorioConcesionaria repoCon;

    public ServicioConcesionaria(IRepositorioConcesionaria repoCon) {
        super(repoCon);
        this.repoCon = repoCon;
    }

    @Override
    public List<Concesionaria> consultarPorDomicilio(String domicilio) {
        return repoCon.findByDomicilio(domicilio);
    }
}
