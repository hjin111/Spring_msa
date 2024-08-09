package beyond.orderSystem.ordering.domain;

import beyond.orderSystem.ordering.dto.OrderListResDto;
import beyond.orderSystem.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OrderDetail {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private Integer quantity;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name = "ordering_id")
    private Ordering ordering;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderListResDto.OrderDetailDto fromEntity(){
        OrderListResDto.OrderDetailDto orderDetailDto = OrderListResDto.OrderDetailDto
                .builder()
                .id(this.id)
                .productName(this.product.getName())
                .count(this.quantity)
                .build();

        return orderDetailDto;
    }

}
