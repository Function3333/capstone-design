package capstone_back.account;

import capstone_back.etc.dto.LoginDto;
import capstone_back.etc.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public Long join(RegisterDto registerDto) {
        Account account = new Account().dtoToAccount(registerDto);

        return accountRepository.save(account);
    }

    /*
    * 찾는 이메일이 없으면 IndexOutOfBoundsException
    * */
    public Account findByEmail(String email) {
        List<Account> byEmail = accountRepository.findByEmail(email);
        Account account = byEmail.get(0);
        return account;
    }

    /*
    * 찾는 이름이 없으면 IndexOutOfBoundsException
    * */
    public Account findByUsername(String username) {
        List<Account> byUsername = accountRepository.findByUsername(username);
        Account account = byUsername.get(0);
        return account;
    }

    public boolean findEmailIsDuplicate(String email) {
        try {
            findByEmail(email);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    public boolean findUsernameIsDuplicate(String username) {
        try {
            findByUsername(username);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }


}
