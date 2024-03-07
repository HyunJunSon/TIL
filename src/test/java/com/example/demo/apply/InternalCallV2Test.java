package com.example.demo.apply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV2Test {

    @Autowired
    CallService callService;

    @Test
    void printProxy() {
        boolean aopProxy = AopUtils.isAopProxy(callService);
        log.info("aopProxy = {}", aopProxy);
    }


    @Test
    void externalCall() {
        callService.exteranl();
    }

    @TestConfiguration
    static class InternalCallV2TestConfig {
        @Bean
        CallService callService() {
            return new CallService();
        }
        @Bean
        InternalService internalService(){
            return new InternalService();
        }
    }

    @Slf4j
    @RequiredArgsConstructor
    static class CallService {
        private InternalService internalService;


        public void exteranl() {
            log.info("call external");
            printTxInfo();
            internalService.internal();

        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active={}", txActive);
//            boolean currentTransactionReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
//            log.info("current transaction is only?={}", currentTransactionReadOnly);
        }


    }

    static class InternalService {
        @Transactional
        public void internal() {
            log.info("call internal");
            printTxInfo();
        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active={}", txActive);
//            boolean currentTransactionReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
//            log.info("current transaction is only?={}", currentTransactionReadOnly);
        }
    }


}

