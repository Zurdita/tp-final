package com.ifes.tpfinal.servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifes.tpfinal.dom.Auto;
import com.ifes.tpfinal.dom.Camioneta;
import com.ifes.tpfinal.dom.Concesionaria;
import com.ifes.tpfinal.dom.Rodado;
import com.ifes.tpfinal.repositorio.RepositorioConcesionaria;

@Service
public class ServicioConcesionaria {

    @Autowired
    private RepositorioConcesionaria repo;

    // --------- CRUD básico de Concesionaria ---------

    public Concesionaria guardar(Concesionaria c) {
        repo.guardar(c);
        return c;
    }

    public List<Concesionaria> listar() {
        return repo.listar();
    }

    /**
     * Devuelve la "única" concesionaria del sistema
     * (la primera de la lista, o null si no hay ninguna).
     */
    public Concesionaria obtenerUnica() {
        return listar().stream().findFirst().orElse(null);
    }

    public Concesionaria buscarConcesionaria(Long id) {
        return listar().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void actualizarConcesionaria(Concesionaria c) {
        // con JDO, volver a guardar la entidad actualizada es suficiente
        repo.guardar(c);
    }

    // --------- Lógica de rodados ---------

    /**
     * Busca un rodado por id dentro de la concesionaria única.
     */
    public Rodado buscarRodado(Long idRod) {
        Concesionaria c = obtenerUnica();
        if (c == null || c.getRodados() == null) return null;

        return c.getRodados().stream()
                .filter(r -> r.getId().equals(idRod))
                .findFirst()
                .orElse(null);
    }

    /**
     * Elimina un rodado por id de la concesionaria única.
     */
    public void eliminarRodado(Long idRod) {
        Concesionaria c = obtenerUnica();
        if (c == null || c.getRodados() == null) return;

        c.getRodados().removeIf(r -> r.getId().equals(idRod));
        // Persistimos el cambio
        actualizarConcesionaria(c);
    }

    public List<Auto> listarAutos() {
        Concesionaria c = obtenerUnica();
        List<Auto> res = new ArrayList<>();
        if (c != null && c.getRodados() != null) {
            for (Rodado r : c.getRodados()) {
                if (r instanceof Auto) {
                    res.add((Auto) r);
                }
            }
        }
        return res;
    }

    public List<Camioneta> listarCamionetas() {
        Concesionaria c = obtenerUnica();
        List<Camioneta> res = new ArrayList<>();
        if (c != null && c.getRodados() != null) {
            for (Rodado r : c.getRodados()) {
                if (r instanceof Camioneta) {
                    res.add((Camioneta) r);
                }
            }
        }
        return res;
    }
}
