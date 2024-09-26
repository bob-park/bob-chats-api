package org.bobpark.bobchatsapi.domain.chat.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record ChatRoomResponse(Long id,
                               String name,
                               String description,
                               LocalDateTime createdDate,
                               LocalDateTime lastModifiedDate,
                               List<ChatRoomUserResponse> users) {
}
