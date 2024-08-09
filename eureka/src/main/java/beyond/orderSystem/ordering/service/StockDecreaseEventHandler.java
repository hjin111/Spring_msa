package beyond.orderSystem.ordering.service;

import beyond.orderSystem.common.configs.RabbitMqConfig;
import beyond.orderSystem.ordering.dto.StockDecreaseEvent;

import beyond.orderSystem.product.domain.Product;
import beyond.orderSystem.product.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Component
public class StockDecreaseEventHandler {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ProductRepository productRepository;

    public void publish(StockDecreaseEvent event){
        rabbitTemplate.convertAndSend(RabbitMqConfig.STOCK_DECREASE_QUEUE, event);// 매개변수로 큐 이름, 내용
    }

    // 트랜잭션이 완료된 이후에 그다음 메세지 수신하므로, 동시성이슈발생 X
    @Transactional
    @RabbitListener(queues = RabbitMqConfig.STOCK_DECREASE_QUEUE ) // 어떤 큐를 바라보고 있을지
    public void listen(Message message)  { // 큐를 바라보고 있다가 메세지를 여기서 처리해주고 rdb 업데이트를 여기서 해줌

        String messageBody = new String(message.getBody());
        // System.out.println(messageBody); // Json 으로 찍혀 나옴

        // json 메세지를 ObjectMapper로 직접 parsing 하기
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            StockDecreaseEvent stockDecreaseEvent = objectMapper.readValue(messageBody, StockDecreaseEvent.class);
            // System.out.println(stockDecreaseEvent);
            // 재고 update
            Product product = productRepository.findById(stockDecreaseEvent.getProductId()).orElseThrow(() -> new EntityNotFoundException("해당 상품이 없음"));
            product.updateQuantity(stockDecreaseEvent.getProductCount());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
