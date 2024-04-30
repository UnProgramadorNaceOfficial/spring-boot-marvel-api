package com.api.marvel.service.impl;

import com.api.marvel.controller.dto.HistoricDTO;
import com.api.marvel.persistence.entity.HistoryEntity;
import com.api.marvel.persistence.repository.HistoryRepository;
import com.api.marvel.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public List<HistoricDTO> findAll() {
        return this.historyRepository.findAll()
                .stream()
                .map(history -> {
                    return new HistoricDTO(
                            history.getId(),
                            history.getUrl(),
                            history.getHttpMethod(),
                            history.getUsername(),
                            history.getTimestamp(),
                            history.getRemoteAddress()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public HistoricDTO findById(Long id) {
        HistoryEntity historyEntity = this.historyRepository.findById(id).orElseGet(() -> null);

        if (historyEntity == null) {
            return new HistoricDTO(null, null, null, null, null, null);
        }

        return new HistoricDTO(
                historyEntity.getId(),
                historyEntity.getUrl(),
                historyEntity.getHttpMethod(),
                historyEntity.getUsername(),
                historyEntity.getTimestamp(),
                historyEntity.getRemoteAddress());
    }
}
