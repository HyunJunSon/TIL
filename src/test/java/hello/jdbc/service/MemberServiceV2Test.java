package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 트랙젝션 - 커넥션 파라미터 전달 동기화
 */
class MemberServiceV2Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_Ex = "ex";

    MemberRepositoryV2 memberRepositoryV2;
    MemberServiceV2 memberServiceV2;

    @BeforeEach
    void before() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepositoryV2 = new MemberRepositoryV2(dataSource);
        memberServiceV2 = new MemberServiceV2(dataSource,memberRepositoryV2);
    }

    @Test
    @DisplayName("정상 이체")
    void accoutTransfer() throws SQLException {
        //given
        Member memberA = new Member("MEMBER_K", 10000);
        Member memberB = new Member("MEMBER_T", 10000);
        memberRepositoryV2.save(memberA);
        memberRepositoryV2.save(memberB);

        //when
        memberServiceV2.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);

        //then
        Member findMemberA = memberRepositoryV2.findById(memberA.getMemberId());
        Member findMemberB = memberRepositoryV2.findById(memberB.getMemberId());

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8000);
        Assertions.assertThat(findMemberB.getMoney()).isEqualTo(12000);
    }

    @Test
    @DisplayName("이체 중 예외가 발생")
    void accountTransferEx() throws SQLException {

        Member memberA = new Member("MEMBER_8", 10000);
        Member memberB = new Member(MEMBER_Ex, 10000);
        memberRepositoryV2.save(memberA);
        memberRepositoryV2.save(memberB);
        memberServiceV2.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);
        //when
        assertThatThrownBy(() -> memberServiceV2.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class);
        //then
        Member findMemberA = memberRepositoryV2.findById(memberA.getMemberId());
        Member findMemberB = memberRepositoryV2.findById(memberB.getMemberId());

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(10000);
        Assertions.assertThat(findMemberB.getMoney()).isEqualTo(10000);
    }

    @AfterEach
    void afterEach() throws SQLException {
        memberRepositoryV2.delete(MEMBER_A);
        memberRepositoryV2.delete(MEMBER_B);
        String MEMBER_EX = null;
        memberRepositoryV2.delete(MEMBER_Ex);
    }


}