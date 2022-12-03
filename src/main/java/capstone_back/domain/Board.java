package capstone_back.domain;

import capstone_back.utils.BoardStatus;
import capstone_back.utils.dto.BoardDto;
import capstone_back.utils.timeStamp.CurrentTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.sql.Blob;
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

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Image> imageList = new ArrayList<>();

    private String title;
    private String text;
    private String price;
    private Timestamp created_at;
    private String category_id;
    private String status;

    public void setAccount(Account account) {
        account.getBoardList().add(this);
        this.account = account;
    }

    public Board createBoard(BoardDto boardDto) {
        this.title = boardDto.getTitle();
        this.text = boardDto.getText();
        this.price = boardDto.getPrice();
        this.category_id = boardDto.getCategory_id();
        this.created_at = CurrentTime.currentTimeToTimeStamp();
        this.status = BoardStatus.Wait.getValue();
        return this;
    }
}