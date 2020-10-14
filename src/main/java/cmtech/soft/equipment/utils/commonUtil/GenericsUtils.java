package cmtech.soft.equipment.utils.commonUtil;

import cmtech.soft.equipment.utils.commonUtil.baseUtil.BindQuery;
import cmtech.soft.equipment.utils.commonUtil.baseUtil.Cons;
import cmtech.soft.equipment.utils.commonUtil.baseUtil.IGetter;
import cmtech.soft.equipment.utils.commonUtil.baseUtil.ISetter;
import cmtech.soft.equipment.utils.commonUtil.logUtil.LogUtilExtend;
import com.baomidou.mybatisplus.annotation.TableField;
import io.lettuce.core.dynamic.support.ResolvableType;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class GenericsUtils {

    @Autowired
    static LogUtilExtend log;

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends
     * GenricManager<Book>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or <code>Object.class</code> if cannot be determined
     */
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends GenricManager<Book>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     */
    public static Class getSuperClassGenricType(Class clazz, int index)
            throws IndexOutOfBoundsException {
        // 获取父类类型,如果父类是带泛型的类那么返回ParameterizedType，如果父类不是带泛型的类，那么返回父类Class
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    /**
     * Copy属性到另一个对象
     *
     * @param source
     * @param target
     */
    public static Object copyProperties(Object source, Object target) {
        // 链式调用无法使用BeanCopier拷贝，换用BeanUtils
        org.springframework.beans.BeanUtils.copyProperties(source, target);
        return target;
    }

    /***
     * 将对象转换为另外的对象实例
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convert(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        T target = null;
        try {
            target = clazz.getConstructor().newInstance();
            copyProperties(source, target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }

    /***
     * 将对象转换为另外的对象实例
     * @param sourceList
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> convertList(List sourceList, Class<T> clazz) {
        if (V.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        List<T> resultList = new ArrayList<>();
        // 类型相同，直接跳过
        if (clazz.getName().equals(sourceList.get(0).getClass().getName())) {
            return sourceList;
        }
        // 不同，则转换
        try {
            for (Object source : sourceList) {
                T target = clazz.getConstructor().newInstance();
                copyProperties(source, target);
                resultList.add(target);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /***
     * 附加Map中的属性值到Model
     * @param model
     * @param propMap
     */
    public static void bindProperties(Object model, Map<String, Object> propMap) {
        if (V.isEmpty(propMap)) {
            return;
        }
        List<Field> fields = extractAllFields(model.getClass());
        Map<String, Field> fieldNameMaps = convertToStringKeyObjectMap(fields, "name");
        for (Map.Entry<String, Object> entry : propMap.entrySet()) {
            Field field = fieldNameMaps.get(entry.getKey());
            if (field != null) {
                try {
                    Object value = convertValueToFieldType(entry.getValue(), field);
                    setProperty(model, entry.getKey(), value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /***
     * 获取对象的属性值
     * @param obj
     * @param field
     * @return
     */
    public static Object getProperty(Object obj, String field) {
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        return wrapper.getPropertyValue(field);
    }

    /***
     * 获取对象的属性值并转换为String
     * @param obj
     * @param field
     * @return
     */
    public static String getStringProperty(Object obj, String field) {
        Object property = getProperty(obj, field);
        if (property == null) {
            return null;
        }
        return String.valueOf(property);
    }

    /***
     * 设置属性值
     * @param obj
     * @param field
     * @param value
     */
    public static void setProperty(Object obj, String field, Object value) {
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        wrapper.setPropertyValue(field, value);
    }

    /**
     * 转换为field对应的类型
     *
     * @param value
     * @param field
     * @return
     */
    public static Object convertValueToFieldType(Object value, Field field) {
        String type = field.getGenericType().getTypeName();
        if (value.getClass().getName().equals(type)) {
            return value;
        }
        if (Integer.class.getName().equals(type)) {
            return Integer.parseInt(S.valueOf(value));
        } else if (Long.class.getName().equals(type)) {
            return Long.parseLong(S.valueOf(value));
        } else if (Double.class.getName().equals(type)) {
            return Double.parseDouble(S.valueOf(value));
        } else if (BigDecimal.class.getName().equals(type)) {
            return new BigDecimal(S.valueOf(value));
        } else if (Float.class.getName().equals(type)) {
            return Float.parseFloat(S.valueOf(value));
        } else if (Boolean.class.getName().equals(type)) {
            return V.isTrue(S.valueOf(value));
        } else if (type.contains(Date.class.getSimpleName())) {
            return D.fuzzyConvert(S.valueOf(value));
        }
        return value;
    }

    /**
     * 获取数据表的列名（驼峰转下划线蛇形命名）
     * <br>
     * 列名取值优先级： @BindQuery.field > @TableField.value > field.name
     *
     * @param field
     * @return
     */
    public static String getColumnName(Field field) {
        String columnName = null;
        if (field.isAnnotationPresent(BindQuery.class)) {
            columnName = field.getAnnotation(BindQuery.class).field();
        } else if (field.isAnnotationPresent(TableField.class)) {
            columnName = field.getAnnotation(TableField.class).value();
        }
        return V.notEmpty(columnName) ? columnName : S.toSnakeCase(field.getName());
    }

    /**
     * 获取类所有属性（包含父类中属性）
     *
     * @param clazz
     * @return
     */
    public static List<Field> extractAllFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        Set<String> fieldNameSet = new HashSet<>();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            if (V.notEmpty(fields)) { //被重写属性，以子类override的为准
                Arrays.stream(fields).forEach((field) -> {
                    if (!fieldNameSet.contains(field.getName())) {
                        fieldList.add(field);
                        fieldNameSet.add(field.getName());
                    }
                });
            }
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    /***
     * Key-Object对象Map
     * @param allLists
     * @return
     */
    public static <T> Map<String, T> convertToStringKeyObjectMap(List<T> allLists, String... fields) {
        if (allLists == null || allLists.isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        Map<String, T> allListMap = new LinkedHashMap<>(allLists.size());
        // 转换为map
        try {
            for (T model : allLists) {
                String key = null;
                if (V.isEmpty(fields)) {
                    //未指定字段，以id为key
                    key = getStringProperty(model, Cons.FieldName.id.name());
                }
                // 指定了一个字段，以该字段为key，类型同该字段
                else if (fields.length == 1) {
                    key = getStringProperty(model, fields[0]);
                } else { // 指定了多个字段，以字段S.join的结果为key，类型为String
                    List list = new ArrayList();
                    for (String fld : fields) {
                        list.add(getProperty(model, fld));
                    }
                    key = S.join(list);
                }
                if (key != null) {
                    allListMap.put(key, model);
                } else {
                    log.warn(model.getClass().getName() + " 的属性 " + fields[0] + " 值存在 null，转换结果需要确认!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allListMap;
    }

    /***
     * Key-Object-List列表Map
     * @param allLists
     * @param fields
     * @param <T>
     * @return
     */
    public static <T> Map<String, List<T>> convertToStringKeyObjectListMap(List<T> allLists, String... fields) {
        if (allLists == null || allLists.isEmpty()) {
            return null;
        }
        Map<String, List<T>> allListMap = new LinkedHashMap<>(allLists.size());
        // 转换为map
        try {
            for (T model : allLists) {
                String key = null;
                if (V.isEmpty(fields)) {
                    //未指定字段，以id为key
                    key = getStringProperty(model, Cons.FieldName.id.name());
                }
                // 指定了一个字段，以该字段为key，类型同该字段
                else if (fields.length == 1) {
                    key = getStringProperty(model, fields[0]);
                } else { // 指定了多个字段，以字段S.join的结果为key，类型为String
                    List list = new ArrayList();
                    for (String fld : fields) {
                        list.add(getProperty(model, fld));
                    }
                    key = S.join(list);
                }
                if (key != null) {
                    List<T> list = allListMap.get(key);
                    if (list == null) {
                        list = new ArrayList<T>();
                        allListMap.put(key, list);
                    }
                    list.add(model);
                } else {
                    log.warn(model.getClass().getName() + " 的属性 " + fields[0] + " 值存在 null，转换结果需要确认!");
                }
            }
        } catch (Exception e) {
            log.warn("转换key-model-list异常", e);
        }

        return allListMap;
    }

    /**
     * 从list对象列表中提取Id主键值到新的List
     *
     * @param objectList 对象list
     * @param <E>
     * @return
     */
    public static <E> List collectIdToList(List<E> objectList) {
        if (V.isEmpty(objectList)) {
            return Collections.emptyList();
        }
        return collectToList(objectList, Cons.FieldName.id.name());
    }

    /***
     * 从list对象列表中提取指定属性值到新的List
     * @param objectList
     * @param getterPropName
     * @param <E>
     * @return
     */
    public static <E> List collectToList(List<E> objectList, String getterPropName) {
        List fieldValueList = new ArrayList();
        try {
            for (E object : objectList) {
                Object fieldValue = getProperty(object, getterPropName);
                if (!fieldValueList.contains(fieldValue)) {
                    fieldValueList.add(fieldValue);
                }
            }
        } catch (Exception e) {
            log.warn("提取属性值异常, getterPropName=" + getterPropName, e);
        }
        return fieldValueList;
    }

    /**
     * 从list对象列表中提取指定属性值到新的List
     *
     * @param objectList 对象list
     * @param getterFn   get方法
     * @param <T>
     * @return
     */
    public static <E, T> List collectToList(List<E> objectList, IGetter<T> getterFn) {
        if (V.isEmpty(objectList)) {
            return Collections.emptyList();
        }
        String getterPropName = convertToFieldName(getterFn);
        return collectToList(objectList, getterPropName);
    }

    /***
     * 从对象集合提取某个属性值到list中
     * @param setterFieldName
     * @param fromList
     * @param getterFieldName
     * @param valueMatchMap
     * @param <E>
     */
    public static <E> void bindPropValueOfList(String setterFieldName, List<E> fromList, String getterFieldName, Map valueMatchMap) {
        if (V.isEmpty(fromList) || V.isEmpty(valueMatchMap)) {
            return;
        }
        try {
            for (E object : fromList) {
                Object fieldValue = getProperty(object, getterFieldName);
                Object value = null;
                if (valueMatchMap.containsKey(fieldValue)) {
                    value = valueMatchMap.get(fieldValue);
                } else {
                    // 获取到当前的属性值
                    String fieldValueStr = getStringProperty(object, getterFieldName);
                    // 获取到当前的value
                    value = valueMatchMap.get(fieldValueStr);
                }
                // 赋值
                setProperty(object, setterFieldName, value);
            }
        } catch (Exception e) {
            log.warn("设置属性值异常, setterFieldName=" + setterFieldName, e);
        }
    }

    /**
     * 克隆对象
     *
     * @param ent
     * @param <T>
     * @return
     */
    public static <T> T cloneBean(T ent) {
        // 克隆对象
        try {
            T cloneObj = (T) org.springframework.beans.BeanUtils.instantiateClass(ent.getClass());
            copyProperties(ent, cloneObj);
            return cloneObj;
        } catch (Exception e) {
            log.warn("Clone Object " + ent.getClass().getSimpleName() + " error", e);
            return ent;
        }
    }

    /***
     * 转换方法引用为属性名
     * @param fn
     * @return
     */
    public static <T> String convertToFieldName(IGetter<T> fn) {
        SerializedLambda lambda = getSerializedLambda(fn);
        String methodName = lambda.getImplMethodName();
        String prefix = null;
        if (methodName.startsWith("get")) {
            prefix = "get";
        } else if (methodName.startsWith("is")) {
            prefix = "is";
        }
        if (prefix == null) {
            log.warn("无效的getter方法: " + methodName);
        }
        return S.uncapFirst(S.substringAfter(methodName, prefix));
    }

    /***
     * 转换方法引用为属性名
     * @param fn
     * @return
     */
    public static <T, R> String convertToFieldName(ISetter<T, R> fn) {
        SerializedLambda lambda = getSerializedLambda(fn);
        String methodName = lambda.getImplMethodName();
        if (!methodName.startsWith("set")) {
            log.warn("无效的setter方法: " + methodName);
        }
        return S.uncapFirst(S.substringAfter(methodName, "set"));
    }

    /**
     * 获取类的指定属性（包含父类中属性）
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field extractField(Class clazz, String fieldName) {
        return ReflectionUtils.findField(clazz, fieldName);
    }

    /**
     * 获取目标类
     *
     * @param instance
     * @return
     */
    public static Class getTargetClass(Object instance) {
        Class targetClass = (instance instanceof Class) ? (Class) instance : AopUtils.getTargetClass(instance);
        return targetClass;
    }

    /**
     * 从实例中获取目标对象的泛型定义类class
     *
     * @param instance 对象实例
     * @param index
     * @return
     */
    public static Class getGenericityClass(Object instance, int index) {
        Class hostClass = getTargetClass(instance);
        ResolvableType resolvableType = ResolvableType.forClass(hostClass).getSuperType();
        ResolvableType[] types = resolvableType.getGenerics();
        if (V.isEmpty(types) || index >= types.length) {
            types = resolvableType.getSuperType().getGenerics();
        }
        if (V.notEmpty(types) && types.length > index) {
            return types[index].resolve();
        }
        log.debug("无法从 {} 类定义中获取泛型类{}", hostClass.getName(), index);
        return null;
    }

    /**
     * 根据指定Key对list去重
     *
     * @param list
     * @param getterFn
     * @param <T>
     * @return 去重后的list
     */
    public static <T> List<T> distinctByKey(List<T> list, Function<? super T, ?> getterFn) {
        return list.stream().filter(distinctPredicate(getterFn)).collect(Collectors.toList());
    }

    /**
     * 去重的辅助方法
     *
     * @param getterFn
     * @param <T>
     * @return
     */
    private static <T> Predicate<T> distinctPredicate(Function<? super T, ?> getterFn) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(getterFn.apply(t));
    }

    /***
     * 获取类对应的Lambda
     * @param fn
     * @return
     */
    public static SerializedLambda getSerializedLambda(Serializable fn) {
        SerializedLambda lambda = null;
        try {
            Method method = fn.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            lambda = (SerializedLambda) method.invoke(fn);
        } catch (Exception e) {
            log.error("获取SerializedLambda异常, class=" + fn.getClass().getSimpleName(), e);
        }
        return lambda;
    }

}
