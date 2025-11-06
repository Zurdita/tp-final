package com.ifes.tpfinal.controlador;

import com.ifes.tpfinal.dom.*;
import com.ifes.tpfinal.repositorio.IRepositorioConcesionaria;
import com.ifes.tpfinal.servicio.IServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rodados")
public class RodadoController {

    private final IServicio<Rodado> servicioRodado;
    private final IRepositorioConcesionaria repoCon;

    public RodadoController(IServicio<Rodado> servicioRodado, IRepositorioConcesionaria repoCon) {
        this.servicioRodado = servicioRodado;
        this.repoCon = repoCon;
    }

    @GetMapping
    public String listar(Model model) {
        List<Rodado> todos = servicioRodado.listar(Rodado.class);
        model.addAttribute("rodados", todos);
        return "rodado/consultar";
    }

    @GetMapping("/crear")
    public String crearForm(Model model) {
        model.addAttribute("tipos", List.of("AUTO", "CAMIONETA"));
        return "rodado/crear";
    }

    @PostMapping
    public String crear(@RequestParam String tipo,
                        @RequestParam String modelo,
                        @RequestParam Double precio,
                        @RequestParam(required = false, defaultValue = "false") boolean automatica,
                        @RequestParam(required = false, defaultValue = "false") boolean cuatroPorCuatro) {

        Concesionaria c = repoCon.listar(Concesionaria.class).stream().findFirst().orElse(null);

        Rodado r;
        if ("AUTO".equalsIgnoreCase(tipo)) {
            r = new Auto(modelo, precio, c, automatica);
        } else {
            r = new Camioneta(modelo, precio, c, cuatroPorCuatro);
        }
        servicioRodado.guardar(r);
        return "redirect:/rodados";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        Rodado r = servicioRodado.buscarPorId(Rodado.class, id);
        model.addAttribute("r", r);
        model.addAttribute("tipos", List.of("AUTO", "CAMIONETA"));
        model.addAttribute("esAuto", (r instanceof Auto));
        model.addAttribute("esCamioneta", (r instanceof Camioneta));
        return "rodado/editar";
    }

    @PostMapping("/{id}/editar")
    public String editar(@PathVariable Long id,
                         @RequestParam String modelo,
                         @RequestParam Double precio,
                         @RequestParam(required = false, defaultValue = "false") boolean automatica,
                         @RequestParam(required = false, defaultValue = "false") boolean cuatroPorCuatro) {

        Rodado r = servicioRodado.buscarPorId(Rodado.class, id);
        if (r != null) {
            r.setModelo(modelo);
            r.setPrecio(precio);
            if (r instanceof Auto) ((Auto) r).setAutomatica(automatica);
            if (r instanceof Camioneta) ((Camioneta) r).setCuatroPorCuatro(cuatroPorCuatro);
            servicioRodado.guardar(r);
        }
        return "redirect:/rodados";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        servicioRodado.eliminar(Rodado.class, id);
        return "redirect:/rodados";
    }
}
