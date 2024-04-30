package com.api.marvel.service.impl;

import com.api.marvel.controller.dto.HistoricDTO;
import com.api.marvel.persistence.repository.HistoryRepository;
import com.api.marvel.service.IHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl implements IHistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public List<HistoricDTO> findAll() {
        return historyRepository.findAll()
                .stream()
                .map(entity -> new HistoricDTO(
                        entity.getId(),
                        entity.getUrl(),
                        entity.getHttpMethod(),
                        entity.getUsername(),
                        entity.getTimestamp(),
                        entity.getRemoteAddress()))
                .collect(Collectors.toList());
    }

    @Override
    public List<HistoricDTO> findByName(String username) {
        return historyRepository.findHistoryEntitiesByUsername(username)
                .stream()
                .map(entity -> new HistoricDTO(
                        entity.getId(),
                        entity.getUrl(),
                        entity.getHttpMethod(),
                        entity.getUsername(),
                        entity.getTimestamp(),
                        entity.getRemoteAddress()))
                .collect(Collectors.toList());
    }
}
