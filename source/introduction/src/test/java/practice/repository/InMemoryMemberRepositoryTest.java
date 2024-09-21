package practice.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import practice.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;


class InMemoryMemberRepositoryTest {

    MemberRepository repo = new InMemoryMemberRepository();

    @AfterEach
    void clear() {
        repo.clear();
    }


    @Test
    void save() {
        // Test 에 사용되는 Assertion 메서드는 jupiter 에서 제공하는 것들도 있고
        // assertj 에서 제공하는 것들도 있다.
        Member member = new Member();
        member.setName("Testing!");
        repo.save(member);

        assertThat(member).isEqualTo(repo
                                             .findById(member.getId())
                                             .orElseThrow());
    }

    @Test
    void findById() {
        Member member1 = new Member();
        member1.setName("Testing!");
        repo.save(member1);

        Member member2 = new Member();
        member2.setName("Testing!!");
        repo.save(member2);

        assertThat(member1).isEqualTo(repo
                                              .findById(member1.getId())
                                              .orElseThrow());
        assertThat(member2).isEqualTo(repo
                                              .findById(member2.getId())
                                              .orElseThrow());

        assertThat(member1).isNotEqualTo(member2);
    }

    @Test
    void findByUsername() {

        Member member1 = new Member();
        member1.setName("Testing!");
        repo.save(member1);

        Member member2 = new Member();
        member2.setName("Testing!!");
        repo.save(member2);

        assertThat(member1).isEqualTo(repo
                                              .findByUsername(member1.getName())
                                              .orElseThrow());
        assertThat(member2).isEqualTo(repo
                                              .findByUsername(member2.getName())
                                              .orElseThrow());
    }

    @Test
    void findAll() {

        Member member1 = new Member();
        member1.setName("Testing!");
        repo.save(member1);

        Member member2 = new Member();
        member2.setName("Testing!!");
        repo.save(member2);

        assertThat(repo.findAll())
                .hasSize(2)
                .contains(member1, member2);
    }
}