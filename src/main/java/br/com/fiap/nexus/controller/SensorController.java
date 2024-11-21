package br.com.fiap.nexus.controller;

import br.com.fiap.nexus.dto.SensorDTO;
import br.com.fiap.nexus.service.SensorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sensors")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    private String redirectToList(String entityType) {
        return "redirect:/" + entityType + "/listar"; // Refatoração do redirecionamento
    }

    @GetMapping("/listar")
    public String listarSensors(Model model) {
        List<SensorDTO> sensors = sensorService.listarTodosSensores();
        model.addAttribute("sensors", sensors);
        return "sensor/listar"; // Página que lista todos os sensores
    }

    @GetMapping("/{id}")
    public String obterSensorPorId(@PathVariable Long id, Model model) {
        return sensorService.encontrarSensorPorId(id)
                .map(sensor -> {
                    model.addAttribute("sensor", sensor);
                    return "sensor/detalhes"; // Página de detalhes do sensor
                })
                .orElse("error/404"); // Página de erro caso não encontre o sensor
    }

    @GetMapping("/cadastrar")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("sensorDTO", new SensorDTO());
        return "sensor/cadastrar"; // Página para cadastrar novo sensor
    }

    @PostMapping("/cadastrar")
    public String cadastrarSensor(@ModelAttribute SensorDTO sensorDTO) {
        sensorService.salvarSensor(sensorDTO);
        return redirectToList("sensors"); // Redireciona para a lista de sensores
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        return sensorService.encontrarSensorPorId(id)
                .map(sensor -> {
                    model.addAttribute("sensorDTO", sensor);
                    return "sensor/editar"; // Página para editar sensor
                })
                .orElse("error/404"); // Página de erro caso não encontre o sensor
    }

    @PostMapping("/editar/{id}")
    public String atualizarSensor(@PathVariable Long id, @ModelAttribute SensorDTO sensorDTO) {
        sensorDTO.setId(id); // Define o ID para a atualização
        sensorService.atualizarSensor(sensorDTO);
        return redirectToList("sensors"); // Redireciona para a lista de sensores
    }

    @GetMapping("/deletar/{id}")
    public String confirmarDelecao(@PathVariable Long id, Model model) {
        return sensorService.encontrarSensorPorId(id)
                .map(sensor -> {
                    model.addAttribute("sensor", sensor);
                    return "sensor/deletar"; // Página de confirmação de deleção
                })
                .orElse("error/404"); // Página de erro caso não encontre o sensor
    }

    @PostMapping("/deletar/{id}")
    public String deletarSensor(@PathVariable Long id, Model model) {
        try {
            sensorService.deletarSensor(id);
            return redirectToList("sensors"); // Redireciona para a lista de sensores
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "Sensor não encontrado");
            return "error/404"; // Redireciona para uma página de erro
        }
    }
}
