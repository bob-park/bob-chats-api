package org.bobpark.bobchatsapi.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import org.bobpark.bobchatsapi.domain.chat.model.ChatResponse;
import org.bobpark.bobchatsapi.domain.chat.model.SendChatRequest;
import org.bobpark.bobchatsapi.domain.chat.model.SubscribeChatRoomRequest;
import org.bobpark.bobchatsapi.domain.chat.service.ChatService;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;

    @ConnectMapping("connect")
    public void onConnect(RSocketRequester requester, @Payload SubscribeChatRoomRequest subRequest) {
        chatService.onConnect(requester, subRequest);
    }

    @MessageMapping("chat.message")
    public Mono<ChatResponse> message(SendChatRequest sendRequest) {
        return chatService.message(sendRequest);
    }

    @MessageMapping("chat.send-message")
    public Mono<Void> sendMessage(SendChatRequest sendRequest) {
        chatService.sendMessage(sendRequest);

        return Mono.empty();
    }

}
