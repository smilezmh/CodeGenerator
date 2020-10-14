package cmtech.soft.equipment.utils.commonUtil.listUtil;

import org.omg.CORBA.Object;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 集合工具类
 *
 */
@Component
public class MyListUtil {

    public static <T> T[] asArray(T... objs) {
        return objs;
    }

    public static <T> T[] asArray(List<? extends T> list, Class<T> clazz) {
        T[] arr = (T[]) Array.newInstance(clazz, 0);

        if (!isNullOrEmpty(list)) {
            arr = list.toArray(arr);
        }

        return arr;
    }

    public static <T> T[] asArray(List<? extends T> list) {
        T[] arr;
        int size = 0;
        Class clazz = Object.class;

        if (!isNullOrEmpty(list)) {
            clazz = list.get(0).getClass();
            size = list.size();
        }

        arr = isNullOrEmpty(list) ? (T[]) Array.newInstance(clazz, size) : list.toArray((T[]) Array.newInstance(clazz, size));
        return arr;
    }

    public static <T> T[] asArray(Class<T> clazz, T... objs) {
        T[] arr = null;

        if (objs != null && objs.length > 0) {
            arr = (T[]) Array.newInstance(clazz, objs.length);
        }

        return arr;
    }

    public static <T> List<T> asList(T... objs) {
        return Arrays.asList(objs);
    }

    public static <T> List intersect(List<? extends T> list1, List<? extends T> list2) {
        List list = new ArrayList();
        list.addAll(new HashSet(list1));
        list.retainAll(new HashSet<>(list2));
        return list;
    }

    public static <T> List union(List<? extends T> list1, List<? extends T> list2) {
        List list = new ArrayList();
        list.addAll(new HashSet(list1));
        list.addAll(new HashSet<>(list2));
        return list;
    }

    public static <T> List diff(List<? extends T> list1, List<? extends T> list2) {
        List list = new ArrayList();
        list.addAll(new HashSet(list1));
        list.removeAll(new HashSet<>(list2));
        return list;
    }

    public static <T, U> List<U> streamConvertToList(List<? extends T> list, Function<? super T, ? extends U> iConvert) {
        if (isNullOrEmpty(list)) {
            return Collections.EMPTY_LIST;
        }

        List<U> returnList = new ArrayList<>();

        if (!isNullOrEmpty(list)) {
            returnList = list.stream().map(iConvert).collect(Collectors.toList());
        }

        return returnList;
    }

    public static <T, U> List<U> streamFlatMapConvertToList(List<? extends T> list, Function<? super T, List<? extends U>> iConvert) {
        if (isNullOrEmpty(list)) {
            return Collections.EMPTY_LIST;
        }

        List<U> returnList = new ArrayList<>();

        if (!isNullOrEmpty(list)) {
            Stream<List> uList = list.stream().map(iConvert);
            returnList = (List<U>) uList.flatMap(l -> {
                if (l != null) return l.stream();
                else return Collections.EMPTY_LIST.stream();
            }).collect(Collectors.toList());
        }

        return returnList;
    }

    public static <T, U> List<U> copyConvertToList(List<? extends T> list, Class<? extends U> clazz) {
        if (isNullOrEmpty(list)) {
            return Collections.EMPTY_LIST;
        }

        List<U> returnList = new ArrayList<>();

        if (!isNullOrEmpty(list)) {
            for (T t : list) {
                U u = null;

                try {
                    u = clazz.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                BeanUtils.copyProperties(t, u);
                returnList.add(u);
            }
        }

        return returnList;
    }

    // 集合去重
    public static <T> List<T> distinct(List<? extends T> list) {
        Set hashSet = new HashSet(list);
        List<T> newList = new ArrayList<>();
        newList.addAll(hashSet);
        return newList;
    }

    // 判断集合是否为空
    public static <T> boolean isNullOrEmpty(List<? extends T> list) {
        boolean flag = true;

        if (list != null && !list.isEmpty()) {
            flag = false;
        }

        return flag;
    }

    // 查找重复的数据集合
    public static <T> List<T> findRepeatData(List<? extends T> list) {
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
    public static <T> boolean isHasRepeatData(List<? extends T> list) {
        boolean flag = false;

        List<T> distinctList = distinct(list);

        if (list != null && distinctList != null && (list.size() > distinctList.size())) {
            flag = true;
        }

        return flag;
    }

    // 找出一个元素在集合中出现的次数
    public static <T> Integer findTimesInList(List<? extends T> list, T t) {
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

    // 去null，使用旧集合
    public static <T> List<T> removeNull(List<? extends T> oldList) {

        oldList.removeAll(Collections.singleton(null));
        return (List<T>) oldList;
    }

    public static <T> List<T> removeNullNewList(List<? extends T> oldList) {
        // 临时集合
        List<T> listTemp = new ArrayList();
        for (int i = 0; i < oldList.size(); i++) {
            // 保存不为空的元素
            if (oldList.get(i) != null) {
                listTemp.add(oldList.get(i));
            }
        }
        return listTemp;
    }
}
