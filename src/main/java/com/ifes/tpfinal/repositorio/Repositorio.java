package com.ifes.tpfinal.repositorio;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class Repositorio<T> implements IRepositorio<T> {

    @Autowired
    protected PersistenceManagerFactory pmf;

    @Override
    public void guardar(T o) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            pm.makePersistent(o);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> listar() {
        PersistenceManager pm = pmf.getPersistenceManager();
        try {
            // 🚩 ANTES: pm.newQuery(oclass());  // oclass() devolvía String
            // ✅ AHORA: usamos Class<T>, no String, así NO es single-string JDOQL
            Query<T> query = pm.newQuery(oclass());
            List<T> resultados = (List<T>) query.executeList();
            // Si querés objetos detach:
            return (List<T>) pm.detachCopyAll(resultados);
        } finally {
            pm.close();
        }
    }

    @Override
    public void eliminar(T o) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            pm.deletePersistent(pm.detachCopy(o));
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    // ANTES: protected abstract String oclass();
    // AHORA:
    protected abstract Class<T> oclass();
}