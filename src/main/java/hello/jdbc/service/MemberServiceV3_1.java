package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;

/**
 * 트랜젝션 매니저
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceV3_1 {

    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepositoryV3;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        //트랙젝션 시작
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            //비지니스 로직 시작
            bizLogic(fromId, toId, money);
            transactionManager.commit(status); //성공시 커밋
        } catch (SQLException e) {
            transactionManager.rollback(status); // 실패시 롤백
            throw new IllegalStateException(e);
        }


    }

    private void bizLogic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepositoryV3.findById(fromId);
        Member toMember = memberRepositoryV3.findById(toId);

        memberRepositoryV3.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepositoryV3.update(toId, toMember.getMoney() + money);
    }


    private static void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
