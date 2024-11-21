package br.com.fiap.nexus.service;

import br.com.fiap.nexus.dto.SensorDTO;
import br.com.fiap.nexus.entity.Sensor;
import br.com.fiap.nexus.repository.SensorRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        modelMapper.typeMap(SensorDTO.class, Sensor.class).addMappings(mapper -> {
            mapper.map(SensorDTO::getType, Sensor::setType);
            mapper.map(SensorDTO::getNome, Sensor::setNome);
        });
        modelMapper.typeMap(Sensor.class, SensorDTO.class).addMappings(mapper -> {
            mapper.map(Sensor::getType, SensorDTO::setType);
            mapper.map(Sensor::getNome, SensorDTO::setNome);
        });
    }

    public List<SensorDTO> listarTodosSensores() {
        return sensorRepository.findAll().stream()
                .map(sensor -> modelMapper.map(sensor, SensorDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<SensorDTO> encontrarSensorPorId(Long id) {
        return sensorRepository.findById(id).map(sensor -> modelMapper.map(sensor, SensorDTO.class));
    }

    public SensorDTO salvarSensor(SensorDTO sensorDTO) {
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
        return modelMapper.map(sensorRepository.save(sensor), SensorDTO.class);
    }

    public void deletarSensor(Long id) {
        if (!sensorRepository.existsById(id)) {
            throw new EntityNotFoundException("Sensor não encontrado com id: " + id);
        }
        sensorRepository.deleteById(id);
    }

    public void atualizarSensor(SensorDTO sensorDTO) {
        Sensor sensorExistente = sensorRepository.findById(sensorDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Sensor não encontrado"));

        sensorExistente.setType(sensorDTO.getType());
        sensorExistente.setNome(sensorDTO.getNome());

        sensorRepository.save(sensorExistente);
    }
}
