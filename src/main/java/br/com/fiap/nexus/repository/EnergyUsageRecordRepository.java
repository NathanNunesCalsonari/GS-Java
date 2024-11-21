package br.com.fiap.nexus.repository;

import br.com.fiap.nexus.entity.EnergyUsageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyUsageRecordRepository extends JpaRepository<EnergyUsageRecord, Long> {
}