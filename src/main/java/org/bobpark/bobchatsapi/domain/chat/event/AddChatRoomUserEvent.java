package org.bobpark.bobchatsapi.domain.chat.event;

import lombok.Builder;

@Builder
public record AddChatRoomUserEvent(long roomId, String userId) {
}
