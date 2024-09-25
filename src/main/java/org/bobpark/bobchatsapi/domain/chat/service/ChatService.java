package org.bobpark.bobchatsapi.domain.chat.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import org.bobpark.bobchatsapi.domain.chat.model.SendChatRequest;
import org.bobpark.bobchatsapi.domain.chat.model.ChatResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final List<RSocketRequester> participants = new ArrayList<>();

    public void onConnect(RSocketRequester requester) {

        log.debug("connected..");

        requester.rsocket()
            .onClose()
            .doFirst(() -> participants.add(requester))
            .doOnError(error -> {
            })
            .doFinally(consumer -> participants.remove(requester))
            .subscribe();
    }

    public Mono<ChatResponse> message(SendChatRequest sendRequest) {
        this.sendMessage(sendRequest);
        return Mono.just(
            ChatResponse.builder()
                .userId(sendRequest.userId())
                .contents(sendRequest.contents())
                .createdDate(LocalDateTime.now())
                .build());
    }

    public void sendMessage(SendChatRequest sendRequest) {
        Flux.fromIterable(participants)
            .publishOn(Schedulers.boundedElastic())
            .doOnNext(ea -> ea.route("")
                .data(sendRequest)
                .send()
                .subscribe())
            .subscribe();

        log.debug("sent message...");
    }
}
