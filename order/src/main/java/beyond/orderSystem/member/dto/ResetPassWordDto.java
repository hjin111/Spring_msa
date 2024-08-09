package beyond.orderSystem.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassWordDto {

    private String email;
    private String asIsPassword;
    private String toBePassword;

}
