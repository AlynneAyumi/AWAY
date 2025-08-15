package com.example.away.controller;

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
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Assistido> save(@RequestBody Assistido assistido) {
        try {
            var result = assistidoService.save(assistido);

            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            assistidoService.delete(id);
            return ResponseEntity.noContent().build(); // Status 204
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Status 400
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Assistido> update(@PathVariable Long id,
                                            @RequestBody Assistido assistidoUpdate) {
        try {
            var result = assistidoService.update(id, assistidoUpdate);
            return ResponseEntity.ok(result); // Atalho pro ResponseEntity 200
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Atalho pro ResponseEntity 400
        }
    }

    
}
