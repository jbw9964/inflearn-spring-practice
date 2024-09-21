package practice.service;

import org.springframework.stereotype.Service;
import practice.domain.Member;
import practice.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository repo;

    public MemberService(MemberRepository repo) {
        this.repo = repo;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        checkDuplicate(member);

        return repo
                .save(member)
                .getId();
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return repo.findAll();
    }

    /**
     * id 로 회원 조회
     */
    public Optional<Member> findMemberById(Long id) {
        return repo.findById(id);
    }

    private void checkDuplicate(Member member) throws IllegalStateException {
        Optional<Member> result = repo.findByUsername(member.getName());

        result.ifPresent(m -> {throw new IllegalStateException("이미 존재하는 회원입니다.");});
    }
}
