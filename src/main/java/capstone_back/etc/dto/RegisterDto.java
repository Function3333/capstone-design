package capstone_back.etc.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterDto {
    private String username;
    private String email;
    private String password;
}