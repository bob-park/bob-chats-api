package org.bobpark.bobchatsapi.domain.chat.repository.query.impl;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;

import reactor.core.publisher.Mono;

import org.bobpark.bobchatsapi.domain.chat.entity.Chat;
import org.bobpark.bobchatsapi.domain.chat.repository.ChatRepository;
import org.bobpark.bobchatsapi.domain.chat.repository.query.ChatQueryRepository;

@RequiredArgsConstructor
public class ChatQueryRepositoryImpl implements ChatQueryRepository {

    private final DatabaseClient client;

    @Override
    public Mono<Page<Chat>> findAllByRoomId(long roomId, Pageable pageable) {

        StringBuilder query = new StringBuilder();
        StringBuilder countQuery = new StringBuilder();

        // select
        query.append("select id, room_id, user_id, contents, created_date, last_modified_date ");
        countQuery.append("select count(id) as count ");

        // from
        query.append("from chats ");
        countQuery.append("from chats ");

        // where
        query.append(String.format("where room_id = %d ", roomId));
        countQuery.append(String.format("where room_id = %d ", roomId));

        // order by
        query.append("order by created_date desc ");

        // pagination
        query.append(String.format("limit %s offset %d", pageable.getPageSize(), pageable.getOffset()));

        Mono<Long> countMono =
            client.sql(countQuery.toString())
                .map((row, rowMetadata) -> row.get("count", Long.class))
                .one();

        return client.sql(query.toString())
            .map((row, rowMetadata) ->
                Chat.builder()
                    .id(row.get("id", Long.class))
                    .roomId(row.get("room_id", Long.class))
                    .userId(row.get("user_id", String.class))
                    .contents(row.get("contents", String.class))
                    .createdDate(row.get("created_date", LocalDateTime.class))
                    .lastModifiedDate(row.get("last_modified_date", LocalDateTime.class))
                    .build())
            .all()
            .collectList()
            .zipWith(countMono)
            .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));

    }
}
