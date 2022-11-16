package capstone_back.utils.dto;

import lombok.Data;

@Data
public class CreateMessageDto {
    private Long id;
    private String title;
    private String content;
    private String receiver;
}
