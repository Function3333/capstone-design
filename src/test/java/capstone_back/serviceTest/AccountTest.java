package capstone_back.serviceTest;

import capstone_back.account.AccountService;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountTest {
    @Autowired
    AccountService accountService;

    @Test
    @DisplayName("예외 발생 확인")
    void findByEmailTest() {
        try {
            accountService.findByEmail("notExistEmail");
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(IndexOutOfBoundsException.class);
        }
    }
}
