package capstone_back.etc.dto;

import capstone_back.message.Message;
import lombok.Data;

@Data
public class MessageReadDto {
    private Long message_id;
    private String title;
    private String content;
    private Long sender;

    public MessageReadDto createMessageReadDto(Message message) {
        this.message_id = message.getId();
        this.title = message.getTitle();
        this.content = message.getContent();
        this.sender = message.getSender().getId();
        return this;
    }
}
