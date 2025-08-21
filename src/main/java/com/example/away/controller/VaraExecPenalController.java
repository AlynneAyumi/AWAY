package com.example.away.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.away.model.VaraExecPenal;
import com.example.away.service.VaraExecPenalService;
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
@RequestMapping("/varaExecPenal")
public class VaraExecPenalController {
    @Autowired
    private VaraExecPenalService varaExecPenalService;

    @GetMapping("/findAll")
    public ResponseEntity<List<VaraExecPenal>> findAll() {
        try {
            List<VaraExecPenal> response = varaExecPenalService.findAll();
            return new ResponseEntity<>(response, HttpStatus.OK); 
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<VaraExecPenal> findById(@PathVariable Long id) {
        try {
            VaraExecPenal response = varaExecPenalService.findById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/save")
    public ResponseEntity<VaraExecPenal> save(@RequestBody VaraExecPenal varaExecPenal) {
        try {
            VaraExecPenal response = varaExecPenalService.save(varaExecPenal);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<VaraExecPenal> update(@PathVariable Long id, @RequestBody VaraExecPenal varaExecPenal) {
        try {
            VaraExecPenal response = varaExecPenalService.update(id, varaExecPenal);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            varaExecPenalService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
}