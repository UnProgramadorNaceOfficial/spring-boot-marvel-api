package com.api.marvel.mapper;

import com.api.marvel.controller.dto.CharacterDTO;
import com.api.marvel.controller.dto.CharacterInfoDTO;
import com.api.marvel.controller.dto.ThumbnailDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
public class CharacterMapper {
    public List<CharacterDTO> getCharacterDTOList(JsonNode response) {
        if (response == null) {
            throw new IllegalArgumentException("El nodo json no puede ser null");
        }

        JsonNode dataNode = response.get("data");
        ArrayNode arrayNode = (ArrayNode) dataNode.get("results");

        List<CharacterDTO> characterList = new ArrayList<>();

        arrayNode.elements().forEachRemaining(character -> {
            CharacterDTO dtoResult = this.getCharacterDTO(character);
            characterList.add(dtoResult);
        });
        return characterList;
    }

    public CharacterDTO getCharacterDTO(JsonNode jsonNode) {
        if (jsonNode == null) {
            throw new IllegalArgumentException("El nodo json no puede ser null");
        }

        CharacterDTO characterDTO = new CharacterDTO(
                jsonNode.get("id").asInt(),
                jsonNode.get("name").asText(),
                jsonNode.get("description").asText(),
                jsonNode.get("modified").asText(),
                jsonNode.get("resourceURI").asText()
        );

        return characterDTO;
    }

    public CharacterInfoDTO getCharacterInfoDTO(JsonNode jsonNode) {
        if (jsonNode == null) {
            throw new IllegalArgumentException("El nodo json no puede ser null");
        }

        JsonNode dataNode = jsonNode.get("data");
        ArrayNode arrayNode = (ArrayNode) dataNode.get("results");

        if (arrayNode.size() > 0) {
            JsonNode characterNode = arrayNode.get(0);
            JsonNode thumbnailNode = characterNode.get("thumbnail");

            ThumbnailDTO thumbnailDTO = new ThumbnailDTO(
                    thumbnailNode.get("path").asText(),
                    thumbnailNode.get("extension").asText()
            );

            String imagePath = thumbnailDTO.path().concat(".").concat(thumbnailDTO.extension());

            CharacterInfoDTO characterInfoDTO = new CharacterInfoDTO(
                    characterNode.get("description").asText(),
                    imagePath
            );

            return characterInfoDTO;
        }
        return null;
    }
}
