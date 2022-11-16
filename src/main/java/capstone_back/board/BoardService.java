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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final AccountService accountService;
    private final BoardRepository boardRepository;

    @Transactional
    public Long join(BoardDto boardDto, String email) {
        Board board = new Board().createBoard(boardDto, new CurrentTime().currentTimeToTimeStamp(), "ture");

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

    public List<BoardDto> findByCategoryId(Long category_id) {
        List<Board> categoryList = boardRepository.findByCategoryId(category_id);

        Stream<Board> boardStream = categoryList.stream();
        List<BoardDto> boardDtoList = boardStream.map(board -> new BoardDto().boardToDto(board)).collect(Collectors.toList());

        return boardDtoList;
    }

    public boolean loginAccountMatchBoarAccount(Account account, Account boardAccount) {
        if(account.getEmail().equals(boardAccount.getEmail())) return true;
        return false;
    }

    public List<BoardDto> findAll() {
        List<Board> boardList = boardRepository.findAll();

        Stream<Board> boardStream = boardList.stream();
        List<BoardDto> boardDtoList = boardStream.map(board -> new BoardDto().boardToDto(board)).collect(Collectors.toList());
        return boardDtoList;
    }
}
