package capstone_back.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Getter @Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Blob image;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public void setBoard(Board board) {
        this.board = board;
        board.getImageList().add(this);
    }
}
