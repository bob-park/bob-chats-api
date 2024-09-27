package org.bobpark.bobchatsapi.domain.chat.repository.query;

import reactor.core.publisher.Flux;

import org.bobpark.bobchatsapi.domain.chat.entity.ChatRoom;
import org.bobpark.bobchatsapi.domain.chat.model.SearchChatRoomRequest;

public interface ChatRoomQueryRepository {

    Flux<ChatRoom> findRoomAll();

    Flux<ChatRoom> search(SearchChatRoomRequest searchRequest);

}
