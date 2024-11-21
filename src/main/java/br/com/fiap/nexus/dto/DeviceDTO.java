package br.com.fiap.nexus.dto;

import br.com.fiap.nexus.entity.Sensor;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {

    private Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    private String name;

    @NotBlank(message = "Tipo não pode ser vazio")
    private String type;

    private Sensor sensor;
}

