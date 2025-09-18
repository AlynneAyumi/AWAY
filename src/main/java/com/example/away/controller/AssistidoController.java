package com.example.away.controller;

import com.example.away.dto.AssistidoCreateRequest;
import com.example.away.model.*;
import com.example.away.service.AssistidoService;
import com.example.away.service.PessoaService;
import com.example.away.service.EnderecoService;
import jakarta.validation.*;
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
        try {
            List<Assistido> response = assistidoService.findAll();
            return new ResponseEntity<>(response, HttpStatus.OK); 

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> save(@RequestBody AssistidoCreateRequest request) {
        try {
            System.out.println("Recebendo requisição: " + request);
            
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
            
            assistido = assistidoService.save(assistido);
            pessoa.setAssistido(assistido);
            pessoa = pessoaService.save(pessoa);

            // Retornar JSON estruturado
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Assistido cadastrado com sucesso!");
            response.put("id", assistido.getIdAssistido());
            response.put("assistido", assistido);
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Erro ao cadastrar assistido: " + e.getMessage());
            errorResponse.put("error", e.getClass().getSimpleName());
            
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Assistido> findById(@PathVariable Long id) {
        try {
            var result = assistidoService.findById(id);
            return ResponseEntity.ok(result); // Atalho pro ResponseEntity 200
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Atalho pro ResponseEntity 400
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Assistido> update(@PathVariable Long id,
                                            @Valid @RequestBody Assistido assistidoUpdate) {
        try {
            var result = assistidoService.update(id, assistidoUpdate);
            return ResponseEntity.ok(result); // Atalho pro ResponseEntity 200
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Atalho pro ResponseEntity 400
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            assistidoService.delete(id);
            return ResponseEntity.noContent().build(); // Status 204
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Status 400
        }
    }


    // Consultas com Métodos Automáticos
    @GetMapping("/numAuto")
    public ResponseEntity<List<Assistido>> buscarPorNumAuto(@RequestParam(name = "numAuto") String numAuto) {

        try {
            List<Assistido> assistidos = assistidoService.buscarPorNumAuto(numAuto);
            return ResponseEntity.ok(assistidos); // OK 200

        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // NOT_FOUND 404
        }

    }

    @GetMapping("/numProcesso")
    public ResponseEntity<List<Assistido>> buscarPorNumProcesso(@RequestParam(name = "numProcesso") String numProcesso) {

        try {
            List<Assistido> assistidos = assistidoService.buscarPorNumProcesso(numProcesso);
            return ResponseEntity.ok(assistidos); // OK 200

        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // NOT_FOUND 404
        }

    }
    
}
