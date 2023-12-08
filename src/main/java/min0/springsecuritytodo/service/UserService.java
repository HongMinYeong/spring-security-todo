package min0.springsecuritytodo.service;

import lombok.extern.slf4j.Slf4j;
import min0.springsecuritytodo.entity.UserEntity;
import min0.springsecuritytodo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public UserEntity create(final UserEntity userEntity){
        if(userEntity == null || userEntity.getEmail() == null){
            throw new RuntimeException("Invalid arguments");
        }
        final String email = userEntity.getEmail();

        if(repository.existsByEmail(email)){
            log.warn("Email already exists {}",email);
            throw new RuntimeException("Email already exists");
        }
        return repository.save(userEntity);
    }

    // 이메일과 패스워드로 검증
    public UserEntity getByCredentials(final String email, String password){
        return repository.findByEmailAndPassword(email,password);

    }
}
