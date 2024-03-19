package com.api.marvel.persistence;

import com.api.marvel.config.ApiMarvelConfig;
import com.api.marvel.controller.dto.ComicDTO;
import com.api.marvel.mapper.ComicMapper;
import com.api.marvel.service.impl.HttpClientServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ComicRepository {

    private HttpClientServiceImpl httpClientService;
    private ApiMarvelConfig apiMarvelConfig;
    private ComicMapper comicMapper;

    @Value("${integration.marvel.base.path}")
    private String basePath;

    private String comicPath;

    @PostConstruct
    public void setCharacterPath() {
        comicPath = this.basePath.concat("/").concat("comics");
    }

    public ComicRepository(ApiMarvelConfig apiMarvelConfig, HttpClientServiceImpl httpClientService, ComicMapper comicMapper) {
        this.apiMarvelConfig = apiMarvelConfig;
        this.httpClientService = httpClientService;
        this.comicMapper = comicMapper;
    }
    public List<ComicDTO> findComicByCharacter(int characterId) {
        Map<String, String> marvelQueryParams = apiMarvelConfig.getAuthorizationParams();

        if (characterId != 0) {
            marvelQueryParams.put("characters", String.valueOf(characterId));
        }

        JsonNode response = httpClientService.httpGet(this.comicPath, marvelQueryParams, JsonNode.class);

        List<ComicDTO> comicDTOList = this.comicMapper.getComicDTOList(response);
        return comicDTOList;
    }

    public List<ComicDTO> findAllComics(int offset, int limit) {
        Map<String, String> marvelQueryParams = apiMarvelConfig.getAuthorizationParams();
        marvelQueryParams.put("limit", String.valueOf(limit));
        marvelQueryParams.put("offset", String.valueOf(offset));

        JsonNode response = httpClientService.httpGet(this.comicPath, marvelQueryParams, JsonNode.class);

        List<ComicDTO> comicDTOList = this.comicMapper.getComicDTOList(response);
        return comicDTOList;
    }

    public ComicDTO findById(int comicId) {
        Map<String, String> marvelQueryParams = apiMarvelConfig.getAuthorizationParams();

        String finalUrl = this.comicPath.concat("/").concat(String.valueOf(comicId));
        JsonNode response = httpClientService.httpGet(finalUrl, marvelQueryParams, JsonNode.class);

        ComicDTO comicDTO = this.comicMapper.getComicInfoDTO(response);
        return comicDTO;
    }
}
