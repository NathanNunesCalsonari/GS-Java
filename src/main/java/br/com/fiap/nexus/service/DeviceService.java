package br.com.fiap.nexus.service;

import br.com.fiap.nexus.dto.DeviceDTO;
import br.com.fiap.nexus.dto.SensorDTO;
import br.com.fiap.nexus.entity.Device;
import br.com.fiap.nexus.entity.Sensor;
import br.com.fiap.nexus.repository.DeviceRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        modelMapper.typeMap(DeviceDTO.class, Device.class)
                .addMappings(mapper -> {
                    mapper.map(DeviceDTO::getName, Device::setName);
                    mapper.map(DeviceDTO::getType, Device::setType);
                });
        modelMapper.typeMap(Device.class, DeviceDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Device::getName, DeviceDTO::setName);
                    mapper.map(Device::getType, DeviceDTO::setType);
                });
    }

    public List<DeviceDTO> listarTodosDispositivos() {
        return deviceRepository.findAll().stream()
                .map(device -> modelMapper.map(device, DeviceDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<DeviceDTO> encontrarDevicePorId(Long id) {
        return deviceRepository.findById(id).map(device -> modelMapper.map(device, DeviceDTO.class));
    }

    public DeviceDTO salvarDevice(DeviceDTO deviceDTO) {
        Device device = modelMapper.map(deviceDTO, Device.class);
        return modelMapper.map(deviceRepository.save(device), DeviceDTO.class);
    }

    public void deletarDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new EntityNotFoundException("Dispositivo não encontrado com id: " + id);
        }
        deviceRepository.deleteById(id);
    }

    public void atualizarDevice(DeviceDTO deviceDTO) {
        Device deviceExistente = deviceRepository.findById(deviceDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Dispositivo não encontrado"));

        deviceExistente.setName(deviceDTO.getName());
        deviceExistente.setType(deviceDTO.getType());

        if (deviceDTO.getSensor() != null && deviceDTO.getSensor().getId() != null) {
            Sensor sensor = modelMapper.map(sensorService.encontrarSensorPorId(deviceDTO.getSensor().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Sensor não encontrado")), Sensor.class);

            deviceExistente.setSensor(sensor);
        }

        deviceRepository.save(deviceExistente);
    }

}
