package capstone_back.utils.dto;

import capstone_back.domain.Board;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class BoardDto {
    private Long board_id;
    private String title;
    private String text;
    private String price;
    private Timestamp created_at;
    private String category_id;
    private String status;
    private Long user_id;
    private String email;
    private List<MultipartFile> imageList = new ArrayList<>();
}
