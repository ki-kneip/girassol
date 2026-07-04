package com.girassol.espaco;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "espaco")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Espaco {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private boolean pessoal;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private Instant criadoEm;

    public Espaco(String nome, boolean pessoal) {
        this.nome = nome;
        this.pessoal = pessoal;
        this.criadoEm = Instant.now();
    }
}
