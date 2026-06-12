package br.com.treinamento.apiveiculos.service;

import br.com.treinamento.apiveiculos.dto.MontadoraRespostaDTO;
import br.com.treinamento.apiveiculos.dto.MontadoraSalvarDTO;
import br.com.treinamento.apiveiculos.model.Montadora;
import br.com.treinamento.apiveiculos.repository.MontadoraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MontadoraService {

    private final MontadoraRepository montadoraRepository;

    // 1. Listar Todas
    public List<MontadoraRespostaDTO> listarTodas() {
        return montadoraRepository.findAll().stream()
                .map(this::converterParaRespostaDTO)
                .toList();
    }

    // 2. Buscar por ID
    public MontadoraRespostaDTO buscarPorId(Long id) {
        Montadora montadora = montadoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Montadora não encontrada com o ID: " + id));
        return converterParaRespostaDTO(montadora);
    }

    // 3. Buscar por País (Usando o Query Method com ILIKE do Repositório)
    public List<MontadoraRespostaDTO> buscarPorPais(String pais) {
        return montadoraRepository.findByPaisOrigemContainingIgnoreCase(pais).stream()
                .map(this::converterParaRespostaDTO)
                .toList();
    }

    // 4. Salvar / Cadastrar
    @Transactional
    public MontadoraRespostaDTO salvar(MontadoraSalvarDTO dto) {
        Montadora montadora = new Montadora();
        montadora.setNome(dto.nome());
        montadora.setPaisOrigem(dto.paisOrigem());

        Montadora montadoraSalva = montadoraRepository.save(montadora);
        return converterParaRespostaDTO(montadoraSalva);
    }

    // 5. Atualizar Existing
    @Transactional
    public MontadoraRespostaDTO atualizar(Long id, MontadoraSalvarDTO dto) {
        Montadora montadora = montadoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Montadora não encontrada para atualização. ID: " + id));

        montadora.setNome(dto.nome());
        montadora.setPaisOrigem(dto.paisOrigem());

        Montadora montadoraAtualizada = montadoraRepository.save(montadora);
        return converterParaRespostaDTO(montadoraAtualizada);
    }

    // 6. Excluir
    @Transactional
    public void excluir(Long id) {
        if (!montadoraRepository.existsById(id)) {
            throw new RuntimeException("Não é possível excluir. Montadora não encontrada. ID: " + id);
        }
        montadoraRepository.deleteById(id);
    }

    // Métodos Auxiliares de Conversão (Mapper manual para fins didáticos)
    private MontadoraRespostaDTO converterParaRespostaDTO(Montadora montadora) {
        return new MontadoraRespostaDTO(
                montadora.getId(),
                montadora.getNome(),
                montadora.getPaisOrigem()
        );
    }
}
