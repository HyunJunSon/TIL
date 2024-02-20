package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * connection param 연동, pool을 고려한 종료
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepositoryV1;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection con = dataSource.getConnection();
        try {
            con.setAutoCommit(false); // 트랜젝션 시작
            //비지니스 로직 시작
            bizLogic(con, fromId, toId, money);
            con.commit(); //성공시 커밋
        } catch (SQLException e) {
            con.rollback(); // 실패시 롤백
            throw new IllegalStateException(e);
        } finally {
            release(con);
        }


    }

    private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepositoryV1.findById(con, fromId);
        Member toMember = memberRepositoryV1.findById(con, toId);

        memberRepositoryV1.update(con, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepositoryV1.update(con, toId, toMember.getMoney() + money);
    }

    private static void release(Connection con) {
        if (con != null){
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException e) {
                log.error("error", e);
            }
        }
    }

    private static void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")){
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
