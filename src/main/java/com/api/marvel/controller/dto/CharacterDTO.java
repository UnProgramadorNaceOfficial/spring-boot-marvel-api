package com.api.marvel.controller.dto;

import java.util.Date;
public record CharacterDTO(int id, String name, String description, String modified, String resourceURI) {}
