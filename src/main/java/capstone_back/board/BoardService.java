package capstone_back.board;

import capstone_back.account.Account;
import capstone_back.account.AccountService;
import capstone_back.etc.dto.BoardDto;
import capstone_back.etc.timeStamp.CurrentTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final AccountService accountService;
    private final BoardRepository boardRepository;

    @Transactional
    public Long join(BoardDto boardDto, String email) {
        Board board = new Board().createBoard(boardDto);

        board.setCreated_at(new CurrentTime().currentTimeToTimeStamp());
        board.setStatus("true");
        Account account = accountService.findByEmail(email);
        board.setAccount(account);

        return boardRepository.save(board);
    }

    @Transactional
    public void delete(Long board_id) {
        boardRepository.delete(board_id);
    }

    public Board findById(Long id) {
        return boardRepository.findById(id);
    }

    public List<BoardDto> findByCategory_id(Long category_id) {
        List<Board> categoryList = boardRepository.findByCategory_id(category_id);

        List<BoardDto> boardDtoList = new ArrayList<>();
        for(Board board : categoryList) {
            BoardDto boardDto = new BoardDto().boardToDto(board);
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    public boolean loginAccountMatchBoarAccount(Account account, Account boardAccount) {
        if(account.getEmail().equals(boardAccount.getEmail())) return true;
        return false;
    }

    public List<BoardDto> findAll() {
        List<Board> all = boardRepository.findAll();

        List<BoardDto> boardDtoList = new ArrayList<>();
        for(Board board : all) {
            BoardDto boardDto = new BoardDto().boardToDto(board);
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }
}
