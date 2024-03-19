package com.api.marvel.service.impl;

import com.api.marvel.controller.dto.CharacterDTO;
import com.api.marvel.controller.dto.CharacterInfoDTO;
import com.api.marvel.persistence.CharacterRepository;
import com.api.marvel.service.CharacterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterServiceImpl implements CharacterService {

    private CharacterRepository characterRepository;

    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public List<CharacterDTO> findCharacters(String name, int[] comics, int[] series, int offset, int limit) {
        return this.characterRepository.findCharacters(name, comics, series, offset, limit);
    }

    @Override
    public CharacterInfoDTO findCharacterById(Long characterId) {
        return this.characterRepository.findCharacterById(characterId);
    }
}
