package practice.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import practice.domain.Member;
import practice.repository.InMemoryMemberRepository;
import practice.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/*
강의에서는 Junit 4 를 이용했음.
Junit 4 는 @TestInstance 어노테이션이 없고 기본적으로 테스트에 사용되는 인스턴스는 모두 테스트 클래스당 생명주기를 가짐.
참고 : https://stackoverflow.com/questions/15573983/junit-4-how-to-disable-creating-new-instance-of-test-per-test-method

반면 Junit 5 는 인스턴스의 생명 주기가 기본적으로 메서드 당임. 그래서 나는 에러가 안났었던 거.
(+ InMemoryMemberRepository 의 Map 을 static 하지 않게 만든것도 한몫 함)
@TestInstance 의 Lifecycle enum 상수로 인스턴스의 생명 주기를 설정할 수 있음.
 */
class MemberServiceTest {

    MemberRepository repo          = new InMemoryMemberRepository();
    MemberService    memberService = new MemberService(repo);

    @AfterEach
    void clean() {
        repo.clear();
    }

    @Test
    @DisplayName("회원가입")
    @Order(1)
    void join() {
        // given
        Member member = new Member();
        member.setName("Testing");

        // when
        Long id = memberService.join(member);

        // then
        Assertions
                .assertThat(id)
                .isNotNull();
        Assertions
                .assertThat(memberService
                                    .findMemberById(id)
                                    .orElseThrow())
                .isEqualTo(member);
    }

    @Test
    @DisplayName("중복 회원 예외 테스트")
    @Order(2)
    void checkDuplicate() {
        // given
        Member member1 = new Member();
        member1.setName("Testing");

        Member member2 = new Member();
        member2.setName("Testing");

        // when
        memberService.join(member1);

        // then
        try {
            memberService.join(member2);
            Assertions.fail("테스트가 실패했습니다.");
        }
        catch (IllegalStateException e) {
            Assertions
                    .assertThat(e.getMessage())
                    .isEqualTo("이미 존재하는 회원입니다.");
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member1));

        Assertions
                .assertThat(e.getMessage())
                .isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    @DisplayName("전체 회원 조회")
    void findMembers() {
    }

    @Test
    @DisplayName("id 로 회원 조회")
    void findMemberById() {
    }
}