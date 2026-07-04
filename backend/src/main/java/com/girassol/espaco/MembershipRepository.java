package com.girassol.espaco;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, UUID> {

    Optional<Membership> findByUsuarioIdAndEspacoId(UUID usuarioId, UUID espacoId);

    List<Membership> findByUsuarioId(UUID usuarioId);
}
