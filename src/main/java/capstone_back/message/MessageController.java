package capstone_back.message;

import capstone_back.account.Account;
import capstone_back.account.AccountService;
import capstone_back.etc.dto.MessageDto;
import capstone_back.etc.dto.MessageReadDto;
import capstone_back.etc.jwt.JwtService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MessageController {
    private final AccountService accountService;
    private final MessageService messageService;
    private final JwtService jwtService;

    @GetMapping("/messages")
    public Response readMessages(HttpServletRequest request) {
        String email = jwtService.getEmail(request);
        List<MessageReadDto> messages = messageService.getMessages(email);
        return new Response("success", messages);
    }

    @GetMapping("/message")
    public Response readMessage(@RequestParam Long message_id, HttpServletRequest request) {
        Account requestAccount = jwtService.headerToAccount(request);

        if(messageService.validateAuthority(message_id, requestAccount)) {
            MessageReadDto message = messageService.getMessage(message_id);
            return new Response("success", message);
        }

        return new Response("fail", "only receiver and sender can read message");
    }

    @PostMapping("/message")
    public Response createMessage(@RequestBody MessageDto messageDto, HttpServletRequest request) {
        if(messageService.checkReceiverIsNull(messageDto.getReceiver())) {
            return new Response("fail", "receiver is null");
        }

        Message message = new Message().createMessage(messageDto.getTitle(), messageDto.getContent());

        Account sender = jwtService.headerToAccount(request);
        Account receiver = accountService.findByEmail(messageDto.getReceiver());

        messageService.sendMessage(sender, receiver, message);
        return new Response("success", messageDto);
    }

    @Data
    public class Response {
        String result;
        Object data;

        public Response(String result, Object data) {
            this.result = result;
            this.data = data;
        }
    }
}


