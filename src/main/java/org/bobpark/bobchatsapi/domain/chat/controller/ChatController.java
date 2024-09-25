package org.bobpark.bobchatsapi.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Mono;

import org.bobpark.bobchatsapi.domain.chat.model.ChatResponse;
import org.bobpark.bobchatsapi.domain.chat.model.SendChatRequest;
import org.bobpark.bobchatsapi.domain.chat.service.ChatService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    @ConnectMapping
    public void onConnect(RSocketRequester requester) {
        chatService.onConnect(requester);
    }

    @MessageMapping("chat")
    public Mono<ChatResponse> message(SendChatRequest sendRequest) {
        return chatService.message(sendRequest);
    }

    @MessageMapping("send")
    public Mono<Void> sendMessage(SendChatRequest sendRequest) {
        chatService.sendMessage(sendRequest);

        return Mono.empty();
    }

}
