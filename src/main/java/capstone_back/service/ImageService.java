package capstone_back.service;

import capstone_back.domain.Board;
import capstone_back.domain.Image;
import capstone_back.repository.ImageRepository;
import capstone_back.utils.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public void saveImage(BoardDto boardDto, Board board) throws SQLException, IOException {
        List<MultipartFile> multipartFiles = boardDto.getImageList();

        if(!multipartFiles.isEmpty()) {
            for(MultipartFile multipartFile : multipartFiles) {
                Blob blob = multipartFileToBlob(multipartFile);

                Image image = new Image();
                image.setImage(blob);
                image.setBoard(board);

                imageRepository.save(image);
            }
        }
    }

    public Blob multipartFileToBlob(MultipartFile multipartFile) throws IOException, SQLException {
        byte[] bytes = multipartFile.getBytes();
        Blob blob = new SerialBlob(bytes);

        return blob;
    }
}
