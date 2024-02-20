package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import hello.jdbc.repository.MemberRepositoryV3;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 트랙젝션 - 트랙젝션 매니져
 */
class MemberServiceV3_1Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_Ex = "ex";

    MemberRepositoryV3 memberRepositoryV3;
    MemberServiceV3_1 memberServiceV3_1;

    @BeforeEach
    void before() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepositoryV3 = new MemberRepositoryV3(dataSource);

        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        memberServiceV3_1 = new MemberServiceV3_1(transactionManager, memberRepositoryV3);
    }

    @AfterEach
    void afterEach() throws SQLException {
        memberRepositoryV3.delete(MEMBER_A);
        memberRepositoryV3.delete(MEMBER_B);
        memberRepositoryV3.delete(MEMBER_Ex);
    }

    @Test
    @DisplayName("정상 이체")
    void accoutTransfer() throws SQLException {
        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);
        memberRepositoryV3.save(memberA);
        memberRepositoryV3.save(memberB);

        //when
        memberServiceV3_1.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);

        //then
        Member findMemberA = memberRepositoryV3.findById(memberA.getMemberId());
        Member findMemberB = memberRepositoryV3.findById(memberB.getMemberId());

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8000);
        Assertions.assertThat(findMemberB.getMoney()).isEqualTo(12000);
    }

    @Test
    @DisplayName("이체 중 예외가 발생")
    void accountTransferEx() throws SQLException {

        Member memberA = new Member("memberC", 10000);
        Member memberEX = new Member(MEMBER_Ex, 10000);
        memberRepositoryV3.save(memberA);
        memberRepositoryV3.save(memberEX);

//        memberServiceV3_1.accountTransfer(memberA.getMemberId(), memberEX.getMemberId(), 2000);
//        when
        assertThatThrownBy(() -> memberServiceV3_1.accountTransfer(memberA.getMemberId(), memberEX.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class);
        //then
        Member findMemberA = memberRepositoryV3.findById(memberA.getMemberId());
        Member findMemberB = memberRepositoryV3.findById(memberEX.getMemberId());

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(10000);
        Assertions.assertThat(findMemberB.getMoney()).isEqualTo(10000);
    }


}