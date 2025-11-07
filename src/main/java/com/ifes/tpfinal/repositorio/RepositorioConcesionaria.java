
package com.ifes.tpfinal.repositorio;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import org.springframework.stereotype.Repository;

import com.ifes.tpfinal.dom.Concesionaria;

@Repository
public class RepositorioConcesionaria extends Repositorio<Concesionaria> {

    // El método guardar() y otros son heredados de Repositorio<T>

    // La lógica de JDO es más clara si se usa la clase concreta, pero JDO
    // a menudo necesita una clase abstracta para la metadata.
    // Asumo que oclass() está bien.
    protected String oclass() { return Concesionaria.class.getName(); } 

    public Concesionaria findByDomicilio(String domicilio) {
        PersistenceManager pm = pmf.getPersistenceManager();
        try {
            Query q = pm.newQuery(Concesionaria.class, "domicilio == d");
            q.declareParameters("String d");
            @SuppressWarnings("unchecked")
            java.util.List<Concesionaria> res = (java.util.List<Concesionaria>) q.execute(domicilio);
            return res.isEmpty()?null:res.get(0);
        } finally { 
            pm.close(); 
        }
    }
}