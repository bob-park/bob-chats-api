package org.bobpark.bobchatsapi.domain.chat.model;

import static org.apache.commons.lang3.ObjectUtils.*;

import java.util.List;

public record CreateChatRoomRequest(String name,
                                    String description,
                                    List<UserRequest> users) {

    public CreateChatRoomRequest {
        users = defaultIfNull(users, List.of());
    }
}
