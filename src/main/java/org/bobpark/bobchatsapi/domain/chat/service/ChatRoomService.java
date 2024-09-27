package org.bobpark.bobchatsapi.domain.chat.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.bobpark.bobchatsapi.domain.chat.entity.ChatRoom;
import org.bobpark.bobchatsapi.domain.chat.entity.ChatRoomUser;
import org.bobpark.bobchatsapi.domain.chat.event.AddChatRoomUserEvent;
import org.bobpark.bobchatsapi.domain.chat.model.AddChatRoomUserRequest;
import org.bobpark.bobchatsapi.domain.chat.model.ChatRoomResponse;
import org.bobpark.bobchatsapi.domain.chat.model.ChatRoomUserResponse;
import org.bobpark.bobchatsapi.domain.chat.model.CreateChatRoomRequest;
import org.bobpark.bobchatsapi.domain.chat.model.SearchChatRoomRequest;
import org.bobpark.bobchatsapi.domain.chat.model.UserRequest;
import org.bobpark.bobchatsapi.domain.chat.model.UserResponse;
import org.bobpark.bobchatsapi.domain.chat.repository.ChatRoomRepository;
import org.bobpark.bobchatsapi.domain.chat.repository.ChatRoomUserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ChatRoomService {

    private final ApplicationEventPublisher eventPublisher;

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;

    @Transactional
    public Mono<ChatRoomResponse> createRoom(CreateChatRoomRequest createRequest) {

        ChatRoom createdRoom =
            ChatRoom.builder()
                .name(createRequest.name())
                .description(createRequest.description())
                .build();

        return chatRoomRepository.save(createdRoom)
            .doOnSuccess(item -> {

                for (UserRequest user : createRequest.users()) {
                    eventPublisher.publishEvent(
                        AddChatRoomUserEvent.builder()
                            .roomId(item.getId())
                            .userId(user.userId())
                            .build());
                }

            })
            .map(this::from);

    }

    public Flux<ChatRoomResponse> getAll(SearchChatRoomRequest searchRequest) {
        return chatRoomRepository.search(searchRequest)
            .map(this::from);
    }

    public Mono<ChatRoomResponse> getById(long roomId){
        return chatRoomRepository.findById(roomId)
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

    @Transactional
    public Flux<ChatRoomUserResponse> addUser(Long roomId, AddChatRoomUserRequest addRequest) {

        List<ChatRoomUser> createdUsers = Lists.newArrayList();

        for (UserRequest user : addRequest.users()) {
            ChatRoomUser createdChatRoomUser =
                ChatRoomUser.builder()
                    .roomId(roomId)
                    .userId(user.userId())
                    .build();

            createdUsers.add(createdChatRoomUser);
        }

        return chatRoomUserRepository.saveAll(createdUsers)
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
