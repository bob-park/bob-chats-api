package org.bobpark.bobchatsapi.domain.chat.service;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import org.bobpark.bobchatsapi.domain.chat.entity.Chat;
import org.bobpark.bobchatsapi.domain.chat.model.ChatResponse;
import org.bobpark.bobchatsapi.domain.chat.model.ChatRoomResponse;
import org.bobpark.bobchatsapi.domain.chat.model.SendChatRequest;
import org.bobpark.bobchatsapi.domain.chat.model.SubscribeChatRoomRequest;
import org.bobpark.bobchatsapi.domain.chat.repository.ChatRepository;
import org.bobpark.bobchatsapi.domain.chat.repository.ChatRoomRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final Map<Long, List<RSocketRequester>> participants = Maps.newHashMap();

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    public void onConnect(RSocketRequester requester, SubscribeChatRoomRequest subRequest) {

        log.debug("connected.. (chatRoomId={})", subRequest.id());

        requester.rsocket()
            .onClose()
            .doFirst(() -> {
                List<RSocketRequester> requesters = participants.get(subRequest.id());

                if (requesters != null) {
                    requesters.add(requester);
                    return;
                }

                requesters = Lists.newArrayList();
                requesters.add(requester);

                participants.put(subRequest.id(), requesters);

            })
            .doOnError(error -> {
                log.error("rsocket error - {}", error.getMessage(), error);
            })
            .doFinally(consumer -> {
                List<RSocketRequester> requesters = participants.get(subRequest.id());

                if (requesters.contains(requester)) {
                    requesters.remove(requester);
                    log.debug("disconnected...");
                }

            })
            .subscribe();
    }

    public Mono<ChatResponse> message(SendChatRequest sendRequest) {
        return sendMessage(sendRequest);
    }

    @Transactional
    public Mono<ChatResponse> sendMessage(SendChatRequest sendRequest) {

        Long roomId = sendRequest.room().id();

        List<RSocketRequester> requesters = participants.getOrDefault(roomId, List.of());

        Chat createdChat =
            Chat.builder()
                .roomId(sendRequest.room().id())
                .userId(sendRequest.userId())
                .contents(sendRequest.contents())
                .build();

        return chatRepository.save(createdChat)
            .map(chat -> {
                ChatResponse result =
                    ChatResponse.builder()
                        .id(chat.getId())
                        .room(ChatRoomResponse.builder().id(chat.getRoomId()).build())
                        .userId(chat.getUserId())
                        .contents(chat.getContents())
                        .createdDate(chat.getCreatedDate())
                        .lastModifiedDate(chat.getLastModifiedDate())
                        .build();

                log.debug("saved chat. (id={})", chat.getId());

                return result;
            })
            .publishOn(Schedulers.boundedElastic())
            .doOnNext(item ->
                Flux.fromIterable(requesters)
                    .publishOn(Schedulers.boundedElastic())
                    .doOnNext(ea -> ea.route("")
                        .data(item)
                        .send()
                        .subscribe())
                    .subscribe());

    }

    @Transactional(readOnly = true)
    public Mono<Page<ChatResponse>> getAllByRoomId(long roomId, Pageable pageable) {
        return chatRepository.findAllByRoomId(roomId, pageable)
            .map(result ->
                result.map(item ->
                    ChatResponse.builder()
                        .id(item.getId())
                        .room(
                            ChatRoomResponse.builder()
                                .id(item.getRoomId())
                                .build())
                        .userId(item.getUserId())
                        .contents(item.getContents())
                        .createdDate(item.getCreatedDate())
                        .lastModifiedDate(item.getLastModifiedDate())
                        .build()));
    }

}
