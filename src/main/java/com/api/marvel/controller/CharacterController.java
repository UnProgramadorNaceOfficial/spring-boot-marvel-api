package com.api.marvel.controller;

import com.api.marvel.controller.dto.CharacterDTO;
import com.api.marvel.controller.dto.CharacterInfoDTO;
import com.api.marvel.service.CharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/character")
public class CharacterController {

    private CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/find")
    public ResponseEntity<List<CharacterDTO>> findCharacters(@RequestParam(required = false) String name,
                                                             @RequestParam(required = false) int[] comics,
                                                             @RequestParam(required = false) int[] series,
                                                             @RequestParam(required = false, defaultValue = "0") int offset,
                                                             @RequestParam(required = false, defaultValue = "10") int limit) {

        return new ResponseEntity<>(this.characterService.findCharacters(name, comics, series, offset, limit), HttpStatus.OK);
    }

    @GetMapping("/find/{characterId}")
    public ResponseEntity<CharacterInfoDTO> findCharacterById(@PathVariable Long characterId){
        return new ResponseEntity<>(this.characterService.findCharacterById(characterId), HttpStatus.OK);
    }
}
