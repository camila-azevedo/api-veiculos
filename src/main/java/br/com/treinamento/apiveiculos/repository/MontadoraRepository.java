package br.com.treinamento.apiveiculos.repository;

import br.com.treinamento.apiveiculos.model.Montadora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MontadoraRepository extends JpaRepository<Montadora, Long> {

    // Query Method: O Spring gerará automaticamente o SQL filtrando pelo país de origem.
    // SQL gerado: SELECT * FROM tb_montadora WHERE pais_origem = ?
    List<Montadora> findByPaisOrigemIgnoreCase(String paisOrigem);

    // Query Method Refatorado com ILIKE (Busca parcial e case-insensitive) // SQL gerado no Postgres: SELECT * FROM tb_montadora WHERE pais_origem ILIKE ? // O Spring Boot injetará automaticamente os caracteres '%' antes e depois da String (ex: '%jap%')
    List<Montadora> findByPaisOrigemContainingIgnoreCase(String paisOrigem);

}
