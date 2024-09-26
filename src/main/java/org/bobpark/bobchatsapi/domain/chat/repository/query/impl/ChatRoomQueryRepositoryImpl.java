package org.bobpark.bobchatsapi.domain.chat.repository.query.impl;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.RequiredArgsConstructor;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

import org.bobpark.bobchatsapi.domain.chat.entity.ChatRoom;
import org.bobpark.bobchatsapi.domain.chat.repository.query.ChatRoomQueryRepository;

@RequiredArgsConstructor
@Repository
public class ChatRoomQueryRepositoryImpl implements ChatRoomQueryRepository {

    private final DatabaseClient client;

    @Override
    public Flux<ChatRoom> findRoomAll() {

        return Flux.empty();
    }

}
