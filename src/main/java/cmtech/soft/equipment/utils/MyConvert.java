package cmtech.soft.equipment.utils;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.ParameterizedType;
import java.util.*;

public class MyConvert<T>  {

    /**
     * list entity 转list map
     * 使用 MyConvert.listEntityToListMap<T>
     * @param list
     * @return
     */
    public static <T> List<Map<String, Object>> listEntityToListMap(List<T> list) {
        List<Map<String, Object>> listMap = new ArrayList<>();

        for (T t : list) {
            listMap.add(JSON.parseObject(JSON.toJSONString(t).getBytes(),HashMap.class));
        }
        return listMap;
    }

    /**
     * object 转 entity
     *
     * @param object
     * @param clazz
     * @return entity
     */
    public static <T> T objT(Object object,Class<T> clazz) {
       return (T) Optional.ofNullable(JSON.parseObject(JSON.toJSONString(object).getBytes(),clazz)).orElseGet(()->create(clazz));
    }

    /**
     * 如果不为空返回原来，为空返回new 对象 代替 Optional.ofNullable(condition).orElseGet(()->new QueryModelBomPartsPo()）
     * @param t 范型对象
     * @param clazz 范型类型
     * @param <T> 范型类型
     * @return 如果不为空返回原来，为空返回new 对象
     */
    public static <T> T $(T t,Class<T> clazz) {

        // 另外一种方式 使用方式MyConvert.$(condition,(new MyConvert<QueryModelBomPartsPo>(){}).getTClass()) 太长不用
//    public static <T> T $(T t,Class<T> clazz) {
//        return Optional.ofNullable(t).orElseGet(()->create(clazz));

        // 使用方式 MyConvert.$(condition,QueryModelBomPartsPo.class）
        return Optional.ofNullable(t).orElseGet(()->create(clazz));
    }

    /**
     * 非静态方法: 如果不为空返回原来，为空返回new 对象 代替 Optional.ofNullable(condition).orElseGet(()->new QueryModelBomPartsPo()）
     * @param t 范型对象
     * @return 如果不为空返回原来，为空返回new 对象  使用方式  MyConvert<QueryModelBomPartsPo> myConvert=new MyConvert<QueryModelBomPartsPo>(){};  myConvert.$(condition)
     */
    public T $(T t) {
        return Optional.ofNullable(t).orElseGet(()->create((Class<T>) GenericsUtils.getSuperClassGenricType(getClass())));  // GenericsUtils.getSuperClassGenricType(getClass()获取范型类型
    }

    /**
     * 创建一个类型对象 create(User.class)
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> clazz) {
        try {
            //T must have a no arg constructor for this to work
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取范型的类型
     * @return 范型类型
     */
    @Deprecated
    public Class<T> getTClass()
    {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }
}
