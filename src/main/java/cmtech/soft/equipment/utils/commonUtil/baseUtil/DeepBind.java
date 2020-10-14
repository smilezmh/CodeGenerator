package cmtech.soft.equipment.utils.commonUtil.baseUtil;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeepBind {
    @AliasFor("deep")
    boolean value() default false;

    @AliasFor("value")
    boolean deep() default false;
}
