package br.com.fiap.nexus.controller;

import br.com.fiap.nexus.dto.DeviceDTO;
import br.com.fiap.nexus.dto.SensorDTO;
import br.com.fiap.nexus.entity.Sensor;
import br.com.fiap.nexus.service.DeviceService;
import br.com.fiap.nexus.service.SensorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SensorService sensorService;  // Adicionando o SensorService

    private String redirectToList(String entityType) {
        return "redirect:/" + entityType + "/listar"; // Refatoração do redirecionamento
    }

    @GetMapping("/listar")
    public String listarDevices(Model model) {
        List<DeviceDTO> devices = deviceService.listarTodosDispositivos();
        model.addAttribute("devices", devices);
        return "devices/listar"; // Página que lista todos os dispositivos
    }

    @GetMapping("/{id}")
    public String obterDevicePorId(@PathVariable Long id, Model model) {
        return deviceService.encontrarDevicePorId(id)
                .map(device -> {
                    model.addAttribute("device", device);
                    return "devices/detalhes"; // Página de detalhes do dispositivo
                })
                .orElse("error/404"); // Página de erro caso não encontre o dispositivo
    }

    @GetMapping("/cadastrar")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("deviceDTO", new DeviceDTO());

        // Passando os sensores para o modelo
        List<SensorDTO> sensors = sensorService.listarTodosSensores();
        model.addAttribute("sensors", sensors);

        return "devices/cadastrar"; // Página para cadastrar novo dispositivo
    }

    @PostMapping("/cadastrar")
    public String cadastrarDevice(@ModelAttribute DeviceDTO deviceDTO) {
        deviceService.salvarDevice(deviceDTO);
        return redirectToList("devices"); // Redireciona para a lista de dispositivos
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        return deviceService.encontrarDevicePorId(id)
                .map(device -> {
                    model.addAttribute("deviceDTO", device);
                    model.addAttribute("sensors", sensorService.listarTodosSensores());
                    return "devices/editar";
                })
                .orElse("error/404");
    }

    @PostMapping("/editar/{id}")
    public String atualizarDevice(@PathVariable Long id, @ModelAttribute DeviceDTO deviceDTO) {
        deviceDTO.setId(id);
        deviceService.atualizarDevice(deviceDTO);
        return "redirect:/devices/listar";
    }

    @GetMapping("/deletar/{id}")
    public String confirmarDelecao(@PathVariable Long id, Model model) {
        return deviceService.encontrarDevicePorId(id)
                .map(device -> {
                    model.addAttribute("device", device);
                    return "devices/deletar"; // Página de confirmação de deleção
                })
                .orElse("error/404"); // Página de erro caso não encontre o dispositivo
    }

    @PostMapping("/deletar/{id}")
    public String deletarDevice(@PathVariable Long id, Model model) {
        try {
            deviceService.deletarDevice(id);
            return redirectToList("devices"); // Redireciona para a lista de dispositivos
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "Dispositivo não encontrado");
            return "error/404"; // Redireciona para uma página de erro
        }
    }
}
