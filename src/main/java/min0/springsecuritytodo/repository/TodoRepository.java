package min0.springsecuritytodo.repository;

import min0.springsecuritytodo.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// TodoRepository 인터페이스
// - jpaRepository 인터페이스를 확장한 인터페이스
// - TodoEntity : 테이블에 매핑될 엔티티 클래스
// - Long : 엔티티의 PK 타입
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity,Long> { //두번째에 pk 타입
    List<TodoEntity> findByUserId(String userId);
}
