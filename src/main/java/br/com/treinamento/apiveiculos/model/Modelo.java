package br.com.treinamento.apiveiculos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_modelo")
@Getter
@Setter
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 80, nullable = false)
    private String nome;

    @Column(name = "valor", precision = 12, scale = 2, nullable = false)
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_montadora", nullable = false)
    private Montadora montadora;
}
