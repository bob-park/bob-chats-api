package org.bobpark.bobchatsapi.domain.chat.model;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ChatResponse(Long id,
                           ChatRoomResponse room,
                           String userId,
                           String contents,
                           LocalDateTime createdDate,
                           LocalDateTime lastModifiedDate) {
}
