package capstone_back.message;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MessageRepository {
    @PersistenceContext
    EntityManager em;

    public Long save(Message message) {
        em.persist(message);
        return message.getId();
    }

    public Message findById(Long id) {
        return em.find(Message.class, id);
    }

    public List<Message> findAll() {
        return em.createQuery("select m from Message m ").getResultList();
    }

    public List<Message> findBySenderId(Long senderId) {
        return em.createQuery(" select m from Message m where m.sender.id =: id")
                .setParameter("id", senderId)
                .getResultList();
    }

    public List<Message> findByReceiverId(Long receiverId) {
        return em.createQuery("select m from Message m where m.receiver.id =: id")
                .setParameter("id", receiverId)
                .getResultList();
    }
}
