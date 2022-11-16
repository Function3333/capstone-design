package capstone_back.utils.dto;

import capstone_back.domain.Message;
import lombok.Data;

@Data
public class MessageReturnDto {
    private Long message_id;
    private String title;
    private String content;
    private String sender;

    public MessageReturnDto createMessageReadDto(Message message) {
        this.message_id = message.getId();
        this.title = message.getTitle();
        this.content = message.getContent();
        this.sender = message.getSender().getEmail();
        return this;
    }
}
