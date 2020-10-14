package cmtech.soft.equipment.utils.commonUtil.wrapperUtil;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public interface IWrapperFactory<T> {
    QueryWrapper wrapper = (QueryWrapper) new QueryWrapper().select("id").eq("id", 0);

    Wrapper<T> create();

    Wrapper<T> create(Condition condition, String... fields);

    Wrapper<T> create(boolean isLambda, Condition condition, String... fields);

    Wrapper<T> create(Condition condition);

    Wrapper<T> create(Condition condition, QueryWrapper queryWrapper, String... fields);

    Wrapper<T> create(Condition condition, QueryWrapper queryWrapper);

    Wrapper<T> createUw();
}
