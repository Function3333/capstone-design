package capstone_back.message;

import capstone_back.account.Account;
import capstone_back.account.AccountRepository;
import capstone_back.etc.dto.MessageReturnDto;
import capstone_back.etc.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;
    private final JwtService jwtService;

    @Transactional
    public void sendMessage(Account sender, Account receiver, Message message) {
        message.setSender(sender);
        message.setReceiver(receiver);
        messageRepository.save(message);
    }

    /*수신자 이메일이 존재하는지 확인*/
    public boolean validateReceiverIsNull(String email) {
        List<Account> byEmail = accountRepository.findByEmail(email);

        if(byEmail.isEmpty()) return true;
        return false;
    }

    public List<MessageReturnDto> getMessages(String email) {
        List<Account> byEmail = accountRepository.findByEmail(email);
        Account account = byEmail.get(0);

        List<Message> byReceiverId = messageRepository.findByReceiverId(account.getId());
        List<MessageReturnDto> messageList = new ArrayList<>();

        for (Message message : byReceiverId) {
            MessageReturnDto messageReadDto = new MessageReturnDto().createMessageReadDto(message);
            messageList.add(messageReadDto);
        }

        return messageList;
    }

    public MessageReturnDto getMessage(Long id) {
        Message message = messageRepository.findById(id);

        return new MessageReturnDto().createMessageReadDto(message);
    }

    public boolean validateAuthority(Long message_id, Account requestAccount) {
        Message message = messageRepository.findById(message_id);

        Account sender = message.getSender();
        Account receiver = message.getReceiver();

        if(requestAccount.equals(sender) || requestAccount.equals(receiver)) {
            return true;
        }

        return false;
    }

    public boolean validateSendToMe(String receiver, String userEmail) {
        if(userEmail.equals(receiver)) {
            return true;
        }
        return false;
    }

}
