package practice.repository;

import practice.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByUsername(String username);

    List<Member> findAll();

    void clear();
}
