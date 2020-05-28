package cmtech.soft.equipment.utils;

import cmtech.soft.equipment.utils.Aop.InterceptAction;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class MyCopyUtils {
    /**
     * 对象属性拷贝 <br>
     * 将源对象的属性拷贝到目标对象
     *
     * @param source 源对象
     * @param target 目标对象
     */
    @InterceptAction("将源对象的属性拷贝到目标对象")
    public static void copyProperties(Object source, Object target) {
            BeanUtils.copyProperties(source, target);
    }

    /**
     * @param input 输入集合
     * @param clzz  输出集合类型
     * @param <E>   输入集合类型
     * @param <T>   输出集合类型
     * @return 返回集合
     */
    @InterceptAction("将源对象集合的属性拷贝到目标对象集合")
    public static <E, T> List<T> copyList(List<E> input, Class<T> clzz) throws IllegalAccessException, InstantiationException {
        List<T> output = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(input)) {
            for (E source : input) {
                T target = clzz.newInstance();
                BeanUtils.copyProperties(source, target);
                output.add(target);
            }
        }
        return output;
    }
}
