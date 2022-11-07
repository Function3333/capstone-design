package capstone_back.repositoryTest;

import capstone_back.account.Account;
import capstone_back.account.AccountService;
import capstone_back.board.Board;
import capstone_back.comment.Comment;
import capstone_back.mention.Mention;
import capstone_back.account.AccountRepository;
import capstone_back.board.BoardRepository;
import capstone_back.comment.CommentRepository;
import capstone_back.mention.MentionRepository;
import capstone_back.message.Message;
import capstone_back.message.MessageRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;

@SpringBootTest
public class RepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MentionRepository mentionRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountService accountService;

    @Test
    @Transactional
    void test1() {
        List<Board> boardList = boardRepository.findByCategory_id(0L);
        Assertions.assertThat(boardList.get(0).getTitle()).isEqualTo("test");
    }


    @Test
    @Transactional
    void findBySenderTest() {
        //given
        Account sender = new Account().createAccount("jmdw@naver.com", "sender", "sender");
        accountRepository.save(sender);

        Message messageOne = new Message().createMessage("messageOne", "messageOne");
        messageOne.setSender(sender);
        Message messageTwo = new Message().createMessage("messageTwo", "messageTwo");
        messageTwo.setSender(sender);

        messageRepository.save(messageOne);
        messageRepository.save(messageTwo);

        //when
        List<Message> bySenderId = messageRepository.findBySenderId(sender.getId());

        //then
        Assertions.assertThat(bySenderId.get(0).getTitle()).isEqualTo("messageOne");
        Assertions.assertThat(bySenderId.get(1).getTitle()).isEqualTo("messageTwo");
    }

    @Test
    @Transactional
    void findByReceiverId() {
        //given
        Account receiver = new Account().createAccount("jmdw@naver.com", "sender", "sender");
        accountRepository.save(receiver);

        Message messageOne = new Message().createMessage("messageOne", "messageOne");
        messageOne.setReceiver(receiver);
        Message messageTwo = new Message().createMessage("messageTwo", "messageTwo");
        messageTwo.setReceiver(receiver);

        messageRepository.save(messageOne);
        messageRepository.save(messageTwo);

        //when
        List<Message> byReceiverId = messageRepository.findByReceiverId(receiver.getId());

        //then
        Assertions.assertThat(byReceiverId.get(0).getTitle()).isEqualTo("messageOne");
        Assertions.assertThat(byReceiverId.get(1).getTitle()).isEqualTo("messageTwo");
    }

    @Test
    @Transactional
    void nullTest() {
        Account account = accountService.findByEmail("asdasdasd");
        Assertions.assertThat(account).isNull();
    }

}
