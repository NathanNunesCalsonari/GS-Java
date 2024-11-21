package br.com.fiap.nexus.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    private List<EnergyUsageRecord> energyUsageRecords;

    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)  // Chave estrangeira para Sensor
    private Sensor sensor;  // A relação com o Sensor

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    private List<EnergyUsageRecord> energyUsageRecord;

    public Device(Long id, String name, String type, Sensor sensor) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.sensor = sensor;
    }

    public Object getStatus() {
        return null;
    }
}
