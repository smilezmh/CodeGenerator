package cmtech.soft.equipment.utils.commonUtil.baseUtil;

import java.io.Serializable;

@FunctionalInterface
public interface IGetter<T> extends Serializable {
    Object apply(T source);
}
