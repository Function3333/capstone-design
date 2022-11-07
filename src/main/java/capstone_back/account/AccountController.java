package capstone_back.account;

import capstone_back.etc.dto.LoginDto;
import capstone_back.etc.dto.RegisterDto;
import capstone_back.etc.jwt.JwtService;
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
    private final JwtService jwtService;

    /*회원가입*/
    @PostMapping("/user/register")
    public ResponseData register(@RequestBody RegisterDto registerDto) throws JsonProcessingException {
        boolean isEmailDuplicate = accountService.findEmailIsDuplicate(registerDto.getEmail());
        boolean isUsernameDuplicate = accountService.findUsernameIsDuplicate(registerDto.getUsername());

        if (isEmailDuplicate || isUsernameDuplicate) {
            return new ResponseData("fail", "duplicateEmail or duplicateUsername");
        }

        Account account = new Account().dtoToAccount(registerDto);
        Long id = accountService.join(account);

        return new ResponseData("success", id);
    }

    /*로그인*/
    @PostMapping("/user/login")
    public ResponseData login(@RequestBody LoginDto loginDto) {
        String loginEmail = accountService.login(loginDto);

        if (loginEmail != null) {
            String accessToken = jwtService.createAccessToken(loginEmail);
            return new ResponseData("success", accessToken);
        }

        return new ResponseData("fail", "");
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
