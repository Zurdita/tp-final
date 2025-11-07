package com.ifes.tpfinal.dom;


import javax.jdo.annotations.*;

@PersistenceCapable(detachable = "true")
public class Camioneta extends Rodado {
    private double capacidadCarga;

    public Camioneta() {}

    public Camioneta(String marca, String modelo, boolean cajaAutomatica, double capacidadCarga) {
        super(marca, modelo, cajaAutomatica);
        this.capacidadCarga = capacidadCarga;
    }

    public double getCapacidadCarga() { return capacidadCarga; }
    public void setCapacidadCarga(double capacidadCarga) { this.capacidadCarga = capacidadCarga; }
}
