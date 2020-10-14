package cmtech.soft.equipment.utils.commonUtil.wrapperUtil;


import cmtech.soft.equipment.utils.commonUtil.GenericsUtils;
import cmtech.soft.equipment.utils.commonUtil.baseUtil.BindQuery;
import cmtech.soft.equipment.utils.commonUtil.baseUtil.Comparison;
import cmtech.soft.equipment.utils.commonUtil.listUtil.MyListUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.diboot.core.util.BeanUtils;
import com.diboot.core.util.V;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class WrapperFactory<T> implements IWrapperFactory<T> {

    public Wrapper<T> create(Condition condition) {
        QueryWrapper queryWrapper;

        if (V.notEmpty(condition)) {
            queryWrapper = new QueryWrapper();
            dtoToWrapper(queryWrapper, condition, null);
        } else {
            queryWrapper = wrapper;
        }

        return queryWrapper;
    }

    @Override
    public Wrapper<T> create(Condition condition, QueryWrapper queryWrapper) {

        if (V.notEmpty(condition)) {
            dtoToWrapper(queryWrapper, condition, null);
        }

        return queryWrapper;
    }

    @Override
    public Wrapper<T> create(Condition condition, QueryWrapper queryWrapper, String... fields) {

        if (V.notEmpty(condition)) {
            dtoToWrapper(queryWrapper, condition, MyListUtil.streamConvertToList(Arrays.asList(fields), field -> String.valueOf(field)));
        }

        return queryWrapper;
    }

    @Override
    public Wrapper<T> create(Condition condition, String... fields) {
        QueryWrapper queryWrapper;

        if (V.notEmpty(fields) && V.notEmpty(condition)) {
            queryWrapper = new QueryWrapper();
            List<String> collections = MyListUtil.streamConvertToList(Arrays.asList(fields), field -> String.valueOf(field));
            dtoToWrapper(queryWrapper, condition, collections);
        } else {
            queryWrapper = wrapper;
        }

        return queryWrapper;
    }

    @Override
    public Wrapper<T> create(boolean isLambda, Condition condition, String... fields) {
        Wrapper wrapper = create(condition, fields);

        if (isLambda) {
            wrapper = ((QueryWrapper) wrapper).lambda();
        }

        return wrapper;
    }

    @Override
    public Wrapper<T> create() {
        return new QueryWrapper();
    }

    @Override
    public Wrapper<T> createUw() {
        return new UpdateWrapper();
    }

    private static <T> void dtoToWrapper(QueryWrapper<T> wrapper, Condition dto, Collection<String> fields) {
        // 转换
        List<Field> declaredFields = BeanUtils.extractAllFields(dto.getClass());
        for (Field field : declaredFields) {
            String fieldName;
            // 非指定属性，非逻辑删除字段，跳过
            if (fields != null && !fields.contains(field.getName())) {
                continue;
            }

            if ((fieldName = field.getName()).equals("current") || fieldName.equals("size"))
                continue;

            //忽略static，以及final，transient
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            boolean isFinal = Modifier.isFinal(field.getModifiers());
            boolean isTransient = Modifier.isTransient(field.getModifiers());
            if (isStatic || isFinal || isTransient) {
                continue;
            }
            //忽略注解 @TableField(exist = false) 的字段
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField != null && tableField.exist() == false) {
                continue;
            }
            BindQuery query = field.getAnnotation(BindQuery.class);
            if (query != null && query.ignore()) { //忽略字段
                continue;
            }
            //打开私有访问 获取值
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(dto);
            } catch (IllegalAccessException e) {
                e.getStackTrace();
            }
            if (value == null) {
                continue;
            }
            // 对比类型
            Comparison comparison = (query != null) ? query.comparison() : Comparison.EQ;
            // 转换条件
            String columnName = GenericsUtils.getColumnName(field);
            switch (comparison) {
                case EQ:
                    wrapper.eq(columnName, value);
                    break;
                case IN:
                    if (value.getClass().isArray()) {
                        Object[] valueArray = (Object[]) value;
                        wrapper.in(columnName, valueArray);
                    } else if (value instanceof List) {
                        Class clazz = ((List) value).get(0).getClass();
                        wrapper.in(columnName, GenericsUtils.convertList((List) value, clazz));
                    }
                    break;
                case CONTAINS:
                    wrapper.like(columnName, value);
                    break;
                case LIKE:
                    wrapper.like(columnName, value);
                    break;
                case STARTSWITH:
                    wrapper.likeRight(columnName, value);
                    break;
                case GT:
                    wrapper.gt(columnName, value);
                    break;
                case BETWEEN_BEGIN:
                    wrapper.ge(columnName, value);
                    break;
                case GE:
                    wrapper.ge(columnName, value);
                    break;
                case LT:
                    wrapper.lt(columnName, value);
                    break;
                case BETWEEN_END:
                    wrapper.le(columnName, value);
                    break;
                case LE:
                    wrapper.le(columnName, value);
                    break;
                case BETWEEN:
                    if (value.getClass().isArray()) {
                        Object[] valueArray = (Object[]) value;
                        if (valueArray.length == 1) {
                            wrapper.ge(columnName, valueArray[0]);
                        } else if (valueArray.length >= 2) {
                            wrapper.between(columnName, valueArray[0], valueArray[1]);
                        }
                    }
                    // 支持逗号分隔的字符串
                    else if (value instanceof String && ((String) value).contains(",")) {
                        Object[] valueArray = ((String) value).split(",");
                        wrapper.between(columnName, valueArray[0], valueArray[1]);
                    } else {
                        wrapper.ge(columnName, value);
                    }
                    break;
                default:
            }
        }
    }
}
