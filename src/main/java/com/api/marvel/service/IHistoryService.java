package com.api.marvel.service;

import com.api.marvel.controller.dto.HistoricDTO;

import java.util.List;

public interface IHistoryService {
    List<HistoricDTO> findAll();
    List<HistoricDTO> findByName(String username);
}
