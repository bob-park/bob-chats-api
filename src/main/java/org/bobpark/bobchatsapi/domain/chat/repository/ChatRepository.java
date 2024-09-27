package org.bobpark.bobchatsapi.domain.chat.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import org.bobpark.bobchatsapi.domain.chat.entity.Chat;
import org.bobpark.bobchatsapi.domain.chat.repository.query.ChatQueryRepository;

public interface ChatRepository extends ReactiveCrudRepository<Chat, Long>, ChatQueryRepository {

}
