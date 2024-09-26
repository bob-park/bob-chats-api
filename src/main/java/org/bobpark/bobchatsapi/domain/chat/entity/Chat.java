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

import org.apache.commons.lang3.StringUtils;

import org.bobpark.bobchatsapi.common.BaseEntity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chats")
public class Chat extends BaseEntity {

    @Id
    private Long id;

    private Long roomId;

    private String userId;
    private String contents;

    @Transient
    private ChatRoom room;

    @Builder
    private Chat(Long id, Long roomId, String userId, String contents) {

        checkArgument(isNotEmpty(roomId), "roomId must be provided.");
        checkArgument(StringUtils.isNotBlank(userId), "userId must be provided.");
        checkArgument(StringUtils.isNotBlank(contents), "contents must be provided.");

        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
        this.contents = contents;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
    }
}
