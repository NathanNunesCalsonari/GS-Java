package br.com.fiap.nexus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnergyUsageRecordDTO {

    private Long id;

    @NotBlank(message = "Mês não pode ser vazio")
    private String month;

    @NotNull(message = "Consumo de energia não pode ser nulo")
    private Double energyConsumed;

    @NotNull(message = "ID do dispositivo não pode ser nulo")
    private Long deviceId;
}

