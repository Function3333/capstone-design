package capstone_back.account;

import capstone_back.board.Board;
import capstone_back.comment.Comment;
import capstone_back.etc.dto.RegisterDto;
import capstone_back.mention.Mention;
import capstone_back.message.Message;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "account")
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Mention> mentionList = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Message> sendMessageList = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private List<Message> receiveMessageList = new ArrayList<>();


    private String email;
    private String username;
    private String password;

    public Account createAccount(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        return this;
    }

    public Account dtoToAccount(RegisterDto registerDto) {
        this.email = registerDto.getEmail();
        this.username = registerDto.getUsername();
        this.password = registerDto.getPassword();
        return this;
    }

}
