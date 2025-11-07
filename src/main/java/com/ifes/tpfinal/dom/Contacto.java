package com.ifes.tpfinal.dom;

import javax.jdo.annotations.*;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable(detachable = "true")
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME, column="tipo_contacto")

public class Contacto {

    private String domicilio;
    private String telefono;

    public Contacto() {}

    public Contacto(String domicilio, String telefono) {
        this.domicilio = domicilio;
        this.telefono = telefono;
    }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return "Contacto{domicilio='" + domicilio + "', telefono='" + telefono + "'}";
    }
}
