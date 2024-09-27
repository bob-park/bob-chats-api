package org.bobpark.bobchatsapi.domain.chat.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import org.bobpark.bobchatsapi.domain.chat.model.ChatResponse;
import org.bobpark.bobchatsapi.domain.chat.model.ChatRoomResponse;
import org.bobpark.bobchatsapi.domain.chat.model.ChatRoomUserResponse;
import org.bobpark.bobchatsapi.domain.chat.model.CreateChatRoomRequest;
import org.bobpark.bobchatsapi.domain.chat.model.PageInfo;
import org.bobpark.bobchatsapi.domain.chat.model.SearchChatRoomRequest;
import org.bobpark.bobchatsapi.domain.chat.service.ChatRoomService;
import org.bobpark.bobchatsapi.domain.chat.service.ChatService;

@RequiredArgsConstructor
@RestController
@RequestMapping("chat/room")
public class ChatRoomController {

    private final ChatService chatService;
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

    @GetMapping(path = "{roomId:\\d+}")
    public Mono<ChatRoomResponse> getById(@PathVariable long roomId) {
        return chatRoomService.getById(roomId);
    }

    @GetMapping(path = "{roomId:\\d+}/users")
    public Flux<ChatRoomUserResponse> getUsers(@PathVariable Long roomId) {
        return chatRoomService.getUsers(roomId);
    }

    @PostMapping(path = "{roomId:\\d+}/users")
    public Flux<ChatRoomUserResponse> addUsers(@PathVariable Long roomId,
        @RequestBody AddChatRoomUserRequest addRequest) {
        return chatRoomService.addUser(roomId, addRequest);
    }

    @GetMapping(path = "{roomId:\\d+}/chats")
    public Mono<Page<ChatResponse>> getChats(@PathVariable Long roomId, PageInfo page) {
        return chatService.getAllByRoomId(roomId, PageRequest.of(page.page(), page.size()));
    }
}
