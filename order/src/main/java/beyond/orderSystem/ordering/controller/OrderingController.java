package beyond.orderSystem.ordering.controller;

import beyond.orderSystem.common.dto.CommonResDto;
import beyond.orderSystem.ordering.domain.Ordering;
import beyond.orderSystem.ordering.dto.OrderDeleteResDto;
import beyond.orderSystem.ordering.dto.OrderListResDto;
import beyond.orderSystem.ordering.dto.OrderSaveReqDto;
import beyond.orderSystem.ordering.service.OrderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderingController {

    private final OrderingService orderService;

    @Autowired
    public OrderingController(OrderingService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order/create")
    public ResponseEntity<?> orderCreate(@RequestBody List<OrderSaveReqDto> dto){
        Ordering ordering = orderService.orderCreate(dto);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "주문 등록 완료", ordering.getId());
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/order/list")
    public ResponseEntity<?> orderList(){
        List<OrderListResDto> ordering = orderService.orderList();
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "정상 완료", ordering);
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED );
    }

    // 내 주문만 볼 수 있는 myOrders : order/myorders
    // @PreAuthorize("hasRole('USER')")
    @GetMapping("/order/myorders")
    public ResponseEntity<?> myOrders(){
        List<OrderListResDto> ordering = orderService.myOrders();
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "정상 완료", ordering);
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED );
    }

    // admin 사용자가 주문 취소 : /order/{id}/cancel -> orderstatus만 변경
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/order/{id}/cancel")
    public ResponseEntity<?> orderCancel(@PathVariable Long id){
        Ordering ordering = orderService.orderCancel(id);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "주문 취소 완료", ordering.getId());
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED );
    }



}
