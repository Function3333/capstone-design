package capstone_back.utils.dto;

import capstone_back.domain.Board;
import capstone_back.domain.Image;
import lombok.Getter;
import lombok.Setter;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

@Getter @Setter
public class ImageReturnForm {
    Long image_id;
    Long board_id;
    Blob image;

    public ImageReturnForm(Image image, Board board) {
        this.image_id = image.getId();
        this.board_id = board.getId();
        this.image = newImage(image);
    }

    /*DB에 저장된 Blob을 바로 주니까 데이터가 제대로 전달이 안되서 새로운 Blob을 만들어서 DB blob을 넣은 뒤 전달하는 방식으로 했습니다*/
    public Blob newImage(Image image) {
        byte[] bytes = new byte[0];
        try {
            bytes = image.getImage().getBytes(1l, (int) image.getImage().length());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Blob blob = null;
        try {
            blob = new SerialBlob(bytes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return blob;
    }
}
