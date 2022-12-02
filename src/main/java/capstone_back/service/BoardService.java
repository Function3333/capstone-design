package capstone_back.service;

import capstone_back.domain.Account;
import capstone_back.domain.Board;
import capstone_back.repository.BoardRepository;
import capstone_back.utils.BoardStatus;
import capstone_back.utils.dto.BoardDto;
import capstone_back.utils.dto.BoardReturnForm;
import capstone_back.utils.dto.ImageReturnForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final AccountService accountService;
    private final BoardRepository boardRepository;
    private final ImageService imageService;


    @Transactional
    public Long join(BoardDto boardDto, String email) throws SQLException, IOException {
        Board board = new Board().createBoard(boardDto);

        Account account = accountService.findByEmail(email);
        board.setAccount(account);

        Long saveId = boardRepository.save(board);

        imageService.saveImage(boardDto, board);

        return saveId;
    }

    @Transactional
    public void delete(Long board_id) {
        boardRepository.delete(board_id);
    }

    public Board findById(Long id) {
        return boardRepository.findById(id);
    }

    public List<BoardReturnForm> findByCategoryId(String category_id) {
        List<Board> categoryList = boardRepository.findByCategoryId(category_id);

        Stream<Board> boardStream = categoryList.stream();
        List<BoardReturnForm> boardReturnForms = boardStream.map(board -> new BoardReturnForm(board)).collect(Collectors.toList());

        return boardReturnForms;
    }

    public List<BoardReturnForm> findByMasterCategoryId(String category_id){
        List<Board> categoryList = boardRepository.findByTotalCategory(category_id);

        System.out.println(categoryList);

        Stream<Board> boardStream = categoryList.stream();
        List<BoardReturnForm> boardReturnForms = boardStream.map(board -> new BoardReturnForm(board)).collect(Collectors.toList());

        return boardReturnForms;
    }

    public List<BoardReturnForm> findAll() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardReturnForm> boardReturnForms = boardList.stream().map(board -> new BoardReturnForm(board)).collect(Collectors.toList());

        return boardReturnForms;
    }

    public List<BoardReturnForm> findByUserId(Long userId) {
        List<Board> byUserId = boardRepository.findByUserId(userId);
        List<BoardReturnForm> boardReturnForms = byUserId.stream().map(board -> new BoardReturnForm(board)).collect(Collectors.toList());

        return boardReturnForms;
    }

    public boolean loginAccountMatchBoarAccount(Account account, Account boardAccount) {
        if(account.getEmail().equals(boardAccount.getEmail())) return true;
        return false;
    }

    @Transactional
    public void changeStatus(Long board_id, String status) {
        log.info("init change status");
        Board board = findById(board_id);


        String changeValue = findStatusValue(status).getValue();
        log.info("chaneValue = {}", changeValue);
        board.setStatus(changeValue);
    }

    public BoardStatus findStatusValue(String status) {
        BoardStatus result = null;

        for(BoardStatus s : BoardStatus.values()) {
            if(status.equals(s.getValue())) {
                result = s;
            }
        }
        return result;
    }

    /*status가 0,1,2가 아니면면*/
    public boolean requestStatusValidate(String requestStatus) {
        boolean result = false;

        for(BoardStatus s : BoardStatus.values()) {
            if(requestStatus.equals(s.getValue())) {
                result = true;
            }
        }

        return result;
    }
}