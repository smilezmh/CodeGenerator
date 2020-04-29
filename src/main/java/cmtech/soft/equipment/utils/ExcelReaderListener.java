package cmtech.soft.equipment.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *  监控读到的数据，并插入数据库
 * @param <U> 数据库关联的实体类型
 * @param <T> 解析表格用（上传下载Excel）的实体类型
 */
public class ExcelReaderListener<U,T> extends AnalysisEventListener<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelReaderListener.class);
    List<U> list = new ArrayList<U>();

    private ExcelService<U> excelService;

    public ExcelReaderListener(ExcelService<U> service) {
        this.excelService = service;
    }

    /**
     * 解析每条数据
     * @param t 读到的每行数据转化成实体T
     * @param analysisContext analysisContext
     */
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(t));
        // 类型转化，T=>U
        list.add(JSON.parseObject(JSON.toJSONString(t).getBytes(),(Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 0)));
    }

    /**
     * 最后持久化到数据库
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        if(list==null||list.isEmpty()){
            return;
        }
        excelService.saveOrUpdateBatch(list,1000);
        LOGGER.info("存储数据库成功！");
    }
}
