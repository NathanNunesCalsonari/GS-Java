package br.com.fiap.nexus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_energy")
public class EnergyUsageRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String month; // Agora a data será representada como um mês em formato String

    @Column(nullable = false)
    private Double energyConsumed; // em kWh

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;  // Relacionamento com o dispositivo

    public EnergyUsageRecord(Long id, Long deviceId, Double energyConsumed, String month) {
        this.id = id;
        this.device = new Device();  // Aqui você deve associar o dispositivo corretamente
        this.energyConsumed = energyConsumed;
        this.month = month;
    }
}
