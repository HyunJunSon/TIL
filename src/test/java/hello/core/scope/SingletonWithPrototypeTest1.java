package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        bean1.addCount();
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
        bean2.addCount();

        assertThat(bean1.count).isEqualTo(1);
        assertThat(bean1.count).isEqualTo(bean2.count);

    }

    @Test
    void sigletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class);

        ClientBean bean = ac.getBean(ClientBean.class);
        int logic1 = bean.logic();
        assertThat(logic1).isEqualTo(1);

        ClientBean bean2 = ac.getBean(ClientBean.class);
        int logic2 = bean2.logic();
        assertThat(logic2).isEqualTo(1);

        Assertions.assertThat(logic1).isEqualTo(logic2);

    }

    @Scope("singleton")
    @RequiredArgsConstructor
    static class ClientBean{
//        private final PrototypeBean prototypeBean; //생성시점에 주입
//        private final ObjectProvider<PrototypeBean> prototypeBeanProvider;
        private final Provider<PrototypeBean> prototypeBeanProvider;
        public int logic(){
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount(){
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }

}
