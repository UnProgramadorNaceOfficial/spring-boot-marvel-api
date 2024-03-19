package com.api.marvel.mapper;

import com.api.marvel.controller.dto.CharacterDTO;
import com.api.marvel.controller.dto.CharacterInfoDTO;
import com.api.marvel.controller.dto.ComicDTO;
import com.api.marvel.controller.dto.ThumbnailDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * {
 * "code": 200,
 * "status": "Ok",
 * "etag": "f0fbae65eb2f8f28bdeea0a29be8749a4e67acb3",
 * "data": {
 * "offset": 0,
 * "limit": 20,
 * "total": 30920,
 * "count": 20,
 * "results": [{array of objects}}]
 * }
 * }
 */
@Component
@Slf4j
public class ComicMapper {

    public List<ComicDTO> getComicDTOList(JsonNode response) {
        if (response == null) {
            throw new IllegalArgumentException("El nodo json no puede ser null");
        }

        JsonNode dataNode = response.get("data");
        ArrayNode arrayNode = (ArrayNode) dataNode.get("results");

        List<ComicDTO> comicList = new ArrayList<>();

        arrayNode.elements().forEachRemaining(comic -> {
            ComicDTO dtoResult = this.getComicDTO(comic);
            comicList.add(dtoResult);
        });
        return comicList;
    }

    public ComicDTO getComicDTO(JsonNode jsonNode) {
        if (jsonNode == null) {
            throw new IllegalArgumentException("El nodo json no puede ser null");
        }


        ComicDTO comicDTO = new ComicDTO(
                jsonNode.get("id").asInt(),
                jsonNode.get("title").asText(),
                jsonNode.get("description").asText(),
                jsonNode.get("pageCount").asInt(),
                jsonNode.get("resourceURI").asText()
        );

        return comicDTO;
    }

    public ComicDTO getComicInfoDTO(JsonNode jsonNode) {
        if (jsonNode == null) {
            throw new IllegalArgumentException("El nodo json no puede ser null");
        }

        JsonNode dataNode = jsonNode.get("data");
        ArrayNode arrayNode = (ArrayNode) dataNode.get("results");

        if (arrayNode.size() > 0) {
            JsonNode comicNode = arrayNode.get(0);

            ComicDTO comicDTO = new ComicDTO(
                    comicNode.get("id").asInt(),
                    comicNode.get("title").asText(),
                    comicNode.get("description").asText(),
                    comicNode.get("pageCount").asInt(),
                    comicNode.get("resourceURI").asText()
            );
            return comicDTO;
        }
        return null;
    }
}
