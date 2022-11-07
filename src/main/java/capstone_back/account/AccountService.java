package capstone_back.account;

import capstone_back.etc.dto.LoginDto;
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
    public Long join(Account account) {
        Long id = accountRepository.save(account);
        return id;
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


    public String login(LoginDto loginDto) {
        List<Account> list = accountRepository.findByEmail(loginDto.getEmail());
        if(list.isEmpty()) return null;

        Account account = list.get(0);
        if(!account.getPassword().equals(loginDto.getPassword())) return null;

        return account.getEmail();
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
