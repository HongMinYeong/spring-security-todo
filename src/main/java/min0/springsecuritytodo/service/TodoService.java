package min0.springsecuritytodo.service;

import lombok.extern.slf4j.Slf4j;
import min0.springsecuritytodo.entity.TodoEntity;
import min0.springsecuritytodo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
// - Simple Logging Facade for Java
// - 로그 라이브러리
// - 용도에 따라서 info, debug, warn, error를 나눠서 로깅
// - 로깅을 하는 클래스에 붙여주면 되는 어노테이션
@Service
public class TodoService {
    // 필드 주입
    @Autowired
    private TodoRepository repository;

    // create todo
    public List<TodoEntity> create(final TodoEntity entity){ //final은 중간에 조작되면 안되니까 붙여주깅
        // 유효성 검사
        validate(entity);

        repository.save(entity); //생성

        log.info("Entity id: {} is saved.",entity.getId()); //몇번 아이디가 생성되었는지 로그로 찍어볼거임 !

        return repository.findByUserId(entity.getUserId());

    }
    // read todo
    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    // entity 유효성 검사 메소드
    // - create, update, delete 할 때 사용되므로 메소드로 처리
    private void validate(final TodoEntity entity){
        if(entity == null){
            log.warn("Entity cannot be null");
            throw new RuntimeException(("Entity cannot be null"));
        }

        if(entity.getUserId() == null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }
}
