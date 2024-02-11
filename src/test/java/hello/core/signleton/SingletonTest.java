package hello.core.signleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        //1. 조회: 호출할때마다 객체를 생성
        MemberService memberService = appConfig.memberService();
        //2. 조회: 호출할때마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        //3. 참조값이 다른것을 확인
//        System.out.println("memberService = " + memberService);
//        System.out.println("memberService1 = " + memberService1);

        //memberService1 != memberService2
        assertThat(memberService).isNotSameAs(memberService1);

    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService instance = SingletonService.getInstance();
        SingletonService instance1 = SingletonService.getInstance();

        assertThat(instance).isSameAs(instance1);
        assertThat(instance).isEqualTo(instance1);

        //same ==
        //equal equal

    }

    @Test
    @DisplayName("스프링 컨네이너와 싱글톤")
    void springContainer() {
//        AppConfig appConfig = new AppConfig();
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        //1. 조회: 호출할때마다 객체를 생성
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        //2. 조회: 호출할때마다 객체를 생성
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        //3. 참조값이 다른것을 확인
//        System.out.println("memberService = " + memberService);
//        System.out.println("memberService1 = " + memberService1);

        //memberService1 != memberService2
        assertThat(memberService).isSameAs(memberService1);

    }

}
