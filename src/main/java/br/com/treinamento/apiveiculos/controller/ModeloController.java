package br.com.treinamento.apiveiculos.controller;

import br.com.treinamento.apiveiculos.dto.ModeloRespostaDTO;
import br.com.treinamento.apiveiculos.dto.ModeloSalvarDTO;
import br.com.treinamento.apiveiculos.service.ModeloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modelos")
@RequiredArgsConstructor
public class ModeloController {

    private final ModeloService modeloService;

    // 1. GET - Listar todos os modelos de veículos
    @GetMapping
    public ResponseEntity<List<ModeloRespostaDTO>> listarTodos() {
        List<ModeloRespostaDTO> lista = modeloService.listarTodos();
        return ResponseEntity.ok(lista); // Retorna HTTP 200 OK
    }

    // 2. GET - Buscar um modelo de veículo por ID específico
    @GetMapping("/{id}")
    public ResponseEntity<ModeloRespostaDTO> buscarPorId(@PathVariable Long id) {
        ModeloRespostaDTO dto = modeloService.buscarPorId(id);
        return ResponseEntity.ok(dto); // Retorna HTTP 200 OK
    }

    // 3. POST - Cadastrar um novo modelo associado a uma montadora
    @PostMapping
    public ResponseEntity<ModeloRespostaDTO> salvar(@RequestBody ModeloSalvarDTO dto) {
        ModeloRespostaDTO novoModelo = modeloService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoModelo); // Retorna HTTP 201 Created
    }

    // 4. PUT - Atualizar os dados de um modelo de veículo existente
    @PutMapping("/{id}")
    public ResponseEntity<ModeloRespostaDTO> atualizar(@PathVariable Long id, @RequestBody ModeloSalvarDTO dto) {
        ModeloRespostaDTO modeloAtualizado = modeloService.atualizar(id, dto);
        return ResponseEntity.ok(modeloAtualizado); // Retorna HTTP 200 OK
    }

    // 5. DELETE - Remover um modelo de veículo do sistema
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        modeloService.excluir(id);
        return ResponseEntity.noContent().build(); // Retorna HTTP 204 No Content
    }
}
