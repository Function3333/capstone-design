package capstone_back.etc.dto;

import capstone_back.board.Board;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class BoardDto {
    private Long board_id;
    private String title;
    private String text;
    private String image;
    private String price;
    private Timestamp created_at;
    private Long category_id;
    private String status;
    private Long user_id;
    private String email;


    public BoardDto boardToDto(Board board) {
        this.board_id = board.getId();
        this.title = board.getTitle();
        this.text = board.getText();
        this.image = board.getImage();
        this.price = board.getPrice();
        this.created_at = board.getCreated_at();
        this.category_id = board.getCategory_id();
        this.status = board.getStatus();
        this.user_id = board.getAccount().getId();
        this.email = board.getAccount().getEmail();
        return this;
    }
}
