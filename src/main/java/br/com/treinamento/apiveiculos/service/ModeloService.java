package br.com.treinamento.apiveiculos.service;

import br.com.treinamento.apiveiculos.dto.ModeloRespostaDTO;
import br.com.treinamento.apiveiculos.dto.ModeloSalvarDTO;
import br.com.treinamento.apiveiculos.model.Modelo;
import br.com.treinamento.apiveiculos.model.Montadora;
import br.com.treinamento.apiveiculos.repository.ModeloRepository;
import br.com.treinamento.apiveiculos.repository.MontadoraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ModeloService {

    private final ModeloRepository modeloRepository;
    private final MontadoraRepository montadoraRepository;

    // 1. Listar Todos
    public List<ModeloRespostaDTO> listarTodos() {
        return modeloRepository.findAll().stream()
                .map(this::converterParaRespostaDTO)
                .toList();
    }

    // 2. Buscar por ID
    public ModeloRespostaDTO buscarPorId(Long id) {
        Modelo modelo = modeloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Modelo de veículo não encontrado. ID: " + id));
        return converterParaRespostaDTO(modelo);
    }

    // 3. Salvar (Onde ocorre a junção das duas tabelas através das chaves)
    @Transactional
    public ModeloRespostaDTO salvar(ModeloSalvarDTO dto) {
        // Regra de Negócio: Validação financeira básica
        if (dto.valor() == null || dto.valor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("O valor do veículo deve ser estritamente maior que zero.");
        }

        // Junção: Buscamos a referência da montadora pai antes de salvar o filho
        Montadora montadora = montadoraRepository.findById(dto.idMontadora())
                .orElseThrow(() -> new RuntimeException("Não foi possível cadastrar o modelo. Montadora não encontrada com o ID: " + dto.idMontadora()));

        Modelo modelo = new Modelo();
        modelo.setNome(dto.nome());
        modelo.setValor(dto.valor());
        modelo.setMontadora(montadora); // Amarra a chave estrangeira (FK)

        Modelo modeloSalvo = modeloRepository.save(modelo);
        return converterParaRespostaDTO(modeloSalvo);
    }

    // 4. Atualizar
    @Transactional
    public ModeloRespostaDTO atualizar(Long id, ModeloSalvarDTO dto) {
        Modelo modelo = modeloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Modelo não encontrado para atualização. ID: " + id));

        Montadora montadora = montadoraRepository.findById(dto.idMontadora())
                .orElseThrow(() -> new RuntimeException("Montadora informada não existe. ID: " + dto.idMontadora()));

        modelo.setNome(dto.nome());
        modelo.setValor(dto.valor());
        modelo.setMontadora(montadora);

        Modelo modeloAtualizado = modeloRepository.save(modelo);
        return converterParaRespostaDTO(modeloAtualizado);
    }

    // 5. Excluir
    @Transactional
    public void excluir(Long id) {
        if (!modeloRepository.existsById(id)) {
            throw new RuntimeException("Não é possível excluir. Modelo não encontrado. ID: " + id);
        }
        modeloRepository.deleteById(id);
    }

    // Métodos Auxiliares de Conversão e Achatamento (Flattening)
    private ModeloRespostaDTO converterParaRespostaDTO(Modelo modelo) {
        return new ModeloRespostaDTO(
                modelo.getId(),
                modelo.getNome(),
                modelo.getValor(),
                modelo.getMontadora().getId(),
                modelo.getMontadora().getNome() // Evita o objeto aninhado enviando apenas a String para o JSON final
        );
    }
}
