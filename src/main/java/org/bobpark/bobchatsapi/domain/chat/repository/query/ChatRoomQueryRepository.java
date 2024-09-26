package org.bobpark.bobchatsapi.domain.chat.repository.query;

import reactor.core.publisher.Flux;

import org.bobpark.bobchatsapi.domain.chat.entity.ChatRoom;

public interface ChatRoomQueryRepository {

    Flux<ChatRoom> findRoomAll();

}
