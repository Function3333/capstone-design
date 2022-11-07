package capstone_back.board;

import capstone_back.account.Account;
import capstone_back.comment.Comment;
import capstone_back.etc.dto.BoardDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity //이게 붙으면 Board table을 DB에 자동 생성해줌
@Getter @Setter//setAccount, getAccount, toString()등 자동생성
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)//jpa가 db에 데이터 넣을때 pk 자동증가하게 해줌
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account account;

    /*하나의 board는 여러개의 comment를 가진다*/
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

    public Board createBoard(BoardDto boardDto) {
        this.title = boardDto.getTitle();
        this.text = boardDto.getText();
        this.image = boardDto.getImage();
        this.price = boardDto.getPrice();
        this.category_id = boardDto.getCategory_id();
        this.status = boardDto.getStatus();
        return this;
    }

    public Board boardInit(Account account, String title, String text, String image, String price, Timestamp created_at, Long category_id, String status) {
        this.account = account;
        this.title = title;
        this.text = text;
        this.image = image;
        this.price = price;
        this.created_at = created_at;
        this.category_id = category_id;
        this.status = status;
        return this;
    }
}
