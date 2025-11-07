package com.ifes.tpfinal.repositorio;


import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import org.springframework.stereotype.Repository;

import com.ifes.tpfinal.dom.Concesionaria;

@Repository
public class RepositorioConcesionaria extends Repositorio<Concesionaria> {

    @Override
    protected String oclass() { return Concesionaria.class.getName(); }

    public Concesionaria findByDomicilio(String domicilio) {
        PersistenceManager pm = pmf.getPersistenceManager();
        try {
            Query q = pm.newQuery(Concesionaria.class, "domicilio == d");
            q.declareParameters("String d");
            java.util.List<Concesionaria> res = (java.util.List<Concesionaria>) q.execute(domicilio);
            return res.isEmpty()?null:res.get(0);
        } finally { pm.close(); }
    }
}
