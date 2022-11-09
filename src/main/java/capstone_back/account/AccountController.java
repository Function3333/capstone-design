package capstone_back.account;

import capstone_back.etc.dto.LoginDto;
import capstone_back.etc.dto.RegisterDto;
import capstone_back.etc.jwt.JwtService;
import capstone_back.login.LoginService;
import capstone_back.message.MessageController;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AccountController {
    private final AccountService accountService;
    private final LoginService loginService;
    private final JwtService jwtService;

    /*회원가입*/
    @PostMapping("/user/register")
    public ResponseData register(@RequestBody RegisterDto registerDto) throws JsonProcessingException {
        boolean isEmailDuplicate = accountService.findEmailIsDuplicate(registerDto.getEmail());
        boolean isUsernameDuplicate = accountService.findUsernameIsDuplicate(registerDto.getUsername());

        if(isEmailDuplicate) {
            return new ResponseData("fail", "duplicate email");
        }

        if(isUsernameDuplicate) {
            return new ResponseData("fail", "duplicate username");
        }

        Account account = new Account().dtoToAccount(registerDto);
        Long id = accountService.join(account);

        return new ResponseData("success", id);
    }

    @PostMapping("/user/login")
    public ResponseData login(@RequestBody LoginDto loginDto) {
        if(loginService.login(loginDto)) {
            return new ResponseData("success", jwtService.createAccessToken(loginDto.getEmail()));
        }

        return new ResponseData("fail", "invalid email or password");
    }

    @Data
    public class ResponseData {
        String result;
        Object data;

        public ResponseData(String result, Object data) {
            this.result = result;
            this.data = data;
        }
    }


}
