package beyond.orderSystem.ordering.dto;

import beyond.orderSystem.ordering.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderListResDto {

    private Long id;
    private String memberEmail;
    private OrderStatus orderStatus;

    private List<OrderDetailDto> orderDetailDtos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderDetailDto{

        private Long id;
        private String productName;
        private Integer count;

    }

}
