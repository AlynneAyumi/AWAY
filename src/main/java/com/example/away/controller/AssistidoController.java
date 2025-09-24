package com.example.away.controller;

import com.example.away.dto.AssistidoCreateRequest;
import com.example.away.dto.AssistidoUpdateRequest;
import com.example.away.model.*;
import com.example.away.service.AssistidoService;
import com.example.away.service.PessoaService;
import com.example.away.service.EnderecoService;
import com.example.away.exception.ResourceNotFoundException;
import com.example.away.exception.BusinessException;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/assistido")
public class AssistidoController {
    @Autowired
    private AssistidoService assistidoService;
    
    @Autowired
    private PessoaService pessoaService;
    
    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Assistido>> findAll() {
        List<Assistido> response = assistidoService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> save(@RequestBody AssistidoCreateRequest request) {
        if (request == null || request.getPessoa() == null) {
            throw new BusinessException("Dados do assistido são obrigatórios", "MISSING_DATA");
        }
        
        try {
            // Criar endereco sem validações obrigatórias por enquanto
            Endereco endereco = new Endereco();
            endereco.setRua(request.getPessoa().getEndereco().getLogradouro());
            endereco.setCep("00000-000");
            endereco.setBairro("A definir");
            endereco.setCidade("A definir");
            endereco.setEstado("A definir");
            endereco.setNumero(0);
            endereco.setCreationDate(new Date());
            
            // Criar pessoa
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(request.getPessoa().getNome());
            pessoa.setSegundoNome(request.getPessoa().getSegundoNome());
            pessoa.setCpf(request.getPessoa().getCpf());
            pessoa.setTelefone(request.getPessoa().getTelefone());
            pessoa.setCreationDate(new Date());
            
            // Converter data de nascimento
            if (request.getPessoa().getDataNascimento() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                pessoa.setDataNascimento(sdf.parse(request.getPessoa().getDataNascimento()));
            }
            
            // Criar assistido
            Assistido assistido = new Assistido();
            assistido.setNumAuto(request.getNumAuto());
            assistido.setNumProcesso(request.getNumProcesso());
            assistido.setObservacao(request.getObservacao());
            assistido.setCreationDate(new Date());
            
            // Salvar na ordem correta
            endereco = enderecoService.save(endereco);
            pessoa.setEndereco(endereco);
            
            // Salvar assistido primeiro
            assistido = assistidoService.save(assistido);
            
            // Associar pessoa ao assistido e salvar
            pessoa.setAssistido(assistido);
            pessoa = pessoaService.save(pessoa);
            
            // Associar pessoa ao assistido
            assistido.setPessoa(pessoa);
            assistido = assistidoService.save(assistido);

            // Retornar JSON estruturado
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Assistido cadastrado com sucesso!");
            response.put("id", assistido.getIdAssistido());
            response.put("assistido", assistido);
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new BusinessException("Erro ao cadastrar assistido: " + e.getMessage(), "SAVE_ERROR", e);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Assistido> findById(@PathVariable Long id) {
        Assistido result = assistidoService.findById(id);
        if (result == null) {
            throw new ResourceNotFoundException("Assistido", "id", id);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id,
                                                      @RequestBody AssistidoUpdateRequest request) {
        try {
            System.out.println("Atualizando assistido ID: " + id + " com dados: " + request);
            
            // Buscar assistido existente
            Assistido assistidoExistente = assistidoService.findById(id);
            if (assistidoExistente == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "Assistido não encontrado");
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }

            // Atualizar dados do assistido
            assistidoExistente.setNumAuto(request.getNumAuto());
            assistidoExistente.setNumProcesso(request.getNumProcesso());
            assistidoExistente.setObservacao(request.getObservacao());

            // Atualizar pessoa se existir
            if (assistidoExistente.getPessoa() != null && request.getPessoa() != null) {
                Pessoa pessoa = assistidoExistente.getPessoa();
                pessoa.setNome(request.getPessoa().getNome());
                pessoa.setSegundoNome(request.getPessoa().getSegundoNome());
                pessoa.setCpf(request.getPessoa().getCpf());
                pessoa.setTelefone(request.getPessoa().getTelefone());

                // Converter data de nascimento
                if (request.getPessoa().getDataNascimento() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    pessoa.setDataNascimento(sdf.parse(request.getPessoa().getDataNascimento()));
                }

                // Atualizar endereço se existir
                if (pessoa.getEndereco() != null && request.getPessoa().getEndereco() != null) {
                    Endereco endereco = pessoa.getEndereco();
                    endereco.setRua(request.getPessoa().getEndereco().getLogradouro());
                    if (request.getPessoa().getEndereco().getCep() != null) {
                        endereco.setCep(request.getPessoa().getEndereco().getCep());
                    }
                    if (request.getPessoa().getEndereco().getBairro() != null) {
                        endereco.setBairro(request.getPessoa().getEndereco().getBairro());
                    }
                    if (request.getPessoa().getEndereco().getCidade() != null) {
                        endereco.setCidade(request.getPessoa().getEndereco().getCidade());
                    }
                    if (request.getPessoa().getEndereco().getEstado() != null) {
                        endereco.setEstado(request.getPessoa().getEndereco().getEstado());
                    }
                    if (request.getPessoa().getEndereco().getNumero() != null) {
                        endereco.setNumero(request.getPessoa().getEndereco().getNumero());
                    }
                    
                    enderecoService.save(endereco);
                }

                pessoaService.save(pessoa);
            }

            // Salvar assistido atualizado
            Assistido assistidoAtualizado = assistidoService.update(id, assistidoExistente);

            // Retornar JSON estruturado
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Assistido atualizado com sucesso!");
            response.put("id", assistidoAtualizado.getIdAssistido());
            response.put("assistido", assistidoAtualizado);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Erro ao atualizar assistido: " + e.getMessage());
            errorResponse.put("error", e.getClass().getSimpleName());

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        try {
            System.out.println("Recebendo requisição de exclusão para assistido ID: " + id);
            
            // Verificar se o assistido existe antes de tentar deletar
            Assistido assistido = assistidoService.findById(id);
            if (assistido == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "Assistido não encontrado");
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
            
            // Executar a exclusão
            assistidoService.delete(id);
            
            // Retornar resposta de sucesso
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Assistido excluído com sucesso!");
            response.put("id", id);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Erro ao excluir assistido: " + e.getMessage());
            errorResponse.put("error", e.getClass().getSimpleName());
            
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }


    // Consultas com Métodos Automáticos
    @GetMapping("/numAuto")
    public ResponseEntity<List<Assistido>> buscarPorNumAuto(@RequestParam(name = "numAuto") String numAuto) {
        List<Assistido> assistidos = assistidoService.buscarPorNumAuto(numAuto);
        return ResponseEntity.ok(assistidos);
    }

    @GetMapping("/numProcesso")
    public ResponseEntity<List<Assistido>> buscarPorNumProcesso(@RequestParam(name = "numProcesso") String numProcesso) {
        List<Assistido> assistidos = assistidoService.buscarPorNumProcesso(numProcesso);
        return ResponseEntity.ok(assistidos);
    }
    
}
