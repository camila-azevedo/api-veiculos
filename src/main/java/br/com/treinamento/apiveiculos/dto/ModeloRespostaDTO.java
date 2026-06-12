package br.com.treinamento.apiveiculos.dto;

import java.math.BigDecimal;

public record ModeloRespostaDTO(
        Long id,
        String nome,
        BigDecimal valor,
        Long idMontadora,
        String nomeMontadora
) {}
