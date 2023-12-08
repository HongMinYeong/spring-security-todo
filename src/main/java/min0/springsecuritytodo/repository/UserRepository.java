package min0.springsecuritytodo.repository;

import min0.springsecuritytodo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);

    Boolean existsByEmail(String email); //이메일 존재하는지

    UserEntity findByEmailAndPassword(String email,String password);
}
