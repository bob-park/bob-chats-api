package org.bobpark.bobchatsapi.domain.chat.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.bobpark.bobchatsapi.domain.chat.model.AddChatRoomUserRequest;
import org.bobpark.bobchatsapi.domain.chat.model.ChatRoomResponse;
import org.bobpark.bobchatsapi.domain.chat.model.ChatRoomUserResponse;
import org.bobpark.bobchatsapi.domain.chat.model.CreateChatRoomRequest;
import org.bobpark.bobchatsapi.domain.chat.model.SearchChatRoomRequest;
import org.bobpark.bobchatsapi.domain.chat.service.ChatRoomService;

@RequiredArgsConstructor
@RestController
@RequestMapping("chat/room")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public Mono<ChatRoomResponse> createRoom(@RequestBody CreateChatRoomRequest createRequest) {
        return chatRoomService.createRoom(createRequest);
    }

    @GetMapping(path = "all")
    public Flux<ChatRoomResponse> getAll(SearchChatRoomRequest searchRequest) {
        return chatRoomService.getAll(searchRequest);
    }

    @GetMapping(path = "{roomId:\\d+}/users")
    public Flux<ChatRoomUserResponse> getUsers(@PathVariable Long roomId) {
        return chatRoomService.getUsers(roomId);
    }

    @PostMapping(path = "{roomId:\\d+}/users")
    public Flux<ChatRoomUserResponse> addUsers(@PathVariable Long roomId, @RequestBody AddChatRoomUserRequest addRequest) {
        return chatRoomService.addUser(roomId, addRequest);
    }
}
