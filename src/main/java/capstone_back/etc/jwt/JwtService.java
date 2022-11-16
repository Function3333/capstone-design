package capstone_back.etc.jwt;

import capstone_back.account.Account;
import capstone_back.account.AccountRepository;
import capstone_back.account.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private final AccountRepository accountRepository;
    private final JwtManager jwtManager;
    private final AccountService accountService;

    public boolean validateToken(Long id, String token) {
        Account account = accountRepository.findById(id);
        String Email = jwtManager.getEmail(token);
        List<Account> findByToken = accountRepository.findByEmail(Email);

        String accountEmail = account.getEmail();
        String tokenEmail = findByToken.get(0).getEmail();

        if(!accountEmail.equals(tokenEmail)) {
            return false;
        }
        return true;
    }

    public void expireToken(String email) {
        jwtManager.generateRefreshToken(email);
    }

    public boolean checkHeaderExist(HttpServletRequest request) {
        String token = request.getHeader("AUTHORIZATION");
        if(token == null) return false;
        return true;
    }

    public String getEmail (HttpServletRequest request) {
        String token = request.getHeader("AUTHORIZATION");
        String email = jwtManager.getEmail(token);

        return email;
    }

    public String createAccessToken(String email) {
        String accessToken = jwtManager.generateAccessToken(email);
        return accessToken;
    }

    public Account headerToAccount(HttpServletRequest request) {
        String email = getEmail(request);
        Account account = accountService.findByEmail(email);

        return account;
    }

    public String refreshToken(String email) {
        String refreshToken = jwtManager.generateRefreshToken(email);

        return refreshToken;
    }
}
