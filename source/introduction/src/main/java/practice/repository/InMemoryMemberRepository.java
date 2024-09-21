package practice.repository;

import org.springframework.stereotype.Repository;
import practice.domain.Member;

import java.util.*;

@Repository
public class InMemoryMemberRepository implements MemberRepository {

    private final static Map<Long, Member> storage  = new HashMap<>();
    private static       long              sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        storage.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        return storage
                .values()
                .stream()
                .filter(m -> m
                        .getName()
                        .equals(username))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void clear() {
        storage.clear();
    }
}
