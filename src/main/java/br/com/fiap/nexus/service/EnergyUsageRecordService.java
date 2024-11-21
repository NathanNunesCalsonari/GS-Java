package br.com.fiap.nexus.service;

import br.com.fiap.nexus.dto.EnergyUsageRecordDTO;
import br.com.fiap.nexus.entity.Device;
import br.com.fiap.nexus.entity.EnergyUsageRecord;
import br.com.fiap.nexus.repository.EnergyUsageRecordRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnergyUsageRecordService {

    @Autowired
    private EnergyUsageRecordRepository energyUsageRecordRepository;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        modelMapper.typeMap(EnergyUsageRecordDTO.class, EnergyUsageRecord.class).addMappings(mapper -> {
            mapper.map(EnergyUsageRecordDTO::getEnergyConsumed, EnergyUsageRecord::setEnergyConsumed);
            mapper.map(EnergyUsageRecordDTO::getMonth, EnergyUsageRecord::setMonth);  // Atualizado para usar 'month'
        });
        modelMapper.typeMap(EnergyUsageRecord.class, EnergyUsageRecordDTO.class).addMappings(mapper -> {
            mapper.map(EnergyUsageRecord::getEnergyConsumed, EnergyUsageRecordDTO::setEnergyConsumed);
            mapper.map(EnergyUsageRecord::getMonth, EnergyUsageRecordDTO::setMonth);  // Atualizado para usar 'month'
        });
    }

    // Listar todos os registros de consumo de energia
    public List<EnergyUsageRecordDTO> listarTodosEnergyUsageRecords() {
        return energyUsageRecordRepository.findAll().stream()
                .map(record -> modelMapper.map(record, EnergyUsageRecordDTO.class))
                .collect(Collectors.toList());
    }

    // Encontrar um registro de consumo de energia por ID
    public Optional<EnergyUsageRecordDTO> encontrarEnergyUsageRecordPorId(Long id) {
        return energyUsageRecordRepository.findById(id).map(record -> modelMapper.map(record, EnergyUsageRecordDTO.class));
    }

    // Salvar um novo registro de consumo de energia
    public EnergyUsageRecordDTO salvarEnergyUsageRecord(EnergyUsageRecordDTO energyUsageRecordDTO) {
        EnergyUsageRecord record = modelMapper.map(energyUsageRecordDTO, EnergyUsageRecord.class);
        return modelMapper.map(energyUsageRecordRepository.save(record), EnergyUsageRecordDTO.class);
    }

    // Deletar um registro de consumo de energia
    public void deletarEnergyUsageRecord(Long id) {
        // Verifica se o registro existe antes de tentar excluir
        if (!energyUsageRecordRepository.existsById(id)) {
            throw new EntityNotFoundException("Registro de consumo de energia não encontrado com id: " + id);
        }
        energyUsageRecordRepository.deleteById(id);
    }

    public void atualizarEnergyUsageRecord(EnergyUsageRecordDTO energyUsageRecordDTO) {
        // Buscar o registro existente no banco de dados
        EnergyUsageRecord recordExistente = energyUsageRecordRepository.findById(energyUsageRecordDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Registro de consumo de energia não encontrado"));

        // Atualizar os campos simples do registro
        recordExistente.setMonth(energyUsageRecordDTO.getMonth());
        recordExistente.setEnergyConsumed(energyUsageRecordDTO.getEnergyConsumed());

        // Verificar se há um DeviceDTO associado e mapear para Device, se necessário
        if (energyUsageRecordDTO.getDeviceId() != null) {
            // Buscar o DeviceDTO e mapear para a entidade Device
            Device device = modelMapper.map(deviceService.encontrarDevicePorId(energyUsageRecordDTO.getDeviceId())
                    .orElseThrow(() -> new EntityNotFoundException("Dispositivo não encontrado")), Device.class);

            // Atribuir o Device ao EnergyUsageRecord
            recordExistente.setDevice(device);
        }

        // Salvar o registro atualizado
        energyUsageRecordRepository.save(recordExistente);
    }
}
