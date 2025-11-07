package com.ifes.tpfinal.dom;

import javax.jdo.annotations.*;
import java.util.List;
import java.util.ArrayList; // Sugerido para inicializar la lista

@PersistenceCapable(detachable="true")
public class Concesionaria {
    
    @PrimaryKey
    @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
    private Long id;

    private String nombre;
    
    // Campo 'domicilio' fue OMITIDO según la solicitud y el diagrama.
    // Solo se incluye 'id' y 'nombre' como propiedades directas.
    
    @Persistent(defaultFetchGroup="true")
    private Contacto contacto;

    @Persistent(defaultFetchGroup="true")
    private List<Rodado> rodados;

    public Concesionaria() {
        // Inicialización segura para la lista
        this.rodados = new ArrayList<>(); 
    }
    
    // Constructor con los campos que se ven en el diagrama (id y nombre) más las relaciones
    public Concesionaria(String nombre, Contacto contacto, List<Rodado> rodados) {
        this.nombre = nombre;
        this.contacto = contacto;
        this.rodados = rodados != null ? rodados : new ArrayList<>();
    }
    
    // Se elimina el argumento 'domicilio' del constructor para reflejar la estructura del diagrama

    public Long getId() { return id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    // 🛑 Métodos getDomicilio() y setDomicilio() son ELIMINADOS
    // ya que no hay campo 'domicilio'

    public Contacto getContacto() { return contacto; }
    public void setContacto(Contacto contacto) { this.contacto = contacto; }
    
    public List<Rodado> getRodados() { return rodados; }
    public void setRodados(List<Rodado> rodados) { this.rodados = rodados; }
    
    // Si la imagen tiene un método 'informe()', también debe estar aquí.
    public String informe() {
        return "Concesionaria: " + this.nombre + ", Contacto: " + (contacto != null ? "Sí" : "No");
    }
}