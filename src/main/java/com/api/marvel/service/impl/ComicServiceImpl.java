package com.api.marvel.service.impl;

import com.api.marvel.controller.dto.ComicDTO;
import com.api.marvel.persistence.ComicRepository;
import com.api.marvel.service.ComicService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComicServiceImpl implements ComicService {

    private ComicRepository comicRepository;

    public ComicServiceImpl(ComicRepository comicRepository) {
        this.comicRepository = comicRepository;
    }

    @Override
    public List<ComicDTO> findComicByCharacter(int characterId) {
        return this.comicRepository.findComicByCharacter(characterId);
    }

    @Override
    public List<ComicDTO> findAllComics(int offset, int limit) {
        return this.comicRepository.findAllComics(offset, limit);
    }

    @Override
    public ComicDTO findById(int comicId) {
        return this.comicRepository.findById(comicId);
    }
}
