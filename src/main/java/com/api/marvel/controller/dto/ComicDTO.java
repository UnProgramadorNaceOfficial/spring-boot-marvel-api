package com.api.marvel.controller.dto;

public record ComicDTO(int id, String title, String description, int pageCount, String resourceURI) {
}
