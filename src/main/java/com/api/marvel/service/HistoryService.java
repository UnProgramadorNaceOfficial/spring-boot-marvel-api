package com.api.marvel.service;

import com.api.marvel.controller.dto.HistoricDTO;

import java.util.List;

public interface HistoryService {
    List<HistoricDTO> findAll();
    HistoricDTO findById(Long id);
}
