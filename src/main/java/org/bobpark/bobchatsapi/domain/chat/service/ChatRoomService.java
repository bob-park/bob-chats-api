package org.bobpark.bobchatsapi.domain.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.bobpark.bobchatsapi.domain.chat.entity.ChatRoom;
import org.bobpark.bobchatsapi.domain.chat.model.ChatRoomResponse;
import org.bobpark.bobchatsapi.domain.chat.model.ChatRoomUserResponse;
import org.bobpark.bobchatsapi.domain.chat.model.CreateChatRoomRequest;
import org.bobpark.bobchatsapi.domain.chat.model.UserResponse;
import org.bobpark.bobchatsapi.domain.chat.repository.ChatRoomRepository;
import org.bobpark.bobchatsapi.domain.chat.repository.ChatRoomUserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;

    @Transactional
    public Mono<ChatRoomResponse> createRoom(CreateChatRoomRequest createRequest) {

        ChatRoom createdRoom =
            ChatRoom.builder()
                .name(createRequest.name())
                .description(createRequest.description())
                .build();

        Mono<ChatRoom> result = chatRoomRepository.save(createdRoom);

        return result.map(this::from);

    }

    public Flux<ChatRoomResponse> getAll() {
        return chatRoomRepository.findAll()
            .map(this::from);
    }

    public Flux<ChatRoomUserResponse> getUsers(Long roomId) {
        return chatRoomUserRepository.findByRoomId(roomId)
            .map(item ->
                ChatRoomUserResponse.builder()
                    .id(item.getId())
                    .user(
                        UserResponse.builder()
                            .userId(item.getUserId())
                            .build())
                    .build());
    }

    private ChatRoomResponse from(ChatRoom chatRoom) {
        return ChatRoomResponse.builder()
            .id(chatRoom.getId())
            .name(chatRoom.getName())
            .description(chatRoom.getDescription())
            .createdDate(chatRoom.getCreatedDate())
            .lastModifiedDate(chatRoom.getLastModifiedDate())
            .build();
    }

}
