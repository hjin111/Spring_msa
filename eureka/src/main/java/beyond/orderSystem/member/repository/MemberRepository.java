package beyond.orderSystem.member.repository;

import beyond.orderSystem.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 페이징 처리
    Page<Member> findAll(Pageable pageable);

    List<Member> findAll();

    Optional<Member> findByEmail(String email);

}
