package br.com.fiap.nexus.controller;

import br.com.fiap.nexus.dto.EnergyUsageRecordDTO;
import br.com.fiap.nexus.service.DeviceService;
import br.com.fiap.nexus.service.EnergyUsageRecordService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/energy")
public class EnergyUsageRecordController {

    @Autowired
    private EnergyUsageRecordService energyUsageService;

    @Autowired
    private DeviceService deviceService;

    // Listar todos os registros de consumo de energia
    @GetMapping("/listar")
    public String listarEnergyUsageRecords(Model model) {
        List<EnergyUsageRecordDTO> records = energyUsageService.listarTodosEnergyUsageRecords();
        model.addAttribute("records", records);
        return "energy/listar"; // Página que lista todos os registros de consumo de energia
    }

    // Exibir detalhes de um registro de consumo de energia
    @GetMapping("/{id}")
    public String obterEnergyUsageRecordPorId(@PathVariable Long id, Model model) {
        return energyUsageService.encontrarEnergyUsageRecordPorId(id)
                .map(record -> {
                    model.addAttribute("record", record);
                    return "energy/detalhes"; // Página de detalhes do registro de consumo
                })
                .orElse("error/404"); // Página de erro caso não encontre o registro
    }

    // Exibir formulário de cadastro de consumo de energia
    @GetMapping("/cadastrar")
    public String showCadastroForm(Model model) {
        // A lista de dispositivos está sendo adicionada ao modelo
        model.addAttribute("devices", deviceService.listarTodosDispositivos()); // Lista de dispositivos
        model.addAttribute("energyUsageRecordDTO", new EnergyUsageRecordDTO()); // DTO do formulário
        return "energy/cadastrar"; // Nome da página HTML
    }

    // Cadastro de um novo registro de consumo de energia
    @PostMapping("/cadastrar")
    public String cadastrarEnergyUsageRecord(@ModelAttribute EnergyUsageRecordDTO energyUsageRecordDTO) {
        energyUsageService.salvarEnergyUsageRecord(energyUsageRecordDTO);
        return "redirect:/energy/listar"; // Redireciona para a lista de registros de consumo
    }

    // Exibir formulário de edição de registro de consumo de energia
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        return energyUsageService.encontrarEnergyUsageRecordPorId(id)
                .map(record -> {
                    // Adiciona o registro de consumo ao modelo
                    model.addAttribute("energyUsageRecordDTO", record);
                    // Adiciona a lista de dispositivos ao modelo para o formulário de edição
                    model.addAttribute("devices", deviceService.listarTodosDispositivos());
                    return "energy/editar"; // Página para editar o registro de consumo
                })
                .orElse("error/404"); // Página de erro caso não encontre o registro
    }

    // Atualizar um registro de consumo de energia
    @PostMapping("/editar/{id}")
    public String atualizarEnergyUsageRecord(@PathVariable Long id, @ModelAttribute EnergyUsageRecordDTO energyUsageRecordDTO) {
        energyUsageRecordDTO.setId(id); // Define o ID para a atualização
        energyUsageService.atualizarEnergyUsageRecord(energyUsageRecordDTO);
        return "redirect:/energy/listar"; // Redireciona para a lista de registros
    }

    // Confirmar a exclusão de um registro de consumo de energia
    @GetMapping("/deletar/{id}")
    public String confirmarDelecao(@PathVariable Long id, Model model) {
        return energyUsageService.encontrarEnergyUsageRecordPorId(id)
                .map(record -> {
                    model.addAttribute("record", record);
                    return "energy/deletar"; // Página de confirmação de deleção
                })
                .orElse("error/404"); // Página de erro caso não encontre o registro
    }

    // Deletar um registro de consumo de energia
    @PostMapping("/deletar/{id}")
    public String deletarEnergyUsageRecord(@PathVariable Long id, Model model) {
        try {
            energyUsageService.deletarEnergyUsageRecord(id);
            return "redirect:/energy/listar"; // Redireciona para a lista de registros
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "Registro de consumo não encontrado");
            return "error/404"; // Redireciona para uma página de erro
        }
    }
}
