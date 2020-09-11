package cmtech.soft.equipment.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @param <T> 范型
 */
public class MyListUtil<T, U> {

    public static <T, U> List<U> getListTU(List<T> list, Function<? super T, ? extends U> iConvert) {
        List<U> returnList = null;

        if (!isNullOrEmpty(list)) {
            returnList = list.stream().map(iConvert).collect(Collectors.toList());
        }

        return returnList;
    }

    // 集合去重
    public static <T> List<T> distinct(List<T> list) {
        Set hashSet = new HashSet(list);
        List<T> newList = new ArrayList<>();
        newList.addAll(hashSet);
        return newList;
    }

    // 判断集合是否为空
    public static <T> boolean isNullOrEmpty(List<T> list) {
        boolean flag = true;

        if (list != null && !list.isEmpty()) {
            flag = false;
        }

        return flag;
    }

    // 查找重复的数据集合
    public static <T> List<T> findRepeatData(List<T> list) {
        List<T> newList = new ArrayList<>();
        List<T> distinctList = distinct(list);

        if (list != null && distinctList != null && (list.size() > distinctList.size())) {
            for (T t : distinctList) {
                if (findTimesInList(list, t) > 1) {
                    newList.add(t);
                }
            }
        }

        return newList;
    }

    // 查找是否有重复数据
    public static <T> boolean isHasRepeatData(List<T> list) {
        boolean flag = false;

        List<T> distinctList = distinct(list);

        if (list != null && distinctList != null && (list.size() > distinctList.size())) {
            flag = true;
        }

        return flag;
    }

    // 找出一个元素在集合中出现的次数
    public static <T> Integer findTimesInList(List<T> list, T t) {
        Integer times = 0;

        if (!isNullOrEmpty(list) && t != null) {

            for (T obj : list) {
                if (t.equals(obj)) {
                    times++;
                }
            }
        }

        return times;
    }
}
