package beyond.orderSystem.product.controller;

import beyond.orderSystem.common.dto.CommonResDto;
import beyond.orderSystem.product.domain.Product;
import beyond.orderSystem.product.dto.ProductResDto;
import beyond.orderSystem.product.dto.ProductSaveReqDto;
import beyond.orderSystem.product.dto.ProductSearchDto;
import beyond.orderSystem.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/product/create")
    public ResponseEntity<?> productCreate(@ModelAttribute ProductSaveReqDto dto){
        Product product = productService.productAwsCreate(dto);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "상품 등록 완료", product.getId());
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
    }

    @GetMapping("/product/list")
    public ResponseEntity<?> productList( ProductSearchDto searchDto , Pageable pageable ){
        Page<ProductResDto> dtos = productService.productList( searchDto, pageable);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "OK", dtos);
        return new ResponseEntity<>(commonResDto, HttpStatus.OK);
    }

}
