package com.api.marvel.service;

import com.api.marvel.controller.dto.CharacterDTO;
import com.api.marvel.controller.dto.CharacterInfoDTO;

import java.util.List;

public interface CharacterService {
    List<CharacterDTO> findCharacters(String name, int[] comics, int[] series, int offset, int limit);

    CharacterInfoDTO findCharacterById(Long characterName);
}
