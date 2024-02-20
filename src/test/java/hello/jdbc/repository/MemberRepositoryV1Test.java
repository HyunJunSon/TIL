package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 memberRepositoryV1;

    @BeforeEach
    void beforeEach(){
        //기본 DriverMager - 항상 새로운 커넥션 획득
//        DriverManagerDataSource datasource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

//        connection pooling -
        HikariDataSource datasource = new HikariDataSource();
        datasource.setJdbcUrl(URL);
        datasource.setUsername(USERNAME);
        datasource.setPassword(PASSWORD);
        datasource.setPoolName("HyunjunPool");
        log.info("HikariDataSource Configuration: URL={}, Username={}, PoolName={}", datasource.getJdbcUrl(), datasource.getUsername(), datasource.getPoolName());

        memberRepositoryV1 = new MemberRepositoryV1(datasource);


    }

    @Test
    void crud() throws SQLException, InterruptedException {
        Member memberV0 = new Member("memberV999", 10000);
        Member member = memberRepositoryV1.save(memberV0);

        //findById
        Member findMember = memberRepositoryV1.findById(member.getMemberId());
//        log.info("findMember={}", findMember);
        assertThat(member).isEqualTo(findMember);

        memberRepositoryV1.update(member.getMemberId(), 200000);
        Member updatedMember = memberRepositoryV1.findById(memberV0.getMemberId());

        assertThat(updatedMember.getMoney()).isEqualTo(200000);

        //delete
        memberRepositoryV1.delete(member.getMemberId());

//        memberRepositoryV1.findById(memberV0.getMemberId());
        assertThatThrownBy(
                ()-> memberRepositoryV1.findById(memberV0.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);

        Thread.sleep(1000);
    }
}