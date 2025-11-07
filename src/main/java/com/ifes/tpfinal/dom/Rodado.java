package com.ifes.tpfinal.dom;


import javax.jdo.annotations.*;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public abstract class Rodado {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    private Long id;

    private String marca;
    private String modelo;
    private boolean cajaAutomatica;

    public Rodado() {}

    public Rodado(String marca, String modelo, boolean cajaAutomatica) {
        this.marca = marca;
        this.modelo = modelo;
        this.cajaAutomatica = cajaAutomatica;
    }

    public Long getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public boolean isCajaAutomatica() { return cajaAutomatica; }

    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setCajaAutomatica(boolean cajaAutomatica) { this.cajaAutomatica = cajaAutomatica; }
}
