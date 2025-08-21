package com.example.away.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.away.model.TipoMonitoramento;
import com.example.away.service.TipoMonitoramentoService;
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
@RequestMapping("/monitoramento")
public class TipoMonitoramentoController {
    @Autowired
    private TipoMonitoramentoService monitoramentoService;

    @GetMapping("/findAll")
    public ResponseEntity<List<TipoMonitoramento>> findAll() {
        try {
            List<TipoMonitoramento> response = monitoramentoService.findAll();
            return new ResponseEntity<>(response, HttpStatus.OK); 

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<TipoMonitoramento> findById(@PathVariable Long id) {
        try {
            TipoMonitoramento response = monitoramentoService.findById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/save")
    public ResponseEntity<TipoMonitoramento> save(@RequestBody TipoMonitoramento monitoramento) {
        try {
            TipoMonitoramento response = monitoramentoService.save(monitoramento);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<TipoMonitoramento> update(@PathVariable Long id, @RequestBody TipoMonitoramento monitoramento) {
        try {
            TipoMonitoramento response = monitoramentoService.update(id, monitoramento);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            monitoramentoService.delete(id);
            return ResponseEntity.noContent().build(); // Status 204
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Status 400
        }
    }
    
}
