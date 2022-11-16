package capstone_back.repository;

import capstone_back.domain.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AccountRepository {
    @PersistenceContext
    EntityManager em;


    /*저장*/
    public Long save(Account account) {
        em.persist(account);
        return account.getId();
    }

    /*Id로 찾아오기*/
    public Account findById(Long id) {
        return em.find(Account.class, id);
    }

    /*email로 찾아오기*/
    public List<Account> findByEmail(String email) {
        return em.createQuery("select a from Account a where a.email =: email", Account.class)
                .setParameter("email", email).getResultList();
    }

    public List<Account> findByUsername(String username) {
        return em.createQuery("select a from Account a where a.username =: username", Account.class)
                .setParameter("username", username).getResultList();
    }

    /*account 테이블 모든 데이터 가져오기*/
    public List<Account> findAll() {
        return em.createQuery("select a from Account a", Account.class)
                .getResultList();
    }
    /*삭제*/
    public void deleteAccount(Long id) {
        Account account = findById(id);
        em.remove(account);
    }
}
