package capstone_back.domain;

import capstone_back.domain.Account;
import capstone_back.domain.Board;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity //이게 붙으면 Comment table을 DB에 자동 생성해줌
@Getter @Setter //setAccount, getAccount, toString()등 자동생성
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //jpa가 db에 데이터 넣을때 pk 자동증가하게 해줌
    private Long id;

    /*여러개의 comment들은 하나의 account를 가진다*/
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account account;

    /*여러개의 comment들은 하나의 board를 가진다*/
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private String text;

    public void setAccount(Account account) {
        account.getCommentList().add(this);
        this.account = account;
    }
    public void setBoard(Board board) {
        board.getCommentList().add(this);
        this.board = board;
    }
}
