package com.api.marvel.controller;

import com.api.marvel.controller.dto.HistoricDTO;
import com.api.marvel.service.IHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historic")
public class HistoryController {

    @Autowired
    private IHistoryService historyService;

    @GetMapping("/find")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<HistoricDTO>> findAll() {
        return new ResponseEntity<>(this.historyService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/find/{username}")
    @PreAuthorize("hasRole('ADMIN') || @InteractionLogUser.validate(#username)")
    public ResponseEntity<List<HistoricDTO>> findByUsername(@PathVariable String username) {
        return new ResponseEntity<>(this.historyService.findByName(username), HttpStatus.OK);
    }
}
