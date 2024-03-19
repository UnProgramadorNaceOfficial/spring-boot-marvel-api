package com.api.marvel.persistence;

import com.api.marvel.config.ApiMarvelConfig;
import com.api.marvel.controller.dto.CharacterDTO;
import com.api.marvel.controller.dto.CharacterInfoDTO;
import com.api.marvel.mapper.CharacterMapper;
import com.api.marvel.service.impl.HttpClientServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public class CharacterRepository {
    private HttpClientServiceImpl httpClientService;
    private ApiMarvelConfig apiMarvelConfig;
    private CharacterMapper characterMapper;

    @Value("${integration.marvel.base.path}")
    private String basePath;

    private String characterPath;

    public CharacterRepository(ApiMarvelConfig apiMarvelConfig, HttpClientServiceImpl httpClientService, CharacterMapper characterMapper) {
        this.apiMarvelConfig = apiMarvelConfig;
        this.httpClientService = httpClientService;
        this.characterMapper = characterMapper;
    }

    @PostConstruct
    public void setCharacterPath() {
        characterPath = this.basePath.concat("/").concat("characters");
    }

    public List<CharacterDTO> findCharacters(String name, int[] comics, int[] series, int offset, int limit) {

        Map<String, String> marvelQueryParams = apiMarvelConfig.getAuthorizationParams();
        marvelQueryParams.put("limit", String.valueOf(limit));
        marvelQueryParams.put("offset", String.valueOf(offset));

        if (StringUtils.hasText(name)) {
            marvelQueryParams.put("name", name);
        }

        if (series != null && series.length > 0) {
            String arrayAsString = convertArrayToString(series);
            marvelQueryParams.put("series", arrayAsString);
        }

        if (comics != null && comics.length > 0) {
            String arrayAsString = convertArrayToString(comics);
            marvelQueryParams.put("comics", arrayAsString);
        }

        JsonNode response = httpClientService.httpGet(characterPath, marvelQueryParams, JsonNode.class);

        List<CharacterDTO> characterList = characterMapper.getCharacterDTOList(response);
        return characterList;
    }

    public CharacterInfoDTO findCharacterById(Long characterId) {
        Map<String, String> marvelQueryParams = apiMarvelConfig.getAuthorizationParams();

        String finalUrl = characterPath.concat("/").concat(String.valueOf(characterId));

        JsonNode response = httpClientService.httpGet(finalUrl, marvelQueryParams, JsonNode.class);

        CharacterInfoDTO characterInfoDTO = characterMapper.getCharacterInfoDTO(response);
        return characterInfoDTO;
    }

    public String convertArrayToString(int[] intArray) { // 12345
        List<String> stringList = Arrays.stream(intArray)
                .mapToObj(String::valueOf)
                .toList();

        return String.join(",", stringList);
    }
}


