package capstone_back.utils.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class RegisterDto {
    private String username;
    private String email;
    private String password;
}
