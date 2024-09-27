package org.bobpark.bobchatsapi.domain.chat.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import org.bobpark.bobchatsapi.common.BaseEntity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_rooms")
public class ChatRoom extends BaseEntity {

    @Id
    private Long id;

    private String name;
    private String description;

    @Exclude
    @Transient
    private List<ChatRoomUser> users = new ArrayList<>();

    @Exclude
    @Transient
    private List<Chat> chats = new ArrayList<>();

    @Builder
    private ChatRoom(Long id, String name, String description, LocalDateTime createdDate,
        LocalDateTime lastModifiedDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public void addUser(ChatRoomUser user) {
        user.setRoom(this);
        getUsers().add(user);
    }

    public void addChat(Chat chat) {
        chat.setRoom(this);
        getChats().add(chat);
    }
}
