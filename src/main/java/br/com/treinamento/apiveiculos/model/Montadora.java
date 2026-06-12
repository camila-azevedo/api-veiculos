package br.com.treinamento.apiveiculos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_montadora")
@Getter
@Setter
public class Montadora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 80, nullable = false)
    private String nome;

    @Column(name = "pais_origem", length = 50, nullable = false)
    private String paisOrigem;
}
