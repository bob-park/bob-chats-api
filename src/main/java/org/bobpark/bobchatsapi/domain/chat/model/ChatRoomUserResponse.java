package org.bobpark.bobchatsapi.domain.chat.model;

import lombok.Builder;

@Builder
public record ChatRoomUserResponse(Long id,
                                   UserResponse user) {
}
