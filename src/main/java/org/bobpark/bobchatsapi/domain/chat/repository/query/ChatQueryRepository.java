package org.bobpark.bobchatsapi.domain.chat.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Mono;

import org.bobpark.bobchatsapi.domain.chat.entity.Chat;

public interface ChatQueryRepository {

    Mono<Page<Chat>> findAllByRoomId(long roomId, Pageable pageable);

}
