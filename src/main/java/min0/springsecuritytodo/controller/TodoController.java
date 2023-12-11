package min0.springsecuritytodo.controller;

import min0.springsecuritytodo.dto.ResponseDTO;
import min0.springsecuritytodo.dto.TodoDTO;
import min0.springsecuritytodo.entity.TodoEntity;
import min0.springsecuritytodo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
// - REST Controller임을 명시하는 어노테이션
// - HTTP Response body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequestMapping("todo")
// - 요청 주소가 어노테이션에 입력된 값과 일치하면 해당 클래스/메소드 실행되는 어노테이숀
// - 클래스 단위에서 사용하면 하위 메소드에 모두 적용 !
public class TodoController {

    @Autowired
    private TodoService service;

    @PostMapping
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
        try{
            // @AuthenticaltionPrincipal
            // - 해당 어노테이션이 붙어 있으므로 스프링이 로그인한 userId를 찾을 수 있다. !

//            String temporaryUserId = "temp-user"; // 임시 유저


            // (1) dto를 TodoEntity로 변환

            TodoEntity entity = TodoDTO.toEntity(dto);


            // (2) 투두가 생성될 때 id 는 null 로 초기화ㅏ
            entity.setUserId(null);

            // (3) 유저 설정
//            entity.setUserId(temporaryUserId); // 아직은 user api 개발하지 않았으므로 임시 유저를 하드 코딩

            entity.setUserId(userId); // 로그인한 유저 아이디로 설정 ("지금 만드는 투두는 이 userId가 생성한 투두이다 " )

            // (4) 서비스 계층을 이용해 투두 엔티티 생성
            List<TodoEntity> entities = service.create(entity);

            // (5) 리턴된 엔티티 배열을 TodoDTO 배열로 변환
            List<TodoDTO> dtos = new ArrayList<>();
            for(TodoEntity tEntity:entities){
                TodoDTO tDto = new TodoDTO(tEntity); //dto로 바꾼다음
                dtos.add(tDto); //dto 배열에 추가

            }

            // (6) 변환된 dto 배열을 이용해서 ResponseDTO 초기화
            ResponseDTO<TodoDTO> res = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // (7) ResponseDTO 리턴
//            ResponseEntity
//            import org.springframework.http.ResponseEntity;
//            java 객체를 return 해버리면 http 응답 제어가 불가능
//            ResponseEntity 를 사용해서 상태코드, 응답 본문 등을 설정해서 클라이언트한테 "응답" 할 수 있어야 함
//            .ok() : 성공 (200 code)
//            .headers(): 헤더 설정
//            .body(): 응답 본문 설정
            return ResponseEntity.ok().body(res);
            // 자바 객체를 리턴해버리면 http 응답 제어가 불가능

        }catch(Exception e){
            // (8) 예외가 있는 경우, dto 대신 에러 메시지 넣어서 리턴
            String error = e.getMessage();
            ResponseDTO<TodoDTO> res = ResponseDTO.<TodoDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(res);

        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){
//        String temporaryUserId = "temp-user"; // 임시 유저


        // (1) 서비스 계층의 retrieve() 사용해서 투두 리스트 가져오기
//        List<TodoEntity> entities = service.retrieve(temporaryUserId);

        List<TodoEntity> entities = service.retrieve(userId);

        //(2) 리턴된 엔티티 리스트를 TodoDTO 배열로 변환
        List<TodoDTO> dtos = new ArrayList<>();
        for(TodoEntity tEntity:entities){
            TodoDTO tDto = new TodoDTO(tEntity); //dto로 바꾼다음
            dtos.add(tDto); //dto 배열에 추가
        }

        // (3) 변환된 TodoDTO 리스트를 이용해서 ResponseDTO를 초기화
        ResponseDTO<TodoDTO> res = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // (4) ResponseDTO를 리턴
        return ResponseEntity.ok().body(res);
    }
}
