package br.com.fiap.nexus.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorDTO {

    private Long id;

    @NotBlank(message = "Tipo não pode ser vazio")
    private String type;

    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    private List<DeviceDTO> devices;
}

