package capstone_back.controller;

import capstone_back.service.BoardService;
import capstone_back.domain.Account;
import capstone_back.domain.Board;
import capstone_back.service.AccountService;
import capstone_back.utils.dto.BoardDto;
import capstone_back.jwt.JwtService;
import capstone_back.utils.dto.BoardReturnForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class BoardController {
    private final BoardService boardService;
    private final AccountService accountService;
    private final JwtService jwtService;

    @GetMapping("/boards/category")
    public Response boardList(@RequestParam String category_id) {
        // Step 1. Category ID = "00" ( 상위 카테고리 ) | "00" ( 하위 카테고리 )
        String strCateID = category_id;
        String strMasterCategory = strCateID.substring(0,2);
        String strSlaveCategory = strCateID.substring(2,4);

        // 상위 카테고리 값이 00 일 경우, 전체 Board 요청과 같다.
        if(strMasterCategory.equals("00")) {
            List<BoardReturnForm> boards = boardService.findAll();
            return new Response("success", boards);
        }
        else {
            // 하위 카테고리 값이 00 일 경우, 상위 카테고리로 검색
            if (strSlaveCategory.equals("00")) {
                List<BoardReturnForm> byMasterCategory_id = boardService.findByMasterCategoryId(strMasterCategory);
                return new Response("success", byMasterCategory_id);
            }
            // 하위 카테고리 값이 00 이 아닐 경우, 하위 카테고리로 검색
            else {
                List<BoardReturnForm> byCategory_id = boardService.findByCategoryId(strCateID);
                return new Response("success", byCategory_id);
            }
        }
    }

    @GetMapping("/boards")
    public Response boards() {
        List<BoardReturnForm> boards = boardService.findAll();
        return new Response("success", boards);
    }

    @GetMapping("/board")
    public Response board(@RequestParam Long board_id ) {
        Board board =  boardService.findById(board_id);
        BoardReturnForm boardReturnForm = new BoardReturnForm(board);
        return new Response("success", boardReturnForm);
    }

    @GetMapping("/board/user")
    public Response userBoard(@RequestParam Long user_id) {
        List<BoardReturnForm> boardReturnForms = boardService.findByUserId(user_id);

        return new Response("success" , boardReturnForms);
    }

    @PostMapping("/board/status")
    public Response changeStatus(@RequestParam Long board_id, @RequestParam String status) {
        log.info("init changeStatus");
        log.info("boar_id = {}, status = {}", board_id, status);
        if(boardService.requestStatusValidate(status)) {
            boardService.changeStatus(board_id, status);
            return new Response("success", status);
        }
        return new Response("fail", "invalid status");
    }

    @GetMapping("/board/myboard")
    public Response myBoard(HttpServletRequest request)
    {
        try
        {
            String email = jwtService.getEmail(request);
            Account account = accountService.findByEmail(email);
            Long user_id = account.getId();
            List<BoardReturnForm> boardDtoList = boardService.findByUserId(user_id);

            return new Response("success", boardDtoList);
        }
        catch (InvalidParameterException e)
        {
            return new Response("fail", "tokenError");
        }
        catch (IndexOutOfBoundsException e)
        {
            return new Response("fail", "tokenError");
        }

    }

    @PostMapping("/board/add")
    public Response boardAdd(@ModelAttribute BoardDto boardDto, HttpServletRequest request) {
        try {
            String email = jwtService.getEmail(request);
            Long saveId = boardService.join(boardDto, email);

            return new Response("success", saveId);
        } catch (InvalidParameterException e ) {
            return new Response("fail", "tokenError");
        } catch (IndexOutOfBoundsException e) {
            return new Response("fail", "tokenError");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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