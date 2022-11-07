package capstone_back.mention;

import capstone_back.account.Account;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity //이게 붙으면 Mention table을 DB에 자동 생성해줌
@Getter @Setter //setAccount, getAccount, toString()등 자동생성
public class Mention {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account account;

    private String title;
    private String text;
    private String to_user_id;

    public void setAccount(Account account) {
        account.getMentionList().add(this);
        this.account = account;
    }
}
