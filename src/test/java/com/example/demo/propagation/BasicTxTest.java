package com.example.demo.propagation;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest
class BasicTxTest {

    @Autowired
    PlatformTransactionManager txManager;

    @TestConfiguration
    static class config{
        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Test
    void commit(){
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());
        
        log.info("트랙잭션 커밋 시작");
        txManager.commit(status);
    }

    @Test
    void rollback() {
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());

        log.info("트랙잭션 롤백 시작");
        txManager.rollback(status);
    }

    @Test
    void double_commit(){
        log.info("트랜잭션1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionDefinition());

        log.info("트랙잭션 커밋 시작");
        txManager.commit(tx1);

        log.info("트랜잭션2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionDefinition());

        log.info("트랙잭션 커밋 시작");
        txManager.commit(tx2);
    }

    @Test
    void double_commit_rollback() {
        log.info("트랜잭션1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionDefinition());
        log.info("tx1.isNewTransation() = {}", tx1.isNewTransaction());

        log.info("트랙잭션 커밋 시작");
        txManager.commit(tx1);

        log.info("트랜잭션2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionDefinition());
        log.info("tx2.isNewTransation() = {}", tx2.isNewTransaction());

        log.info("트랙잭션 롤백 시작");
        txManager.rollback(tx2);
    }

    @Test
    void inner_commit(){
        log.info("외부 트랙젝션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionDefinition());
        log.info("outer.isNewTransation() = {}", outer.isNewTransaction());

        log.info("내부 트랙잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionDefinition());
        log.info("inner.isNewTransation() = {}", inner.isNewTransaction());
        log.info("내부트랙젝션 커밋");
        txManager.commit(inner);

        txManager.commit(outer);
    }

    @Test
    void outer_rollback() {
        log.info("외부 트랙젝션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionDefinition());

        log.info("내부 트랙잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionDefinition());
        log.info("내부트랙젝션 커밋");
        txManager.commit(inner);

        txManager.rollback(outer);
    }

    @Test
    void inner_rollback() {
        log.info("외부 트랙젝션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionDefinition());

        log.info("내부 트랙잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionDefinition());
        log.info("내부트랙젝션 롤백");
        txManager.rollback(inner); //roll-back only mark

        log.info("외부트랙잭션 커밋");
//        txManager.commit(outer);
        Assertions.assertThatThrownBy(() -> txManager.commit(outer))
                .isInstanceOf(UnexpectedRollbackException.class);
    }

    @Test
    void inner_rollback_requires_new(){
        log.info("외부 트랙젝션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionDefinition());
        log.info("outer.isNewTransation() = {}", outer.isNewTransaction());


        log.info("내부 트랙잭션 시작");
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus inner = txManager.getTransaction(definition);
        log.info("outer.isNewTransation() = {}", outer.isNewTransaction());
        log.info("내부트랙젝션 롤백");
        txManager.rollback(inner); //roll-back only mark

        log.info("외부트랙잭션 커밋");
//        txManager.commit(outer);
        txManager.commit(outer);
    }


}
