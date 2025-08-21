package com.example.away.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.away.model.TipoSituacao;
import com.example.away.service.TipoSituacaoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/situacao")
public class TipoSituacaoController {
    @Autowired
    private TipoSituacaoService situacaoService;

    @GetMapping("/findAll")
    public ResponseEntity<List<TipoSituacao>> findAll() {
        try {
            List<TipoSituacao> response = situacaoService.findAll();
            return new ResponseEntity<>(response, HttpStatus.OK); 

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<TipoSituacao> findById(@PathVariable Long id) {
        try {
            TipoSituacao response = situacaoService.findById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/save")
    public ResponseEntity<TipoSituacao> save(@RequestBody TipoSituacao situacao) {
        try {
            TipoSituacao response = situacaoService.save(situacao);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<TipoSituacao> update(@PathVariable Long id, @RequestBody TipoSituacao situacao) {
        try {
            TipoSituacao response = situacaoService.update(id, situacao);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
