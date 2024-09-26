package org.bobpark.bobchatsapi.domain.chat.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;

import org.bobpark.bobchatsapi.domain.chat.entity.ChatRoomUser;

public interface ChatRoomUserRepository extends ReactiveCrudRepository<ChatRoomUser, Long> {

    Flux<ChatRoomUser> findByRoomId(Long roomId);
}
