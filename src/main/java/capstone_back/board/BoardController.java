package capstone_back.board;

import capstone_back.account.Account;
import capstone_back.account.AccountService;
import capstone_back.etc.dto.BoardDto;
import capstone_back.etc.jwt.JwtService;
import capstone_back.etc.timeStamp.CurrentTime;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BoardController {
    private final BoardService boardService;
    private final AccountService accountService;
    private final JwtService jwtService;

    @GetMapping("/boards/category")
    public Response boardList(@RequestParam Long category_id) throws JsonProcessingException {
        List<BoardDto> byCategory_id = boardService.findByCategoryId(category_id);
        return new Response("success", byCategory_id);
    }

    @GetMapping("/boards")
    public Response boards() {
        List<BoardDto> boards = boardService.findAll();
        return new Response("success", boards);
    }

    @GetMapping("/board")
    public Response board(@RequestParam Long board_id ) {
        Board board =  boardService.findById(board_id);
        BoardDto boardDto = new BoardDto().boardToDto(board);
        return new Response("success", boardDto);
    }

    @GetMapping("/board/user")
    public Response userBoard(@RequestParam Long user_id) {
        List<BoardDto> boardDtoList = boardService.findByUserId(user_id);

        return new Response("success" , boardDtoList);
    }

    @PostMapping("/board/add")
    public Response boardAdd(@RequestBody BoardDto boardDto, HttpServletRequest request) {
        try {
            String email = jwtService.getEmail(request);
            Long saveId = boardService.join(boardDto, email);

            return new Response("success", saveId);
        } catch (InvalidParameterException e ) {
            return new Response("fail", "tokenError");
        } catch (IndexOutOfBoundsException e) {
            return new Response("fail", "tokenError");
        }
    }


    @PostMapping("/board/delete")
    public Response boardDelete(@RequestParam Long board_id, HttpServletRequest request) {
        try {
            String email = jwtService.getEmail(request);
            Account loginAccount = accountService.findByEmail(email);

            Board board = boardService.findById(board_id);
            Account boardAccount = board.getAccount();

            if (boardService.loginAccountMatchBoarAccount(loginAccount, boardAccount)) {
                boardService.delete(board_id);
                return new Response("success", "delete");
            }

            return new Response("fail", "loginAccount not match boardAccount");

        } catch (InvalidParameterException e) {
            return new Response("fail", "tokenError");
        } catch (IndexOutOfBoundsException e) {
            return new Response("fail", "tokenError");
        }

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
