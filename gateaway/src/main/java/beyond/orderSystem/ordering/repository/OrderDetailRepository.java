package beyond.orderSystem.ordering.repository;

import beyond.orderSystem.ordering.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
