package beyond.orderSystem.product.domain;

import beyond.orderSystem.common.domain.BaseTimeEntity;
import beyond.orderSystem.product.dto.ProductResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private String name;
    private String category;
    private Integer price;
    private Integer stockQuantity;
    private String imagePath;

    public void updateImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    public ProductResDto fromEntity(){
        return ProductResDto.builder()
                .id(this.id)
                .name(this.name)
                .category(this.category)
                .price(this.price)
                .stockQuantity(this.stockQuantity)
                .imagePath(this.imagePath)
                .build();
    }

    public void updateQuantity(int quantity){
        this.stockQuantity -= quantity;
    }

}
