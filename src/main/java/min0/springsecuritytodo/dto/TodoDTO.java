package min0.springsecuritytodo.dto;

import lombok.*;
import min0.springsecuritytodo.entity.TodoEntity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data //getter + setter
public class TodoDTO {
    private Long id;
    private String title;
    private  boolean done;

    public TodoDTO(final TodoEntity entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
        //userId는 숨김처리
    }
    // DTO를 entity로 반환하는 메소드
    public static TodoEntity toEntity(final TodoDTO dto){
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone()) //boolean타입은 is~ // get~(x)
                .build();
    }
}
