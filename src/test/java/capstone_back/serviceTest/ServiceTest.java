package capstone_back.serviceTest;

import capstone_back.account.Account;
import capstone_back.account.AccountService;
import capstone_back.etc.timeStamp.CurrentTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ServiceTest {
    @Autowired
    AccountService accountService;

    @Test
    @Transactional
    void test() {
        Account account = createAccount("test@naver.com", "password");
        accountService.join(account);

        /*boolean flag = accountService.duplicateEmail("test123@naver.com");
        Assertions.assertThat(flag).isFalse();*/
    }
    public Account createAccount(String email, String password) {
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        return account;
    }

    @Test
    void timeTest() {
        CurrentTime currentTime = new CurrentTime();
        System.out.println(currentTime.currentTimeToTimeStamp());
    }
}
