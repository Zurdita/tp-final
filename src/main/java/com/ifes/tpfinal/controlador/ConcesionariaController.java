package com.ifes.tpfinal.controlador;

import com.ifes.tpfinal.dom.Concesionaria;
import com.ifes.tpfinal.servicio.IServicioConcesionaria;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/concesionarias")
public class ConcesionariaController {

    private final IServicioConcesionaria servicio;

    public ConcesionariaController(IServicioConcesionaria servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public String listar(Model model) {
        List<Concesionaria> todas = servicio.listar(Concesionaria.class);
        model.addAttribute("concesionarias", todas);
        return "concesionaria/consultar";
    }

    @GetMapping("/domicilio")
    public String porDomicilio(@RequestParam("dir") String dir, Model model) {
        List<Concesionaria> res = servicio.consultarPorDomicilio(dir);
        model.addAttribute("concesionarias", res);
        model.addAttribute("filtro", dir);
        return "concesionaria/consultar";
    }

    @GetMapping("/{id}/informe")
    public String informe(@PathVariable Long id, Model model) {
        Concesionaria c = servicio.buscarPorId(Concesionaria.class, id);
        model.addAttribute("concesionaria", c);
        model.addAttribute("textoInforme", c != null ? c.informe() : "No encontrada");
        return "concesionaria/informe";
    }
}
