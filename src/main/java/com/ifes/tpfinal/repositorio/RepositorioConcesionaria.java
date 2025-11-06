package com.ifes.tpfinal.repositorio;

import com.ifes.tpfinal.dom.Concesionaria;
import org.springframework.stereotype.Repository;

import javax.jdo.*;
import java.util.List;

@Repository
public class RepositorioConcesionaria extends Repositorio<Concesionaria> implements IRepositorioConcesionaria {

    private final PersistenceManagerFactory pmf;

    public RepositorioConcesionaria(PersistenceManagerFactory pmf) {
        super(pmf);
        this.pmf = pmf;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Concesionaria> findByDomicilio(String domicilio) {
        PersistenceManager pm = pmf.getPersistenceManager();
        try {
            Query<Concesionaria> q = pm.newNamedQuery(Concesionaria.class, "Concesionaria.findByDomicilio");
            List<Concesionaria> res = (List<Concesionaria>) q.execute(domicilio);
            return (List<Concesionaria>) pm.detachCopy(res);
        } finally {
            pm.close();
        }
    }
}
