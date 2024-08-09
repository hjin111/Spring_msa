package beyond.orderSystem.ordering.dto;

import beyond.orderSystem.member.domain.Member;
import beyond.orderSystem.ordering.domain.OrderDetail;
import beyond.orderSystem.ordering.domain.OrderStatus;
import beyond.orderSystem.ordering.domain.Ordering;
import beyond.orderSystem.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderSaveReqDto {

    private Long productId;
    private Integer productCount;

//    private Long memberId;
//    private List<OrderDto> orderDtos;

//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class OrderDto{ // 한 객체가
//        private Long productId;
//        private Integer productCount;
//    }

    public Ordering toEntity(Member member){
        return Ordering.builder()
                .member(member)
                .build();
    }

}
