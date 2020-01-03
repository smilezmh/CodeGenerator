package cmtech.soft.equipment.utils.Aop;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 注解式aop拦截，可以实现更加细粒度和更加强的拦截
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InterceptAction {

    // 可以自定义一些参数
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";
}
