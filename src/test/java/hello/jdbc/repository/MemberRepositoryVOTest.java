package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@Slf4j
class MemberRepositoryVOTest {

    MemberRepositoryVO memberRepositoryVO = new MemberRepositoryVO();

    @Test
    void crud() throws SQLException {
        Member memberV0 = new Member("memberV99", 10000);
        Member member = memberRepositoryVO.save(memberV0);

        //findById
        Member findMember = memberRepositoryVO.findById(member.getMemberId());
//        log.info("findMember={}", findMember);
        assertThat(member).isEqualTo(findMember);

        memberRepositoryVO.update(member.getMemberId(), 200000);
        Member updatedMember = memberRepositoryVO.findById(memberV0.getMemberId());

        assertThat(updatedMember.getMoney()).isEqualTo(200000);

        //delete
        memberRepositoryVO.delete(member.getMemberId());

//        memberRepositoryVO.findById(memberV0.getMemberId());
        assertThatThrownBy(
                ()-> memberRepositoryVO.findById(memberV0.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);


    }
}