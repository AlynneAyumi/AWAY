package com.example.away.controller;

import org.springframework.web.bind.annotation.*;
import com.example.away.model.TipoSituacao;
import com.example.away.service.TipoSituacaoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/situacao")
@CrossOrigin(origins = "*")
public class TipoSituacaoController {
    @Autowired
    private TipoSituacaoService situacaoService;

    @GetMapping("/findAll")
    public ResponseEntity<List<TipoSituacao>> findAll() {
        try {
            List<TipoSituacao> response = situacaoService.findAll();
            return new ResponseEntity<>(response, HttpStatus.OK); 

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<TipoSituacao> findById(@PathVariable Long id) {
        try {
            TipoSituacao response = situacaoService.findById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/save")
    public ResponseEntity<TipoSituacao> save(@RequestBody TipoSituacao situacao) {
        try {
            TipoSituacao response = situacaoService.save(situacao);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<TipoSituacao> update(@PathVariable Long id, @RequestBody TipoSituacao situacao) {
        try {
            TipoSituacao response = situacaoService.update(id, situacao);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            situacaoService.delete(id);
            return ResponseEntity.noContent().build(); // Status 204
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Status 400
        }
    }
}
