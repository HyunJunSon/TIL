package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FixDiscountPolicy implements DiscountPolicy {
    private int discountFixAmount = 1000;
    @Override
    public int discount(Member member, int price) {

        return member.getGrade() == Grade.VIP ? discountFixAmount : 0;
    }
}
