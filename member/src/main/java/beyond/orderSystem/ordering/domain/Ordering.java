package beyond.orderSystem.ordering.domain;

import beyond.orderSystem.member.domain.Member;
import beyond.orderSystem.ordering.dto.OrderDeleteResDto;
import beyond.orderSystem.ordering.dto.OrderListResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Ordering {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.ORDERED;

    @OneToMany( mappedBy = "ordering" , cascade = CascadeType.PERSIST )
    // 빌더 패턴에서도 ArrayList로 초기화 되도록 하는 설정
    @Builder.Default
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public OrderListResDto fromEntity(){

        List<OrderDetail> orderDetailList = this.getOrderDetails();
        List<OrderListResDto.OrderDetailDto> orderDetailDtos = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList ){
            orderDetailDtos.add(orderDetail.fromEntity());
        }
        OrderListResDto orderListResDto = OrderListResDto.builder()
                .id(this.id)
                .memberEmail(this.member.getEmail())
                .orderStatus(this.orderStatus)
                .orderDetailDtos(orderDetailDtos)
                .build();

        return orderListResDto;
    }

    public void updateStatus(OrderStatus orderStatus){
       this.orderStatus = orderStatus;
    }

}
