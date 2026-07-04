package com.girassol.espaco;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EspacoRepository extends JpaRepository<Espaco, UUID> {
}
