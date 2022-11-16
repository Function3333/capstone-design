package capstone_back.etc.dto;

import lombok.Data;

@Data
public class CreateMessageDto {
    private Long id;
    private String title;
    private String content;
    private String receiver;
}
