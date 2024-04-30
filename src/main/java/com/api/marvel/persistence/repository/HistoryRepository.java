package com.api.marvel.persistence.repository;

import com.api.marvel.persistence.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {

//    @Query("SELECT hr FROM HistoryEntity hr WHERE hr.username = ?")
//    List<HistoryEntity> findByUsername(String username);

    List<HistoryEntity> findHistoryEntitiesByUsername(String username);
}
