package com.ifes.tpfinal.controlador;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    // Crea (o recupera) la concesionaria única y agrega el rodado nuevo
    @PostMapping
    public String guardarDesdeFormulario(
            @RequestParam String tipo,
            @RequestParam String modelo,
            @RequestParam(required = false) Double precio,
            @RequestParam(name = "automatica", defaultValue = "false") boolean automatica,
            @RequestParam(name = "cuatroPorCuatro", defaultValue = "false") boolean cuatroPorCuatro) {

        // 1) Obtener la concesionaria única
        Concesionaria concesionaria = servicio.obtenerUnica();

        // 2) Si no existe, la creamos junto con su contacto fijo
        if (concesionaria == null) {
            Contacto contacto = new Contacto();
            contacto.setDomicilio("Av. Siempre Viva 123");
            contacto.setTelefono("111-222-333");

            concesionaria = new Concesionaria();
            concesionaria.setNombre("Concesionaria Única");
            concesionaria.setContacto(contacto);
        }

        // 3) Crear el rodado según el tipo
        Rodado nuevo;

        if ("AUTO".equalsIgnoreCase(tipo)) {
            Auto auto = new Auto();
            auto.setMarca(modelo);             // usamos modelo como marca de ejemplo
            auto.setModelo(modelo);
            auto.setCajaAutomatica(automatica);
            auto.setPuertas(4);                // valor por defecto
            nuevo = auto;
        } else {
            Camioneta cam = new Camioneta();
            cam.setMarca(modelo);
            cam.setModelo(modelo);
            cam.setCajaAutomatica(automatica);
            cam.setCapacidadCarga(1000.0);     // valor por defecto
            nuevo = cam;
        }

        // 4) Asociar el rodado a la concesionaria y persistir
        if (concesionaria.getRodados() == null) {
            concesionaria.setRodados(new ArrayList<>());
        }
        concesionaria.getRodados().add(nuevo);
        servicio.guardar(concesionaria);

        // 5) Volver al listado
        return "redirect:/rodado";
    }

    // ============================================================
    // LISTADO /rodado
    // ============================================================

    @GetMapping
    public String listarRodados(Model model) {
        Concesionaria concesionaria = servicio.obtenerUnica();

        List<Auto> autos = new ArrayList<>();
        List<Camioneta> camionetas = new ArrayList<>();

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

        return "rodado/listar";
    }

    // ============================================================
    // EDITAR (GET) -> abre el formulario correspondiente
    // ============================================================

    @GetMapping("/editar/{idRod}")
    public String editar(@PathVariable Long idRod, Model model) {
        Concesionaria concesionaria = servicio.obtenerUnica();
        if (concesionaria == null || concesionaria.getRodados() == null) {
            return "redirect:/rodado";
        }

        Rodado r = servicio.buscarRodado(idRod);
        if (r == null) {
            return "redirect:/rodado";
        }

        if (r instanceof Auto) {
            model.addAttribute("auto", (Auto) r);
            return "rodado/auto_form";
        } else {
            model.addAttribute("camioneta", (Camioneta) r);
            return "rodado/camioneta_form";
        }
    }

    // ============================================================
    // ACTUALIZAR AUTO (POST del formulario de edición)
    // ============================================================

    @PostMapping("/actualizarAuto/{idRod}")
    public String actualizarAuto(@PathVariable Long idRod, @ModelAttribute Auto form) {
        Concesionaria concesionaria = servicio.obtenerUnica();
        if (concesionaria == null || concesionaria.getRodados() == null) {
            return "redirect:/rodado";
        }

        // Buscamos el auto dentro de ESA MISMA instancia de concesionaria
        for (Rodado r : concesionaria.getRodados()) {
            if (r instanceof Auto && idRod.equals(r.getId())) {
                Auto auto = (Auto) r;
                auto.setMarca(form.getMarca());
                auto.setModelo(form.getModelo());
                auto.setCajaAutomatica(form.isCajaAutomatica());
                auto.setPuertas(form.getPuertas());
                break;
            }
        }

        servicio.guardar(concesionaria);
        return "redirect:/rodado";
    }

    // ============================================================
    // ACTUALIZAR CAMIONETA (POST del formulario de edición)
    // ============================================================

    @PostMapping("/actualizarCamioneta/{idRod}")
    public String actualizarCamioneta(@PathVariable Long idRod, @ModelAttribute Camioneta form) {
        Concesionaria concesionaria = servicio.obtenerUnica();
        if (concesionaria == null || concesionaria.getRodados() == null) {
            return "redirect:/rodado";
        }

        for (Rodado r : concesionaria.getRodados()) {
            if (r instanceof Camioneta && idRod.equals(r.getId())) {
                Camioneta cam = (Camioneta) r;
                cam.setMarca(form.getMarca());
                cam.setModelo(form.getModelo());
                cam.setCajaAutomatica(form.isCajaAutomatica());
                cam.setCapacidadCarga(form.getCapacidadCarga());
                break;
            }
        }

        servicio.guardar(concesionaria);
        return "redirect:/rodado";
    }

    // ============================================================
    // ELIMINAR AUTO
    // ============================================================

    @GetMapping("/eliminarAuto/{idRod}")
    public String eliminarAuto(@PathVariable Long idRod) {
        Concesionaria concesionaria = servicio.obtenerUnica();
        if (concesionaria != null && concesionaria.getRodados() != null) {
            concesionaria.getRodados()
                    .removeIf(r -> (r instanceof Auto) && idRod.equals(r.getId()));
            servicio.guardar(concesionaria);
        }
        return "redirect:/rodado";
    }

    // ============================================================
    // ELIMINAR CAMIONETA
    // ============================================================

    @GetMapping("/eliminarCamioneta/{idRod}")
    public String eliminarCamioneta(@PathVariable Long idRod) {
        Concesionaria concesionaria = servicio.obtenerUnica();
        if (concesionaria != null && concesionaria.getRodados() != null) {
            concesionaria.getRodados()
                    .removeIf(r -> (r instanceof Camioneta) && idRod.equals(r.getId()));
            servicio.guardar(concesionaria);
        }
        return "redirect:/rodado";
    }
}
