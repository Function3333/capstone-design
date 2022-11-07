package capstone_back.mention;

import capstone_back.mention.Mention;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MentionRepository {
    @PersistenceContext
    EntityManager em;

    /*일단 기본적인 기능만 만들었어요*/

    /*저장*/
    public void save(Mention mention) {
        em.persist(mention);
    }
    /*Id로 찾아오기*/
    public Mention findById(Long id) {
        return em.find(Mention.class, id);
    }
    /*Mention 테이블 모든 데이터 가져오기*/
    public List<Mention> findAll() {
        return em.createQuery("select m from Mention m", Mention.class)
                .getResultList();
    }
    /*삭제*/
    public void deleteMention(Long id) {
        Mention Mention = findById(id);
        em.remove(Mention);
    }
}
