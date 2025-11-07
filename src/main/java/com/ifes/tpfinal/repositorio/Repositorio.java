package com.ifes.tpfinal.repositorio;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class Repositorio<T> implements IRepositorio<T> {

    @Autowired
    protected PersistenceManagerFactory pmf;

    @Override
    public void guardar(T o) {
        PersistenceManager pm = pmf.getPersistenceManager();
        try {
            pm.makePersistent(o);
        } finally {
            pm.close();
        }
    }

    @Override
    public List<T> listar() {
        PersistenceManager pm = pmf.getPersistenceManager();
        try {
            Query query = pm.newQuery(oclass());
            return (List<T>) query.executeList();
        } finally {
            pm.close();
        }
    }

    @Override
    public void eliminar(T o) {
        PersistenceManager pm = pmf.getPersistenceManager();
        try {
            pm.deletePersistent(pm.detachCopy(o));
        } finally {
            pm.close();
        }
    }

    // Cada repositorio concreto debe devolver:
    // return ClaseDominio.class.getName();
    protected abstract String oclass();
}
