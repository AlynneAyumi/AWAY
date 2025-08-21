package com.example.away.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.away.model.Comparecimento;
import com.example.away.service.ComparecimentoService;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



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
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Comparecimento> findById(@PathVariable Long id) {
        try {
            Comparecimento response = comparecimentoService.findById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/save")
    public ResponseEntity<Comparecimento> save(@RequestBody Comparecimento comparecimento) {
        try {
            Comparecimento response = comparecimentoService.save(comparecimento);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<Comparecimento> update(@PathVariable Long id, @RequestBody Comparecimento comparecimento) {
        try {
            Comparecimento response = comparecimentoService.update(id, comparecimento);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/dataComparecimento")
    public ResponseEntity<List<Comparecimento>> buscarPorDataComparecimento(@PathVariable Date data) { // RequestParam

        List<Comparecimento> comparecimentos = comparecimentoService.buscarPorDataComparecimento(data);

        // Se a lista estiver vazia, retorna 404 Not Found.
        if (comparecimentos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Se houver resultados, retorna 200 OK com a lista de produtos
        return ResponseEntity.ok(comparecimentos);
    }
    
}
