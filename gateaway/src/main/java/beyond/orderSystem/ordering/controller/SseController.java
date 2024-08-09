package beyond.orderSystem.ordering.controller;
import beyond.orderSystem.ordering.dto.OrderListResDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class SseController implements MessageListener {
    // SseEmiter는 연결된 사용자 정보를 의미
    // ConcurrentHashMap는 Thread-safe한 map(동시성 이슈 발생 안 함)
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    // 여러번 구독을 방지 하기 위한 ConcurrentHashSet 변수 생성.
    private Set<String> subscribeList = ConcurrentHashMap.newKeySet();

    @Qualifier("4")
    private final RedisTemplate<String,Object> sseRedisTemplate;

    private final RedisMessageListenerContainer redisMessageListenerContainer;

    public SseController(@Qualifier("4")RedisTemplate<String, Object> sseRedisTemplate, RedisMessageListenerContainer redisMessageListenerContainer) {
        this.sseRedisTemplate = sseRedisTemplate;
        this.redisMessageListenerContainer = redisMessageListenerContainer;
    }

    // email에 해당되는 메시지를 listen 하는 listener를 추가한 것.
    public void subscribeChannel(String email){
        // 이미 구독한 email일 경우에는 더이상 구독하지 않는 분기처리
        if(!subscribeList.contains(email)){
            MessageListenerAdapter listenerAdapter = createListenerAdapter(this);
            redisMessageListenerContainer.addMessageListener(listenerAdapter, new PatternTopic(email));
            subscribeList.add(email); // admin@test.com 란 이메일을 구독 목록에 넣을테니 만약 admin@test.com 이란 메시지로 또 구독 요청이 오면 무시하겠따

        }

    }

    private MessageListenerAdapter createListenerAdapter(SseController sseController){
        return new MessageListenerAdapter(sseController, "onMessage");
    }

    @GetMapping("/subscribe")
    public SseEmitter subscribe(){
        SseEmitter emitter = new SseEmitter(14400*60*1000L); // 30분 정도로 emitter 유효시간 설정
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        emitters.put(email,emitter);
        emitter.onCompletion(()->emitters.remove(email));
        emitter.onTimeout(()->emitters.remove(email));
        try{
            // eventName : 실질적인 메세지임!!!
            emitter.send(SseEmitter.event().name("connect").data("connected!!"));
        }catch (IOException e){
            e.printStackTrace();
        }
        subscribeChannel(email); // redis 에서 subscribe 하겠다
        return emitter;
    }

    public void publishMessage(OrderListResDto dto, String email){
        SseEmitter emitter = emitters.get(email);
        // emitter 있으면 내가 처리
        // redis pub/sub 실습 테스트를 위해 잠시 주석처리
//        if(emitter !=null){
//            try {
//                emitter.send(SseEmitter.event().name("ordered").data(dto));
//            }catch(IOException e){
//                throw new RuntimeException(e);
//            }
//        }
//        //emitter 없으면 레디스에 배포(노션 sse 알림 노트 보면 좀 알거야)
//        else{
//            // redisconfig에 4번 qualifier야!
//            // convertAndSend : 직렬화해서 보내겠다는 것
        sseRedisTemplate.convertAndSend(email,dto);
//        }

    }


//    사용자가 주문을 한다.
//    = 사용자와 연결되어 있는 서버가 레디스로 주문 정보를 보낸다.
//    = 레디스의 DB 중에서 admin@test.com 가지고 있는 구독되어 있는 서버에 메시지를 보낸다
    @Override
    public void onMessage(Message message, byte[] pattern) {
//        아래는 message 내용 parsing 해주는 것
        ObjectMapper objectMapper  = new ObjectMapper();
        try {
            OrderListResDto dto =objectMapper.readValue(message.getBody(), OrderListResDto.class);

            String email = new String(pattern, StandardCharsets.UTF_8);
            SseEmitter emitter = emitters.get(email);
            if(emitter != null){
                emitter.send(SseEmitter.event().name("ordered").data(dto));
            }
            System.out.println("listening");
            System.out.println(dto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

