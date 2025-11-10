
package com.ifes.tpfinal.repositorio;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import org.springframework.stereotype.Repository;

import com.ifes.tpfinal.dom.Concesionaria;

import org.springframework.stereotype.Repository;

@Repository
public class RepositorioConcesionaria extends Repositorio<Concesionaria> {

    @Override
    protected Class<Concesionaria> oclass() {
        return Concesionaria.class;
    }
}