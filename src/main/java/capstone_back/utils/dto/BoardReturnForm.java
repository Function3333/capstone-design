package capstone_back.utils.dto;

import capstone_back.domain.Board;
import capstone_back.domain.Image;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class BoardReturnForm {
    private Long board_id;
    private String title;
    private String text;
    private String price;
    private Timestamp created_at;
    private String category_id;
    private String status;
    private Long user_id;
    private String email;
    private List<ImageReturnForm> imageReturnFormList;

    public BoardReturnForm(Board board) {
        this.board_id = board.getId();
        this.title = board.getTitle();
        this.text = board.getText();
        this.price = board.getPrice();
        this.created_at = board.getCreated_at();
        this.category_id = board.getCategory_id();
        this.status = board.getStatus();
        this.user_id = board.getAccount().getId();
        this.email = board.getAccount().getEmail();
        this.imageReturnFormList = imageToImageReturnForm(board);
    }

    public List<ImageReturnForm> imageToImageReturnForm(Board board) {
        List<Image> images = board.getImageList();
        List<ImageReturnForm> imageReturnFormList = new ArrayList<>();

        for(Image image : images) {
            ImageReturnForm imageReturnForm = new ImageReturnForm(image, board);
            imageReturnFormList.add(imageReturnForm);
        }

        return imageReturnFormList;
    }
}
