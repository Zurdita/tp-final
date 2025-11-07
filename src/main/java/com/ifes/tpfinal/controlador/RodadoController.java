package com.ifes.tpfinal.controlador;


import com.ifes.tpfinal.dom.*;
import com.ifes.tpfinal.dom.Camioneta;
import com.ifes.tpfinal.dom.Concesionaria;
import com.ifes.tpfinal.servicio.ServicioConcesionaria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rodado")
public class RodadoController {

    @Autowired
    private ServicioConcesionaria servicio;

    @GetMapping("/agregarAuto/{idCon}")
    public String agregarAuto(@PathVariable Long idCon, Model model) {
        model.addAttribute("auto", new Auto());
        model.addAttribute("idCon", idCon);
        return "rodado/auto_form";
    }

    @PostMapping("/guardarAuto/{idCon}")
    public String guardarAuto(@PathVariable Long idCon, @ModelAttribute Auto auto) {
        Concesionaria c = servicio.listar().stream().filter(x -> x.getId().equals(idCon)).findFirst().orElse(null);
        c.getRodados().add(auto);
        servicio.guardar(c);
        return "redirect:/concesionaria/ver/" + idCon;
    }

    @GetMapping("/agregarCamioneta/{idCon}")
    public String agregarCamioneta(@PathVariable Long idCon, Model model) {
        model.addAttribute("camioneta", new Camioneta());
        model.addAttribute("idCon", idCon);
        return "rodado/camioneta_form";
    }

    @PostMapping("/guardarCamioneta/{idCon}")
    public String guardarCamioneta(@PathVariable Long idCon, @ModelAttribute Camioneta camioneta) {
        Concesionaria c = servicio.listar().stream().filter(x -> x.getId().equals(idCon)).findFirst().orElse(null);
        c.getRodados().add(camioneta);
        servicio.guardar(c);
        return "redirect:/concesionaria/ver/" + idCon;
    }
    @GetMapping("/rodado/crear")
public String seleccionarTipo() {
    return "rodado/seleccionar_tipo";
}

@GetMapping("/rodado/informe")
public String informe(Model model) {
    model.addAttribute("autos", servicio.listar());
    model.addAttribute("camionetas", servicio.listarCamionetas());
    return "rodado/informe";
}

// EDITAR (detecta tipo y abre el form correcto)
@GetMapping("/editar/{idCon}/{idRod}")
public String editar(@PathVariable Long idCon, @PathVariable Long idRod, Model model) {
    Rodado r = servicio.buscarRodado(idCon, idRod);
    if (r == null) return "redirect:/concesionaria/ver/" + idCon;

    model.addAttribute("idCon", idCon);
    if (r instanceof Auto) {
        model.addAttribute("auto", (Auto) r);
        return "rodado/auto_form";
    } else {
        model.addAttribute("camioneta", (Camioneta) r);
        return "rodado/camioneta_form";
    }
}

// ACTUALIZAR AUTO
@PostMapping("/actualizarAuto/{idCon}/{idRod}")
public String actualizarAuto(@PathVariable Long idCon, @PathVariable Long idRod, @ModelAttribute Auto form) {
    Concesionaria c = servicio.buscarConcesionaria(idCon);
    Auto existente = (Auto) servicio.buscarRodado(idCon, idRod);
    if (c != null && existente != null) {
        existente.setMarca(form.getMarca());
        existente.setModelo(form.getModelo());
        existente.setCajaAutomatica(form.isCajaAutomatica());
        existente.setPuertas(form.getPuertas());
        servicio.actualizarConcesionaria(c);
    }
    return "redirect:/concesionaria/ver/" + idCon;
}

// ACTUALIZAR CAMIONETA
@PostMapping("/actualizarCamioneta/{idCon}/{idRod}")
public String actualizarCamioneta(@PathVariable Long idCon, @PathVariable Long idRod, @ModelAttribute Camioneta form) {
    Concesionaria c = servicio.buscarConcesionaria(idCon);
    Camioneta existente = (Camioneta) servicio.buscarRodado(idCon, idRod);
    if (c != null && existente != null) {
        existente.setMarca(form.getMarca());
        existente.setModelo(form.getModelo());
        existente.setCajaAutomatica(form.isCajaAutomatica());
        existente.setCapacidadCarga(form.getCapacidadCarga());
        servicio.actualizarConcesionaria(c);
    }
    return "redirect:/concesionaria/ver/" + idCon;
}

// ELIMINAR
@GetMapping("/eliminar/{idCon}/{idRod}")
public String eliminar(@PathVariable Long idCon, @PathVariable Long idRod) {
    servicio.eliminarRodado(idCon, idRod);
    return "redirect:/concesionaria/ver/" + idCon;
}


}
