package cmtech.soft.equipment.utils;

import cmtech.soft.equipment.utils.commonUtil.GenericsUtils;
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
    public static <K> List<Map<String, Object>> listEntityToListMap(List<K> list) {
        List<Map<String, Object>> listMap = new ArrayList<>();

        for (K k : list) {
            listMap.add(JSON.parseObject(JSON.toJSONString(k).getBytes(),HashMap.class));
        }
        return listMap;
    }

    /**
     * entity to map
     * @param k 范型对象
     * @param <K> 范型
     * @return map
     */
    public static <K> Map<String, Object> entityToMap(K k) {
        Map<String, Object> map=new HashMap<>();
        map=JSON.parseObject(JSON.toJSONString(k).getBytes(),HashMap.class);
        return map;
    }

    /**
     * object 转 entity
     *
     * @param object
     * @param clazz
     * @return entity
     */
    public static <K> K objT(Object object,Class<K> clazz) {
        return (K) Optional.ofNullable(JSON.parseObject(JSON.toJSONString(object).getBytes(),clazz)).orElseGet(()->create(clazz));
    }

    /**
     * 如果不为空返回原来，为空返回new 对象 代替 Optional.ofNullable(condition).orElseGet(()->new QueryModelBomPartsPo()）
     * @param k 范型对象
     * @param clazz 范型类型
     * @param <K> 范型类型
     * @return 如果不为空返回原来，为空返回new 对象
     */
    public static <K> K $(K k,Class<K> clazz) {

        // 另外一种方式 使用方式MyConvert.$(condition,(new MyConvert<QueryModelBomPartsPo>(){}).getTClass()) 太长不用
//    public static <T> T $(T t,Class<T> clazz) {
//        return Optional.ofNullable(t).orElseGet(()->create(clazz));
        // 使用方式 MyConvert.$(condition,QueryModelBomPartsPo.class）
        return Optional.ofNullable(k).orElseGet(()->create(clazz));
    }

    /**
     * 非静态方法: 如果不为空返回原来，为空返回new 对象 代替 Optional.ofNullable(condition).orElseGet(()->new QueryModelBomPartsPo()）
     * @param t 范型对象
     * @return 如果不为空返回原来，为空返回new 对象  使用方式  MyConvert<QueryModelBomPartsPo> myConvert=new MyConvert<QueryModelBomPartsPo>(){}; myConvert.$(condition)
     * 不加{}即匿名内部 GenericsUtils.getSuperClassGenricType(getClass())则是Object, 使用{}相当于声明了一个子类所以可以使用getSuperClassGenricType获取父类的参数类型
     */
    public T $(T t)  {
//        System.out.println(GenericsUtils.getSuperClassGenricType(getClass()));
        return Optional.ofNullable(t).orElseGet(()->create((Class<T>) GenericsUtils.getSuperClassGenricType(getClass())));  // GenericsUtils.getSuperClassGenricType(getClass()获取范型类型
    }

    /**
     * 创建一个类型对象 create(User.class)
     * @param clazz
     * @param <K>
     * @return
     */
    public static <K> K create(Class<K> clazz) {
        try {
            //T must have a no arg constructor for this to work
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
