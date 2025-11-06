package com.ifes.tpfinal.dom;

import javax.jdo.annotations.*;
import java.util.ArrayList;
import java.util.List;

@PersistenceCapable(detachable = "true")
@Queries({
  @Query(
    name = "Concesionaria.findByDomicilio",
    language = "JDOQL",
    value = "SELECT FROM com.ifes.tpfinal.dom.Concesionaria WHERE this.contacto.domicilio == :domicilio")
})
public class Concesionaria {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    private Long id;

    private String nombre;

    @Persistent(defaultFetchGroup = "true")
    private Contacto contacto;

    @Element(dependent = "true")
    private List<Rodado> rodados = new ArrayList<>();

    public Concesionaria() {}

    public Concesionaria(String nombre, Contacto contacto) {
        this.nombre = nombre;
        this.contacto = contacto;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Contacto getContacto() { return contacto; }
    public void setContacto(Contacto contacto) { this.contacto = contacto; }
    public List<Rodado> getRodados() { return rodados; }

    public void addRodado(Rodado r) { rodados.add(r); }

    public String informe() {
        long autos = rodados.stream().filter(r -> r instanceof Auto).count();
        long camionetas = rodados.stream().filter(r -> r instanceof Camioneta).count();
        return "Informe de " + nombre + " -> Autos: " + autos + ", Camionetas: " + camionetas;
    }

    @Override
    public String toString() {
        return "Concesionaria{id=" + id + ", nombre='" + nombre + "', contacto=" + contacto + "}";
    }
}
