package com.example.away.controller;

import org.springframework.web.bind.annotation.*;
import com.example.away.model.Comparecimento;
import com.example.away.service.ComparecimentoService;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/comparecimento")
public class ComparecimentoController {
    @Autowired
    private ComparecimentoService comparecimentoService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Comparecimento>> findAll() {
        try {
            List<Comparecimento> response = comparecimentoService.findAll();
            return new ResponseEntity<>(response, HttpStatus.OK); 

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Comparecimento> findById(@PathVariable Long id) {
        try {
            Comparecimento response = comparecimentoService.findById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }
    
    @PostMapping("/save")
    public ResponseEntity<Comparecimento> save(@RequestBody Comparecimento comparecimento) {
        try {
            System.out.println("Recebendo comparecimento para assistido ID: " + 
                (comparecimento.getAssistido() != null ? comparecimento.getAssistido().getIdAssistido() : "null"));
            Comparecimento response = comparecimentoService.save(comparecimento);
            System.out.println("Comparecimento salvo com sucesso - ID: " + response.getIdComparecimento());
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            System.err.println("Erro ao salvar comparecimento: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Comparecimento> update(@PathVariable Long id, @RequestBody Comparecimento comparecimento) {
        try {
            Comparecimento response = comparecimentoService.update(id, comparecimento);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            comparecimentoService.delete(id);
            return ResponseEntity.noContent().build(); // Status 204
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Status 400
        }
    }

    // Consultas com Métodos Automáticos
    @GetMapping("/dataComparecimento")
    public ResponseEntity<List<Comparecimento>> buscarPorDataComparecimento(@RequestParam(name = "data") Date data) { // RequestParam

        try {
            List<Comparecimento> comparecimentos = comparecimentoService.buscarPorDataComparecimento(data);
            return ResponseEntity.ok(comparecimentos); // OK 200

        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // NOT_FOUND 404
        }

    }

    @GetMapping("/flagComparecimento")
    public ResponseEntity<List<Comparecimento>> buscarPorFlagComparecimento(@RequestParam(name = "flag") Boolean flag) { // RequestParam

        try {
            List<Comparecimento> comparecimentos = comparecimentoService.buscarPorFlagComparecimento(flag);
            return ResponseEntity.ok(comparecimentos); // OK 200

        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // NOT_FOUND 404
        }

    }

}
