package com.example.away.controller;

import com.example.away.model.Pessoa;
import com.example.away.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Pessoa>> findAll() {
        try {
            List<Pessoa> response = pessoaService.findAll();
            return ResponseEntity.ok(response); // Atalho pro ResponseEntity 200

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Pessoa> save(@Valid @RequestBody Pessoa pessoa) {
        try {
            var result = pessoaService.save(pessoa);

            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Pessoa> findById(@PathVariable Long id) {
        try {
            var result = pessoaService.findById(id);
            return ResponseEntity.ok(result); // Atalho pro ResponseEntity 200
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Atalho pro ResponseEntity 400
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            pessoaService.delete(id);
            return ResponseEntity.noContent().build(); // Status 204
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Status 400
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable Long id,
                                            @Valid @RequestBody Pessoa assistidoUpdate) {
        try {
            var result = pessoaService.update(id, assistidoUpdate);
            return ResponseEntity.ok(result); // Atalho pro ResponseEntity 200
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Atalho pro ResponseEntity 400
        }
    }
    


    // Métodos Personalizados
    @GetMapping("/cpf")
    public ResponseEntity<Pessoa> buscarPorCpf(@RequestParam(name = "cpf") String cpf) {
        try {
            Pessoa pessoa = pessoaService.buscarPessoaPorCpf(cpf);
            return ResponseEntity.ok(pessoa); // Retorna 200 OK com o objeto Pessoa
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Atalho pro ResponseEntity 404 Not Found
        }
    }
}
