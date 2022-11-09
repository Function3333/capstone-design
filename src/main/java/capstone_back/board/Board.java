package capstone_back.board;

import capstone_back.account.Account;
import capstone_back.comment.Comment;
import capstone_back.etc.dto.BoardDto;
import capstone_back.etc.timeStamp.CurrentTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account account;

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    private String title;
    private String text;
    private String image;
    private String price;
    private Timestamp created_at;
    private Long category_id;
    private String status;

    public void setAccount(Account account) {
        account.getBoardList().add(this);
        this.account = account;
    }

    public Board createBoard(BoardDto boardDto, Timestamp timestamp, String status) {
        this.title = boardDto.getTitle();
        this.text = boardDto.getText();
        this.image = boardDto.getImage();
        this.price = boardDto.getPrice();
        this.category_id = boardDto.getCategory_id();
        this.created_at = timestamp;
        this.status = "true";
        return this;
    }
}
