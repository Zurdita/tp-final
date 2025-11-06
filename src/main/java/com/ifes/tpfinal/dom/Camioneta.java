package com.ifes.tpfinal.dom;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable(detachable = "true")
public class Camioneta extends Rodado {
    @Persistent
    private boolean cuatroPorCuatro;

    public Camioneta() {}

    public Camioneta(String modelo, Double precio, Concesionaria c, boolean cuatroPorCuatro) {
        super(modelo, precio, c);
        this.cuatroPorCuatro = cuatroPorCuatro;
    }

    public boolean isCuatroPorCuatro() { return cuatroPorCuatro; }
    public void setCuatroPorCuatro(boolean cuatroPorCuatro) { this.cuatroPorCuatro = cuatroPorCuatro; }

    @Override
    public String toString() {
        return "Camioneta{" + super.toString() + ", cuatroPorCuatro=" + cuatroPorCuatro + "}";
    }
}
