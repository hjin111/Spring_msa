package beyond.orderSystem.common.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class StockInventoryService {

    @Qualifier("3")
    private final RedisTemplate<String, Object> redisTemplate;

    public StockInventoryService(@Qualifier("3")RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 상품등록시 increaseStock 호출
    public Long increaseStock( Long itemId, int quantity ){

        // redis가 음수까지 내려 갈 경우 추후 재고 update 상황에서 increase 값이 정확하지 않을 수 있으므로,
        // 음수이면 0으로 setting 로직이 필요하다.

//        Object remains = redisTemplate.opsForValue().get(String.valueOf(itemId));
//        int longRemains = Integer.parseInt(remains.toString());
//        if ( longRemains < 0 ){
//            int longRemains2 = Math.abs(longRemains);
//            redisTemplate.opsForValue().decrement(String.valueOf(itemId), quantity);
//        }
        // 아래 메서드의 리턴 값은 잔량값을 리턴
        return redisTemplate.opsForValue().increment(String.valueOf(itemId), quantity);
    }

    // 주문등록시 decreaseStock 호출
    public Long decreaseStock( Long itemId, int quantity ){

        Object remains = redisTemplate.opsForValue().get(String.valueOf(itemId));

        // itemId 남아 있는 값
        int longRemains = Integer.parseInt(remains.toString());

        if( longRemains < quantity ){
            return -1L;
        } else {
            // 남아 있는 잔량을 리턴
            return redisTemplate.opsForValue().decrement(String.valueOf(itemId), quantity);
        }
    }

}
