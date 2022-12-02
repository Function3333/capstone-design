package capstone_back.repository;

import capstone_back.domain.Image;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ImageRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Image image) {
        em.persist(image);
    }

    public Image findById(Long id) {
        return em.find(Image.class, id);
    }
}
