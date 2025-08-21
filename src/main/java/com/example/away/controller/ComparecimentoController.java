package com.example.away.controller;

import com.example.away.model.Comparecimento;
import com.example.away.service.ComparecimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comparecimento")
public class ComparecimentoController {

    @Autowired
    private ComparecimentoService comparecimentoService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Comparecimento>> findAll() {
        try {
            List<Comparecimento> response = comparecimentoService.findAll();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Comparecimento> findById(@PathVariable Long id) {
        try {
            Comparecimento response = comparecimentoService.findById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Comparecimento> save(@RequestBody Comparecimento comparecimento) {
        try {
            Comparecimento response = comparecimentoService.save(comparecimento);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Comparecimento> update(@PathVariable Long id,
                                                 @RequestBody Comparecimento comparecimento) {
        try {
            Comparecimento response = comparecimentoService.update(id, comparecimento);
            return ResponseEntity.ok(response); // 200 com body
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            comparecimentoService.delete(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ===== Endpoints que usam os "métodos automáticos" do repository =====

    // 1) Buscar por data exata: GET /comparecimento/search/data?data=2025-08-21
    @GetMapping("/search/data")
    public ResponseEntity<List<Comparecimento>> buscarPorData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data) {
        try {
            List<Comparecimento> comparecimentos = comparecimentoService.buscarPorDataComparecimento(data);
            // Se preferir retornar 200 mesmo vazio, troque pelo ResponseEntity.ok(comparecimentos)
            if (comparecimentos.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(comparecimentos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 2) Buscar por período: GET /comparecimento/search/periodo?inicio=2025-08-01&fim=2025-08-31
    @GetMapping("/search/periodo")
    public ResponseEntity<List<Comparecimento>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fim) {
        try {
            List<Comparecimento> lista = comparecimentoService.buscarPorPeriodo(inicio, fim);
            if (lista.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 3) Buscar por assistido (FK): GET /comparecimento/search/assistido/{idAssistido}
    @GetMapping("/search/assistido/{idAssistido}")
    public ResponseEntity<List<Comparecimento>> buscarPorAssistido(@PathVariable Long idAssistido) {
        try {
            List<Comparecimento> lista = comparecimentoService.buscarPorAssistido(idAssistido);
            if (lista.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}