package br.com.treinamento.apiveiculos.controller;

import br.com.treinamento.apiveiculos.dto.MontadoraRespostaDTO;
import br.com.treinamento.apiveiculos.dto.MontadoraSalvarDTO;
import br.com.treinamento.apiveiculos.service.MontadoraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/montadoras")
@RequiredArgsConstructor
public class MontadoraController {

    private final MontadoraService montadoraService;

    // 1. GET - Listar todas as montadoras
    @GetMapping
    public ResponseEntity<List<MontadoraRespostaDTO>> listarTodas() {
        List<MontadoraRespostaDTO> lista = montadoraService.listarTodas();
        return ResponseEntity.ok(lista); // Retorna HTTP 200 OK
    }

    // 2. GET - Buscar uma montadora por ID específico
    @GetMapping("/{id}")
    public ResponseEntity<MontadoraRespostaDTO> buscarPorId(@PathVariable Long id) {
        MontadoraRespostaDTO dto = montadoraService.buscarPorId(id);
        return ResponseEntity.ok(dto); // Retorna HTTP 200 OK
    }

    // 3. GET - Filtrar montadoras por país de origem (Usando Query Param)
    // Exemplo de chamada: GET /api/montadoras/filtro?pais=Alemanha
    @GetMapping("/filtro")
    public ResponseEntity<List<MontadoraRespostaDTO>> buscarPorPais(@RequestParam String pais) {
        List<MontadoraRespostaDTO> lista = montadoraService.buscarPorPais(pais);
        return ResponseEntity.ok(lista); // Retorna HTTP 200 OK
    }

    // 4. POST - Cadastrar uma nova montadora
    @PostMapping
    public ResponseEntity<MontadoraRespostaDTO> salvar(@RequestBody MontadoraSalvarDTO dto) {
        MontadoraRespostaDTO novaMontadora = montadoraService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMontadora); // Retorna HTTP 201 Created
    }

    // 5. PUT - Atualizar os dados de uma montadora existente
    @PutMapping("/{id}")
    public ResponseEntity<MontadoraRespostaDTO> atualizar(@PathVariable Long id, @RequestBody MontadoraSalvarDTO dto) {
        MontadoraRespostaDTO montadoraAtualizada = montadoraService.atualizar(id, dto);
        return ResponseEntity.ok(montadoraAtualizada); // Retorna HTTP 200 OK
    }

    // 6. DELETE - Remover uma montadora do sistema
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        montadoraService.excluir(id);
        return ResponseEntity.noContent().build(); // Retorna HTTP 204 No Content
    }
}
