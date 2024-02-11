package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.junit.jupiter.api.Assertions.*;

public class ConponentFilterAppConfigTest {
    @Test
    void filterScan(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFileterAppConfig.class);

        BeanA beanA = ac.getBean("beanA", BeanA.class);
//        BeanB beanB = ac.getBean("beanB", BeanB.class);

        Assertions.assertThat(beanA).isNotNull();
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class));
    }

    @Configuration
    @ComponentScan(
            includeFilters = @ComponentScan.Filter(classes = MyIncludeComponent.class),
            excludeFilters = @ComponentScan.Filter(classes = MyExcludeComponent.class)
    )
    static class ComponentFileterAppConfig{

    }
}
