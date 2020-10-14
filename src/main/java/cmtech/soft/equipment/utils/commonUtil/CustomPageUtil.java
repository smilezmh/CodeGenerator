package cmtech.soft.equipment.utils.commonUtil;

import cmtech.soft.biz.utils.commonUtil.listUtil.MyListUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public class CustomPageUtil<T> {
    /**
     * 设置分页对象
     *
     * @param list    集合
     * @param page    分页对象
     * @param current 当前第几页
     * @param size    每页大小
     * @param <T>     泛型
     */
    public static <T> void setPage(List<T> list, IPage<T> page, int current, int size) {
        int total = 0;
        int pages = 0;

        if (!MyListUtil.isNullOrEmpty(list)) {
            pages = (int) Math.ceil((double) list.size() / size);
            total = list.size();
        }

        page.setTotal(total);
        page.setCurrent(current);
        page.setSize(size);
        page.setPages(pages);

        if (current >= 1) {
            int startIndex = (current - 1) * size;
            int endIndex = 0;

            if (startIndex + size < total) {// 如果不是最后一页
                endIndex = startIndex + size;
            } else {
                endIndex = total;
            }

            page.setRecords(list.subList((current - 1) * size, endIndex));
        }
    }
}
