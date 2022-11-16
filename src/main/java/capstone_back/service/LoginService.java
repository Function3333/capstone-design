package capstone_back.service;

import capstone_back.domain.Account;
import capstone_back.utils.dto.LoginDto;
import capstone_back.utils.hash.Hash;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final AccountService accountService;

    public boolean login(LoginDto loginDto) {
        try {
            Account account = accountService.findByEmail(loginDto.getEmail());

            String password = loginDto.getPassword();
            String encryptPassword = Hash.encrypt(password);

            if(account.getPassword().equals(encryptPassword)) {
                return true;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        } catch (NoSuchAlgorithmException e){
            return false;
        }
        /*아이디는 맞으나 비밀번호가 틀림*/
        return false;
    }
}
