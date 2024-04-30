package com.api.marvel.controller.dto;

import java.time.LocalDateTime;

public record HistoricDTO(
        Long id,
        String url,
        String httpMethod,
        String username,
        LocalDateTime timestamp,
        String remoteAddress) {
}
