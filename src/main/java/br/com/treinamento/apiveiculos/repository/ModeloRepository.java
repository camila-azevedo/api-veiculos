package br.com.treinamento.apiveiculos.repository;

import br.com.treinamento.apiveiculos.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    // Query Method: Busca todos os modelos associados a um ID de montadora específico.
    // SQL gerado: SELECT * FROM tb_modelo WHERE id_montadora = ?
    List<Modelo> findByMontadoraId(Long idMontadora);

    // Query Method Avançado: Busca veículos com valores menores ou iguais ao parâmetro informado.
    // SQL gerado: SELECT * FROM tb_modelo WHERE valor <= ?
    List<Modelo> findByValorLessThanEqual(BigDecimal valorMaximo);
}
