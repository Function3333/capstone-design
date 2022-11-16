package capstone_back.service;

import capstone_back.repository.AccountRepository;
import capstone_back.domain.Account;
import capstone_back.utils.dto.RegisterDto;
import capstone_back.utils.hash.Hash;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public Long join(RegisterDto registerDto) {
        String password = registerDto.getPassword();
        String encryptPassword = encryptPassword(password);

        registerDto.setPassword(encryptPassword);

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

    public String encryptPassword(String password) {
        String encryptPassword = "";

        try {
            encryptPassword = Hash.encrypt(password);
        } catch (NoSuchAlgorithmException e) {
            log.info("encryptPassword Error");
        }

        return encryptPassword;
    }
}
