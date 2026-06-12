package br.com.treinamento.apiveiculos.dto;

import java.math.BigDecimal;

public record ModeloSalvarDTO(
        String nome,
        BigDecimal valor,
        Long idMontadora
) {}
