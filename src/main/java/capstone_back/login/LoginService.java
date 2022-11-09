package capstone_back.login;

import capstone_back.account.Account;
import capstone_back.account.AccountService;
import capstone_back.etc.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final AccountService accountService;

    public boolean login(LoginDto loginDto) {
        try {
            /*여기서 이메일로 회원 찾아오지 못하면 밑의 예외 발생, 존재하면 마지막으로 비밀번호 검사*/
            Account accountFindByRep = accountService.findByEmail(loginDto.getEmail());

            if(loginDto.getPassword().equals(accountFindByRep.getPassword())) {
                return true;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        /*아이디는 맞으나 비밀번호가 틀림*/
        return false;
    }
}
