package com.example.away.controller;

import org.springframework.web.bind.annotation.*;
import com.example.away.model.Endereco;
import com.example.away.service.EnderecoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Endereco>> findAll() {
        try {
            List<Endereco> response = enderecoService.findAll();
            return new ResponseEntity<>(response, HttpStatus.OK); 

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Endereco> findById(@PathVariable Long id) {
        try {
            Endereco response = enderecoService.findById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/save")
    public ResponseEntity<Endereco> save(@RequestBody Endereco endereco) {
        try {
            Endereco response = enderecoService.save(endereco);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Endereco> update(@PathVariable Long id, @RequestBody Endereco endereco) {
        try {
            Endereco response = enderecoService.update(id, endereco);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            enderecoService.delete(id);
            return ResponseEntity.noContent().build(); // Status 204
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Status 400
        }
    }



    // Consultas com Métodos Automáticos
    @GetMapping("/cep")
    public ResponseEntity<Endereco> buscarEnderecoPorCep(@RequestParam(name = "cep") String cep) {
        try {
            Endereco endereco = enderecoService.buscarPorCep(cep);
            return ResponseEntity.ok(endereco); // Retorna 200 OK com o objeto Endereco no corpo

        } catch (IllegalArgumentException ex) {
            // Captura a exceção e retorna 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/cidade")
    public ResponseEntity<Endereco> buscarEnderecoPorCidade(@RequestParam(name = "cidade") String cidade) {
        try {
            Endereco endereco = enderecoService.buscarPorCidade(cidade);
            return ResponseEntity.ok(endereco); // Retorna 200 OK com o objeto Endereco no corpo

        } catch (IllegalArgumentException ex) {
            // Captura a exceção e retorna 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
}
