package com.example.away.controller;

import org.springframework.web.bind.annotation.*;
import com.example.away.model.TipoRegime;
import com.example.away.service.TipoRegimeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/regime")
@CrossOrigin(origins = "*")
public class TipoRegimeController {
    @Autowired
    private TipoRegimeService regimeService;

    @GetMapping("/findAll")
    public ResponseEntity<List<TipoRegime>> findAll() {
        try {
            List<TipoRegime> response = regimeService.findAll();
            return new ResponseEntity<>(response, HttpStatus.OK); 

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<TipoRegime> findById(@PathVariable Long id) {
        try {
            TipoRegime response = regimeService.findById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/save")
    public ResponseEntity<TipoRegime> save(@RequestBody TipoRegime regime) {
        try {
            TipoRegime response = regimeService.save(regime);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<TipoRegime> update(@PathVariable Long id, @RequestBody TipoRegime regime) {
        try {
            TipoRegime response = regimeService.update(id, regime);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            regimeService.delete(id);
            return ResponseEntity.noContent().build(); // Status 204
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Status 400
        }
    }
    
}
