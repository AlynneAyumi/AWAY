package com.example.away.controller;

import jakarta.validation.*;
import org.springframework.web.bind.annotation.*;
import com.example.away.model.Assistido;
import com.example.away.service.AssistidoService;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/assistido")
public class AssistidoController {
    @Autowired
    private AssistidoService assistidoService;

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
    public ResponseEntity<Assistido> save(@Valid @RequestBody Assistido assistido) {
        try {
            var result = assistidoService.save(assistido);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Assistido> findById(@PathVariable Long id) {
        try {
            var result = assistidoService.findById(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Assistido> update(@PathVariable Long id,
                                            @Valid @RequestBody Assistido assistidoUpdate) {
        try {
            var result = assistidoService.update(id, assistidoUpdate);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            assistidoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 🔎 Buscar por número do processo (contendo termo)
    @GetMapping("/search/processo")
    public ResponseEntity<List<Assistido>> searchByNumProcesso(@RequestParam String termo) {
        try {
            var result = assistidoService.searchByNumProcesso(termo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 🔎 Buscar por número do auto (contendo termo)
    @GetMapping("/search/auto")
    public ResponseEntity<List<Assistido>> searchByNumAuto(@RequestParam String termo) {
        try {
            var result = assistidoService.searchByNumAuto(termo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}