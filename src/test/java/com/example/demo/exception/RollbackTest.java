package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class RollbackTest {

    @Autowired RollbackService service;

    @Test
    void runtimeExcption(){
        assertThatThrownBy(() -> service.runtimeException())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void checkedExcption(){
        assertThatThrownBy(() -> service.checkedexcption())
                .isInstanceOf(MyException.class);
    }

    @Test
    void rollbackForExcption() {
        assertThatThrownBy(() -> service.rollbackForTest())
                .isInstanceOf(MyException.class);
    }


    @TestConfiguration
    static class RollbackTestConfig{
        @Bean
        RollbackService rollbackService(){
            return new RollbackService();
        }
    }
    @Slf4j
    static class RollbackService {

        //런타임예외발생 : 롤백
        @Transactional
        public void runtimeException() {
            log.info("call runtimeException");
            throw new RuntimeException();
        }

        //체크예외발생 : 커밋
        @Transactional
        public void checkedexcption() throws Exception {
            log.info("call check exception");
            throw new MyException();
        }

        //체크 예외 rollback 지정 : 롤백
        @Transactional(rollbackFor = MyException.class)
        public void rollbackForTest() throws MyException {
            log.info("call rollbackForTest");
            throw new MyException();
        }
    }

    static class MyException extends Exception {
    }


}
