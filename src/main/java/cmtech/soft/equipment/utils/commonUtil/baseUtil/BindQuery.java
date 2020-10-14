package cmtech.soft.equipment.utils.commonUtil.baseUtil;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BindQuery {

    /**
     * 查询条件
     *
     * @return
     */
    Comparison comparison() default Comparison.EQ;

    /**
     * 数据库字段，默认为空，自动根据驼峰转下划线
     *
     * @return
     */
    String field() default "";

    /**
     * 忽略该字段
     *
     * @return
     */
    boolean ignore() default false;

}
