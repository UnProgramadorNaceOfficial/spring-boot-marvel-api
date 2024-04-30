package com.api.marvel.controller;

import com.api.marvel.controller.dto.HistoricDTO;
import com.api.marvel.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HistoricDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(this.historyService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<HistoricDTO>> findAll() {
        return new ResponseEntity<>(this.historyService.findAll(), HttpStatus.OK);
    }
}
