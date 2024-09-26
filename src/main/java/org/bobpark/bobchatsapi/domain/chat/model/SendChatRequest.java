package org.bobpark.bobchatsapi.domain.chat.model;

import lombok.Builder;

@Builder
public record SendChatRequest(ChatRoomRequest room,
                              String userId,
                              String contents) {
}
