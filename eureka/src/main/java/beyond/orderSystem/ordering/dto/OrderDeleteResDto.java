package beyond.orderSystem.ordering.dto;

import beyond.orderSystem.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDeleteResDto {

    private Long id;
    private Member member;

}
