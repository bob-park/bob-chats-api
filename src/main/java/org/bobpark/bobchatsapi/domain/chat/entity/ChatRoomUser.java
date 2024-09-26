package org.bobpark.bobchatsapi.domain.chat.entity;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_rooms_users")
public class ChatRoomUser {

    @Id
    private Long id;

    private Long roomId;

    private String userId;

    @Transient
    private ChatRoom room;

    @Builder
    private ChatRoomUser(Long id, Long roomId, String userId) {

        checkArgument(isNotEmpty(roomId), "roomId must be provided.");
        checkArgument(isNotEmpty(userId), "userId must be provided.");

        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
    }
}
