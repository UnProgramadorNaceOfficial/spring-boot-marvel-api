package com.api.marvel.controller;

import com.api.marvel.controller.dto.CharacterDTO;
import com.api.marvel.controller.dto.CharacterInfoDTO;
import com.api.marvel.service.CharacterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Characters", description = "Controller to work with Characters")
@RestController
@RequestMapping("/character")
public class CharacterController {

    private CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/find")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<CharacterDTO>> findCharacters(@RequestParam(required = false) String name,
                                                             @RequestParam(required = false) int[] comics,
                                                             @RequestParam(required = false) int[] series,
                                                             @RequestParam(required = false, defaultValue = "0") int offset,
                                                             @RequestParam(required = false, defaultValue = "10") int limit) {

        return new ResponseEntity<>(this.characterService.findCharacters(name, comics, series, offset, limit), HttpStatus.OK);
    }

    @GetMapping("/find/{characterId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<CharacterInfoDTO> findCharacterById(@PathVariable Long characterId){
        return new ResponseEntity<>(this.characterService.findCharacterById(characterId), HttpStatus.OK);
    }
}
