package com.example.away.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.away.model.TipoRegime;
import com.example.away.service.TipoRegimeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/regime")
public class TipoRegimeController {
    @Autowired
    private TipoRegimeService regimeService;

    @GetMapping("/findAll")
    public ResponseEntity<List<TipoRegime>> findAll() {
        try {
            List<TipoRegime> response = regimeService.findAll();
            return new ResponseEntity<>(response, HttpStatus.OK); 

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<TipoRegime> findById(@PathVariable Long id) {
        try {
            TipoRegime response = regimeService.findById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/save")
    public ResponseEntity<TipoRegime> save(@RequestBody TipoRegime regime) {
        try {
            TipoRegime response = regimeService.save(regime);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<TipoRegime> update(@PathVariable Long id, @RequestBody TipoRegime regime) {
        try {
            TipoRegime response = regimeService.update(id, regime);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
}
