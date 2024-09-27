package org.bobpark.bobchatsapi.domain.chat.repository.query.impl;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

import org.springframework.data.relational.core.sql.SQL;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import io.micrometer.common.util.StringUtils;
import reactor.core.publisher.Flux;

import org.bobpark.bobchatsapi.domain.chat.entity.ChatRoom;
import org.bobpark.bobchatsapi.domain.chat.model.SearchChatRoomRequest;
import org.bobpark.bobchatsapi.domain.chat.repository.query.ChatRoomQueryRepository;

@RequiredArgsConstructor
@Repository
public class ChatRoomQueryRepositoryImpl implements ChatRoomQueryRepository {

    private final DatabaseClient client;

    @Override
    public Flux<ChatRoom> findRoomAll() {

        return Flux.empty();
    }

    @Override
    public Flux<ChatRoom> search(SearchChatRoomRequest searchRequest) {

        StringBuilder queryBuilder = new StringBuilder();

        // select
        queryBuilder.append("select cr.id, cr.name, cr.description, cr.created_date, cr.last_modified_date ");

        // from
        queryBuilder.append("from chat_rooms cr ");

        // where
        queryBuilder.append("where 1=1 ");

        if (StringUtils.isNotBlank(searchRequest.userId())) {
            queryBuilder.append(String.format("and (select count(id) from chat_rooms_users where user_id='%s' and room_id = cr.id) > 0 ", searchRequest.userId()));
        }

        // order by
        queryBuilder.append("order by cr.name");

        return client.sql(queryBuilder.toString())
            .map((row, rowMetadata) ->
                ChatRoom.builder()
                    .id(row.get("id", Long.class))
                    .name(row.get("name", String.class))
                    .description(row.get("description", String.class))
                    .createdDate(row.get("created_date", LocalDateTime.class))
                    .lastModifiedDate(row.get("last_modified_date", LocalDateTime.class))
                    .build())
            .all();
    }

}
