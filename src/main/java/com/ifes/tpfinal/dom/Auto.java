package com.ifes.tpfinal.dom;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable(detachable = "true")
public class Auto extends Rodado {

    private boolean automatica;

    public Auto() {}

    public Auto(String modelo, Double precio, Concesionaria c, boolean automatica) {
        super(modelo, precio, c);
        this.automatica = automatica;
    }

    public boolean isAutomatica() { return automatica; }
    public void setAutomatica(boolean automatica) { this.automatica = automatica; }

    @Override
    public String toString() {
        return "Auto{" + super.toString() + ", automatica=" + automatica + "}";
    }
}
