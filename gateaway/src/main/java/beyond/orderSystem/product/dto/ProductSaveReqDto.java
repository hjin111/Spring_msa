package beyond.orderSystem.product.dto;

import beyond.orderSystem.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSaveReqDto {

    private String name;
    private String category;
    private Integer price;
    private Integer stockQuantity;
    private MultipartFile productImage;

    public Product toEntity(){
        return Product.builder()
                .name(this.name)
                .category(this.category)
                .price(this.price)
                .stockQuantity(this.stockQuantity)
                .build();
    }

}
