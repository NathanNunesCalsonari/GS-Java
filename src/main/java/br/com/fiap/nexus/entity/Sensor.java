package br.com.fiap.nexus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String nome;

    // Relacionamento bidirecional, um sensor pode ter muitos dispositivos
    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    private List<Device> devices;
}
