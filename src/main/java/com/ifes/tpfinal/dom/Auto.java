package com.ifes.tpfinal.dom;



import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable(detachable = "true")
public class Auto extends Rodado {
    private int puertas;

    public Auto() {}

    public Auto(String marca, String modelo, boolean cajaAutomatica, int puertas) {
        super(marca, modelo, cajaAutomatica);
        this.puertas = puertas;
    }

    public int getPuertas() { return puertas; }
    public void setPuertas(int puertas) { this.puertas = puertas; }
}
