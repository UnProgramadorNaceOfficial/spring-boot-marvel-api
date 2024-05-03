package com.api.marvel.controller;

import com.api.marvel.controller.dto.HistoricDTO;
import com.api.marvel.service.IHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "History", description = "Controller to work with History Interactions")
@RestController
@RequestMapping("/historic")
public class HistoryController {

    @Autowired
    private IHistoryService historyService;

    @Operation(
            method = "GET",
            description = "This is an endpoint to list all user interaction historic",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unhauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/find")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<HistoricDTO>> findAll() {
        return new ResponseEntity<>(this.historyService.findAll(), HttpStatus.OK);
    }

    @Operation(
            method = "GET",
            description = "This is an endpoint to list a particular user interaction historic",
            parameters = {
                    @Parameter(
                            name = "username",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unhauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/find/{username}")
    @PreAuthorize("hasRole('ADMIN') || @InteractionLogUser.validate(#username)")
    public ResponseEntity<List<HistoricDTO>> findByUsername(@PathVariable String username) {
        return new ResponseEntity<>(this.historyService.findByName(username), HttpStatus.OK);
    }
}
