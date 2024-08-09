package beyond.orderSystem.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResDto {
    private Long id;
    private String name;
    private String category;
    private Integer price;
    private Integer stockQuantity;
    private String imagePath;
}
