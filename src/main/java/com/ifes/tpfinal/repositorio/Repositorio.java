package com.ifes.tpfinal.repositorio;

import com.ifes.tpfinal.dom.Concesionaria;
import com.ifes.tpfinal.dom.Rodado;
import org.springframework.stereotype.Repository;

import javax.jdo.*;
import javax.jdo.Transaction;
import java.util.List;

@Repository
public class Repositorio<T> implements IRepositorio<T> {

    private final PersistenceManagerFactory pmf;

    public Repositorio(PersistenceManagerFactory pmf) {
        this.pmf = pmf;
    }

    @Override
    public T guardar(T entidad) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            T persistido = pm.makePersistent(entidad);
            tx.commit();
            return pm.detachCopy(persistido);
        } finally {
            if (tx.isActive()) tx.rollback();
            pm.close();
        }
    }

    @Override
    public T buscarPorId(Class<T> clazz, Object id) {
        PersistenceManager pm = pmf.getPersistenceManager();
        try {
            T obj = pm.getObjectById(clazz, id);
            return pm.detachCopy(obj);
        } catch (JDOObjectNotFoundException e) {
            return null;
        } finally {
            pm.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> listar(Class<T> clazz) {
        PersistenceManager pm = pmf.getPersistenceManager();
        try {
            Query<T> q = pm.newQuery(clazz);
            List<T> res = (List<T>) q.execute();
            return (List<T>) pm.detachCopy(res);
        } finally {
            pm.close();
        }
    }

    @Override
    public void eliminar(Class<T> clazz, Object id) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Object obj = pm.getObjectById(clazz, id);
            if (obj instanceof Rodado) {
                Rodado r = (Rodado) obj;
                Concesionaria c = r.getConcesionaria();
                if (c != null) {
                    c.getRodados().remove(r);
                }
            }
            pm.deletePersistent(obj);
            tx.commit();
        } catch (JDOObjectNotFoundException e) {
            if (tx.isActive()) tx.rollback();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            pm.close();
        }
    }
}
