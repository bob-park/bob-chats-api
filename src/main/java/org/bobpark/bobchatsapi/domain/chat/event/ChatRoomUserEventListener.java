package org.bobpark.bobchatsapi.domain.chat.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import reactor.core.publisher.Mono;

import org.bobpark.bobchatsapi.domain.chat.entity.ChatRoomUser;
import org.bobpark.bobchatsapi.domain.chat.repository.ChatRoomUserRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChatRoomUserEventListener {

    private final ChatRoomUserRepository chatRoomUserRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @EventListener
    public Mono<Void> addChatRoomUser(AddChatRoomUserEvent event) {

        ChatRoomUser createdUser =
            ChatRoomUser.builder()
                .roomId(event.roomId())
                .userId(event.userId())
                .build();



        log.debug("saved chat room user. (roomId={}, userId={})", event.roomId(), event.userId());

        return chatRoomUserRepository.save(createdUser).then();
    }

}
