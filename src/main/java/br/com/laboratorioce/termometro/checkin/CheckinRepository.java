package br.com.laboratorioce.termometro.checkin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckinRepository extends JpaRepository<Checkin, Long> {
}
