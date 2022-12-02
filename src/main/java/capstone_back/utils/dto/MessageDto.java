package capstone_back.utils.dto;

import lombok.Data;

@Data
public class MessageDto {
    private Long id;
    private String title;
    private String content;
    private String receiver;
    private String boardtitle;
}
