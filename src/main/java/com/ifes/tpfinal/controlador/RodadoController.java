package com.ifes.tpfinal.controlador;

import java.util.Arrays;
import java.util.List;

import com.ifes.tpfinal.dom.Auto;
import com.ifes.tpfinal.dom.Camioneta;
import com.ifes.tpfinal.dom.Concesionaria;
import com.ifes.tpfinal.dom.Contacto;
import com.ifes.tpfinal.dom.Rodado;
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

    // ============================================================
    // ALTA SIMPLE DESDE /rodado/crear (sin id de concesionaria)
    // ============================================================

    // GET /rodado/crear  -> muestra el formulario rodado/crear.html
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        List<String> tipos = Arrays.asList("AUTO", "CAMIONETA");
        model.addAttribute("tipos", tipos);
        return "rodado/crear";
    }

    // POST /rodado  -> lo llama el <form th:action="@{/rodado}" method="post">
    // Crea la concesionaria fija si no existe y agrega el rodado nuevo
    @PostMapping
    public String guardarDesdeFormulario(
            @RequestParam String tipo,
            @RequestParam String modelo,
            @RequestParam(required = false) Double precio,
            @RequestParam(name = "automatica", defaultValue = "false") boolean automatica,
            @RequestParam(name = "cuatroPorCuatro", defaultValue = "false") boolean cuatroPorCuatro) {

        // 1) Buscar la concesionaria única
        Concesionaria concesionaria = servicio.listar().stream().findFirst().orElse(null);

        // 2) Si no existe, la creamos junto con su contacto fijo (sin formulario)
        if (concesionaria == null) {
            Contacto contacto = new Contacto();
            contacto.setDomicilio("Av. Siempre Viva 123");   // datos fijos de ejemplo
            contacto.setTelefono("111-222-333");
            
            concesionaria = new Concesionaria();
            concesionaria.setNombre("Concesionaria Única");
            concesionaria.setContacto(contacto);
        }

        // 3) Crear el rodado según el tipo
        Rodado nuevo;

        if ("AUTO".equalsIgnoreCase(tipo)) {
            Auto auto = new Auto();
            auto.setMarca(modelo);              // usamos modelo como marca de ejemplo
            auto.setModelo(modelo);
            auto.setCajaAutomatica(automatica);
            auto.setPuertas(4);                 // valor por defecto
            nuevo = auto;
        } else { // CAMIONETA
            Camioneta cam = new Camioneta();
            cam.setMarca(modelo);
            cam.setModelo(modelo);
            cam.setCajaAutomatica(automatica);
            cam.setCapacidadCarga(1000.0);      // valor por defecto
            nuevo = cam;
        }

        // 4) Asociar el rodado a la concesionaria y persistir
        concesionaria.getRodados().add(nuevo);
        servicio.guardar(concesionaria);

        // 5) Volver a la página principal (o a donde quieras)
        return "redirect:/";
    }

    // ============================================================
    // RESTO DE MÉTODOS (por idCon), por si todavía los usás
    // ============================================================

    @GetMapping("/agregarAuto/{idCon}")
    public String agregarAuto(@PathVariable Long idCon, Model model) {
        model.addAttribute("auto", new Auto());
        model.addAttribute("idCon", idCon);
        return "rodado/auto_form";
    }

    @PostMapping("/guardarAuto/{idCon}")
    public String guardarAuto(@PathVariable Long idCon, @ModelAttribute Auto auto) {
        Concesionaria c = servicio.listar().stream()
                .filter(x -> x.getId().equals(idCon))
                .findFirst()
                .orElse(null);
        if (c != null) {
            c.getRodados().add(auto);
            servicio.guardar(c);
        }
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
        Concesionaria c = servicio.listar().stream()
                .filter(x -> x.getId().equals(idCon))
                .findFirst()
                .orElse(null);
        if (c != null) {
            c.getRodados().add(camioneta);
            servicio.guardar(c);
        }
        return "redirect:/concesionaria/ver/" + idCon;
    }

    // /rodado/informe
    @GetMapping("/informe")
    public String informe(Model model) {
        model.addAttribute("autos", servicio.listar());
        model.addAttribute("camionetas", servicio.listarCamionetas());
        return "rodado/informe";
    }

    @GetMapping("/editar/{idCon}/{idRod}")
    public String editar(@PathVariable Long idCon, @PathVariable Long idRod, Model model) {
        Rodado r = servicio.buscarRodado(idCon, idRod);
        if (r == null) {
            return "redirect:/concesionaria/ver/" + idCon;
        }

        model.addAttribute("idCon", idCon);
        if (r instanceof Auto) {
            model.addAttribute("auto", (Auto) r);
            return "rodado/auto_form";
        } else {
            model.addAttribute("camioneta", (Camioneta) r);
            return "rodado/camioneta_form";
        }
    }

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

    @GetMapping("/eliminar/{idCon}/{idRod}")
    public String eliminar(@PathVariable Long idCon, @PathVariable Long idRod) {
        servicio.eliminarRodado(idCon, idRod);
        return "redirect:/concesionaria/ver/" + idCon;
    }

    // GET /rodado  -> ver listado de rodados
    @GetMapping
    public String listarRodados(Model model) {
    Concesionaria concesionaria = servicio.listar().stream().findFirst().orElse(null);

    // Separamos en dos listas para mostrar en tablas distintas
    java.util.List<Auto> autos = new java.util.ArrayList<>();
    java.util.List<Camioneta> camionetas = new java.util.ArrayList<>();

    if (concesionaria != null && concesionaria.getRodados() != null) {
        for (Rodado r : concesionaria.getRodados()) {
            if (r instanceof Auto) {
                autos.add((Auto) r);
            } else if (r instanceof Camioneta) {
                camionetas.add((Camioneta) r);
            }
        }
    }

    model.addAttribute("autos", autos);
    model.addAttribute("camionetas", camionetas);
    return "rodado/listar";   // templates/rodado/listar.html
}

}
