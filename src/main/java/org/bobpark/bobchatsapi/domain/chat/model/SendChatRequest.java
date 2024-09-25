package org.bobpark.bobchatsapi.domain.chat.model;

import lombok.Builder;

@Builder
public record SendChatRequest(String userId,
                              String contents) {
}
