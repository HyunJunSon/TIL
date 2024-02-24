package hello.jdbc.repository;

import hello.jdbc.domain.Member;

import java.sql.SQLException;


public interface MemberRepostioryEx {
    Member save(Member member) throws SQLException;

    Member findById(String memeberId);

    void update(String memberId, int money) throws SQLException;

    void delete(String memberId) throws SQLException;
}
