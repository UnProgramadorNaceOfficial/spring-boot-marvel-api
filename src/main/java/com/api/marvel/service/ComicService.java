package com.api.marvel.service;

import com.api.marvel.controller.dto.ComicDTO;

import java.util.List;

public interface ComicService {
    List<ComicDTO> findComicByCharacter(int characterId);

    List<ComicDTO> findAllComics(int offset, int limit);

    ComicDTO findById(int comicId);
}
