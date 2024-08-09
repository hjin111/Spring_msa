package beyond.orderSystem.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

// 타 엔티티에서 사용 가능한 형태로 만드는 어노테이션이다.
@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;

}
