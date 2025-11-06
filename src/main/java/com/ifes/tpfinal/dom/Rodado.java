package com.ifes.tpfinal.dom;

import javax.jdo.annotations.*;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public abstract class Rodado {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    private Long id;

    @Persistent(defaultFetchGroup = "true")
    private String modelo;

    private Double precio;

    @Persistent(defaultFetchGroup = "true")
    private Concesionaria concesionaria;

    protected Rodado() {}

    public Rodado(String modelo, Double precio, Concesionaria concesionaria) {
        this.modelo = modelo;
        this.precio = precio;
        this.concesionaria = concesionaria;
    }

    public Long getId() { return id; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Concesionaria getConcesionaria() { return concesionaria; }
    public void setConcesionaria(Concesionaria concesionaria) { this.concesionaria = concesionaria; }

    @Override
    public String toString() {
        return "Rodado{id=" + id + ", modelo='" + modelo + "', precio=" + precio + "}";
    }
}
