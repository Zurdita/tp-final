package com.ifes.tpfinal.controlador;

import com.ifes.tpfinal.dom.Concesionaria;
import com.ifes.tpfinal.dom.Contacto;
import com.ifes.tpfinal.servicio.ServicioConcesionaria;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/concesionaria")
public class ConcesionariaController {

    private final ServicioConcesionaria servicio;

    public ConcesionariaController(ServicioConcesionaria servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public String listar(Model model) {
        List<Concesionaria> todas = servicio.listar();
        model.addAttribute("concesionarias", todas);
        return "concesionaria/consultar";
    }

    // @GetMapping("/domicilio")
    // public String porDomicilio(@RequestParam("dir") String dir, Model model) {
    // List<Concesionaria> res = servicio.consultarPorDomicilio(dir);
    // model.addAttribute("concesionarias", res);
    // model.addAttribute("filtro", dir);
    // return "concesionaria/consultar";
    // }

    // @GetMapping("/{id}/informe")
    // public String informe(@PathVariable Long id, Model model) {
    // Concesionaria c = servicio.buscarPorId(Concesionaria.class, id);
    // model.addAttribute("concesionaria", c);
    // model.addAttribute("textoInforme", c != null ? c.informe() : "No
    // encontrada");
    // return "concesionaria/informe";
    // }

    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("concesionaria", new Concesionaria());
        return "concesionaria/crear";
    }

    /**
     * Procesa la solicitud POST del formulario (POST /concesionaria/guardar)
     */
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("concesionaria") Concesionaria concesionaria) {

        try {
            servicio.guardar(concesionaria);
        } catch (Exception e) {
            // Manejar si el guardado falla, quizá redirigir a un error o al mismo
            // formulario
            e.printStackTrace();
            // Podrías retornar aquí a "concesionaria/crear" si quieres que el usuario lo
            // reintente
            return "redirect:/concesionaria/crear?error=true";
        }

        // CORRECCIÓN: Redirige a la ruta existente para listar (/concesionaria)
        return "redirect:/concesionaria";
    }
}
