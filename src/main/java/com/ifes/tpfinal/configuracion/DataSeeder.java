package com.ifes.tpfinal.configuracion;

import com.ifes.tpfinal.dom.Concesionaria;
import com.ifes.tpfinal.dom.Contacto;
import com.ifes.tpfinal.repositorio.IRepositorioConcesionaria;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder implements CommandLineRunner {

    private final IRepositorioConcesionaria repoCon;

    @Value("${concesionaria.nombre}")
    private String nombre;
    @Value("${concesionaria.domicilio}")
    private String domicilio;
    @Value("${concesionaria.telefono}")
    private String telefono;

    public DataSeeder(IRepositorioConcesionaria repoCon) {
        this.repoCon = repoCon;
    }

    @Override
    public void run(String... args) {
        List<Concesionaria> ya = repoCon.findByDomicilio(domicilio);
        if (ya == null || ya.isEmpty()) {
            Concesionaria c = new Concesionaria(nombre, new Contacto(domicilio, telefono));
            repoCon.guardar(c);
        }
    }
}
