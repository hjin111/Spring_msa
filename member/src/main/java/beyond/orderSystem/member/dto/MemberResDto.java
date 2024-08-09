package beyond.orderSystem.member.dto;

import beyond.orderSystem.common.domain.Address;
import beyond.orderSystem.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResDto {

    private Long id;
    private String name;
    private String email;
    private Address address;
    private int orderCount;

}
