package org.bobpark.bobchatsapi.domain.chat.model;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ChatRoomResponse(Long id,
                               String name,
                               String description,
                               String userId,
                               LocalDateTime createdDate,
                               LocalDateTime lastModifiedDate) {
}
