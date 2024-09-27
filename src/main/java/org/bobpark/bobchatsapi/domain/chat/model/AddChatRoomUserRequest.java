package org.bobpark.bobchatsapi.domain.chat.model;

import java.util.List;

public record AddChatRoomUserRequest(List<UserRequest> users) {
}
