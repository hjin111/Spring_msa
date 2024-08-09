package beyond.orderSystem.ordering.repository;

import beyond.orderSystem.member.domain.Member;
import beyond.orderSystem.ordering.domain.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderingRepository extends JpaRepository<Ordering, Long> {

    List<Ordering> findByMember(Member member);
}
