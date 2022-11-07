package capstone_back.comment;

import capstone_back.comment.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CommentRepository {
    @PersistenceContext
    EntityManager em;

    /*일단 기본적인 기능만 만들었어요*/

    /*저장*/
    public void save(Comment comment) {
        em.persist(comment);
    }
    /*Id로 찾아오기*/
    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }
    /*Comment 테이블 모든 데이터 가져오기*/
    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }
    /*삭제*/
    public void deleteComment(Long id) {
        Comment Comment = findById(id);
        em.remove(Comment);
    }
}
