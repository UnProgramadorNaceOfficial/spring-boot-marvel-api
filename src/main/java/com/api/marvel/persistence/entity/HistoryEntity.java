package com.api.marvel.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "history_users")
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String url;

    @Column(name = "http_method")
    private String httpMethod;

    private String username;

    private LocalDateTime timestamp;

    @Column(name = "remote_address")
    private String remoteAddress;
}
