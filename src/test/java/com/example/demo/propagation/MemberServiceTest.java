package com.example.demo.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LogRepository logRepository;

    /**
     * memberService       : Transactional : OFF
     * memberRepository    : Transactioanl : ON
     * logRepository       : Transactional : OFF
     */
    @Test
    void outerTxOff_success(){
        // given
        String username = "outerTxOff_success";

        //when
        memberService.joinV1(username);

        //then : 모든데이터가 정상 저장된다.
//        assertThat(memberRepository.find(username).get().getUsername()).isEqualTo(username);
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService       : Transactional : OFF
     * memberRepository    : Transactional : ON
     * logRepository       : Transactional : ON_ Exception
     */
    @Test
    void outerTxOff_fail() {
        // given
        String username = "로그예외_outerTxOff_fail";

        //when
        assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        //then : 모든데이터가 정상 저장된다.
//        assertThat(memberRepository.find(username).get().getUsername()).isEqualTo(username);
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isEmpty());
    }

    /**
     * memberService       : Transactional : ON
     * memberRepository    : Transactioanl : OFF
     * logRepository       : Transactional : OFF
     */
    @Test
    void singleTx(){
        // given
        String username = "singleTx_success";

        //when
        memberService.joinV1(username);

        //then : 모든데이터가 정상 저장된다.
//        assertThat(memberRepository.find(username).get().getUsername()).isEqualTo(username);
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService       : Transactional : ON
     * memberRepository    : Transactioanl : ON
     * logRepository       : Transactional : ON
     */
    @Test
    void outerTx_success() {
        // given
        String username = "outerTx_success";

        //when
        memberService.joinV1(username);

        //then : 모든데이터가 정상 저장된다.
//        assertThat(memberRepository.find(username).get().getUsername()).isEqualTo(username);
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService       : Transactional : ON
     * memberRepository    : Transactional : ON
     * logRepository       : Transactional : ON_ Exception
     */
    @Test
    void outerInnerTxOn_fail() {
        // given
        String username = "로그예외_outerInnerTxOn_fail";

        //when
        assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        //then : 모든데이터가 롤백된다..
//        assertThat(memberRepository.find(username).get().getUsername()).isEqualTo(username);
        Assertions.assertTrue(memberRepository.find(username).isEmpty());
        Assertions.assertTrue(logRepository.find(username).isEmpty());
    }

    /**
     * memberService       : Transactional : ON
     * memberRepository    : Transactional : ON
     * logRepository       : Transactional : ON_ Exception
     */
    @Test
    void recoverException_fail() {
        // given
        String username = "로그예외_recoverExcption_fail";

        //when
        assertThatThrownBy(() -> memberService.joinV2(username))
                .isInstanceOf(UnexpectedRollbackException.class);

        //then : 모든데이터가 롤백된다..
//        assertThat(memberRepository.find(username).get().getUsername()).isEqualTo(username);
        Assertions.assertTrue(memberRepository.find(username).isEmpty());
        Assertions.assertTrue(logRepository.find(username).isEmpty());
    }

    /**
     * memberService       : Transactional : ON
     * memberRepository    : Transactional : ON
     * logRepository       : Transactional : ON_ Exception
     */
    @Test
    void recoverException_success() {
        // given
        String username = "로그예외_recoverExcption_success";

        //when
        memberService.joinV2(username);

        //then : 모든데이터가 롤백된다..
//        assertThat(memberRepository.find(username).get().getUsername()).isEqualTo(username);
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isEmpty());
    }
}