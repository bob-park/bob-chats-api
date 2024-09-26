package org.bobpark.bobchatsapi.domain.chat.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import org.bobpark.bobchatsapi.domain.chat.entity.ChatRoom;
import org.bobpark.bobchatsapi.domain.chat.repository.query.ChatRoomQueryRepository;

@Repository
public interface ChatRoomRepository extends ReactiveCrudRepository<ChatRoom, Long>, ChatRoomQueryRepository {
}
