package com.ifes.tpfinal.servicio;


import com.ifes.tpfinal.dom.Auto;
import com.ifes.tpfinal.dom.Camioneta;
import com.ifes.tpfinal.dom.Concesionaria;
import com.ifes.tpfinal.dom.Rodado;
import com.ifes.tpfinal.repositorio.RepositorioConcesionaria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioConcesionaria {

    @Autowired
    private RepositorioConcesionaria repo;

    public void guardar(Concesionaria c) {
        repo.guardar(c);
    }

    public List<Concesionaria> listar() {
        return repo.listar();
    }

    public void eliminar(Concesionaria c) {
        repo.eliminar(c);
    }

    public String informe() {
        int autos = 0, camionetas = 0;
        for (Concesionaria c : listar()) {
            for (Rodado r : c.getRodados()) {
                if (r instanceof Auto)
                    autos++;
                else if (r instanceof Camioneta)
                    camionetas++;
            }
        }
        return "Autos: " + autos + " | Camionetas: " + camionetas;
    }

    public Object listarCamionetas() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarCamionetas'");
    }

    public Concesionaria buscarConcesionaria(Long idCon) {
        return listar().stream().filter(c -> c.getId().equals(idCon)).findFirst().orElse(null);
    }

    public Rodado buscarRodado(Long idCon, Long idRod) {
        Concesionaria c = buscarConcesionaria(idCon);
        if (c == null || c.getRodados() == null)
            return null;
        return c.getRodados().stream().filter(r -> {
            try {
                java.lang.reflect.Method m = r.getClass().getMethod("getId");
                Long id = (Long) m.invoke(r);
                return idRod.equals(id);
            } catch (Exception e) {
                return false;
            }
        }).findFirst().orElse(null);
    }

    public void actualizarConcesionaria(Concesionaria c) {
        guardar(c);
    }

    public void eliminarRodado(Long idCon, Long idRod) {
        Concesionaria c = buscarConcesionaria(idCon);
        if (c == null || c.getRodados() == null)
            return;
        Rodado r = buscarRodado(idCon, idRod);
        if (r != null) {
            c.getRodados().remove(r);
            guardar(c);
        }
    }

}
