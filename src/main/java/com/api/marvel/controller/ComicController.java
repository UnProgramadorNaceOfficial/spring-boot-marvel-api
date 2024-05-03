package com.api.marvel.controller;

import com.api.marvel.controller.dto.ComicDTO;
import com.api.marvel.service.ComicService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gestionar solicitudes relacionadas con comics.
 * Proporciona endpoints para operaciones CRUD sobre comics.
 *
 * @author Santiago Perez
 */
@Tag(name = "Comics", description = "Controller to work with Comics")
@RestController
@RequestMapping("/comic")
public class ComicController {

    private ComicService comicService;

    public ComicController(ComicService comicService) {
        this.comicService = comicService;
    }

    /**
     * Busca y devuelve los cómics asociados a un ID de personaje específico.
     *
     * @param characterId El ID del personaje cuyos cómics se quieren encontrar.
     * @return ResponseEntity que contiene un List<{@link ComicDTO}> con los cómics asociados al personaje especificado
     */
    @GetMapping("/find")
    @PreAuthorize("hasAnyRole('ADMIN') or hasAnyAuthority('READ_COMIC')")
    public ResponseEntity<List<ComicDTO>> findComicsByCharacter(@RequestParam int characterId) {
        return new ResponseEntity<>(this.comicService.findComicByCharacter(characterId), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasAnyRole('ADMIN') or hasAnyAuthority('READ_COMIC')")
    public ResponseEntity<List<ComicDTO>> findAll(@RequestParam(required = false, defaultValue = "0") int offset,
                                                  @RequestParam(required = false, defaultValue = "10") int limit) {
        return new ResponseEntity<>(this.comicService.findAllComics(offset, limit), HttpStatus.OK);
    }


    @GetMapping("/find/{comicId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') or hasAnyAuthority('READ_COMIC')")
    public ResponseEntity<ComicDTO> findById(@PathVariable int comicId) {
        return new ResponseEntity<>(this.comicService.findById(comicId), HttpStatus.OK);
    }
}
