package cmtech.soft.equipment.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cmtech.soft.equipment.base.entity.EquipmentSlaveInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import cmtech.soft.equipment.base.model.QueryModelEquipmentSlaveInfo;

import java.util.List;

/**
 * <p>
 * 设备基础信息从表 服务类
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-10
 */
public interface IEquipmentSlaveInfoService extends IService<EquipmentSlaveInfo> {
     /**
     * 根据条件查询分页结果
     * @param condition 查询条件
     * @return 分页结果
     */
    IPage<EquipmentSlaveInfo> getPageByContition(QueryModelEquipmentSlaveInfo condition);

    /**
    * 根据条件随机取出一条数据
    * @param condition 查询条件
    * @return 实体
    */
    EquipmentSlaveInfo getRandomOneByQueryModel(QueryModelEquipmentSlaveInfo condition);

    /**
    * 插入数据库实体，并返回实体在数据库的id
    * @param entity 插入的实体
    * @return  返回实体在数据库的id
    */
    int saveOrUpdateWithIdReturnBack(EquipmentSlaveInfo entity);

    /**
    * 带有主键是否重复判断的批量插入方法
    *
    * @param entities 实体集合
    * @return 返回ErrorReturn.CodeRepete=-1就是主键重复，返回插入的数量
    */
    Integer insertBatchWithCodeRepeatCheck(List<EquipmentSlaveInfo> entities);

    /**
    * 无分页查询list
    * @param condition 无分页条件
    * @return list
    */
    List<EquipmentSlaveInfo> getListByQueryModel(QueryModelEquipmentSlaveInfo condition);

    /**
    * 根据查询条件查询数据是否存在
    * @param condition 查询条件
    * @return 是或否
    */
    boolean isExistsByQueryModel(QueryModelEquipmentSlaveInfo condition);

    /**
    * 根据更新条件更新了几条数据
    *
    * @param condition 更新条件
    * @return 更新了几条数据
    */
    Integer updateByQueryModel(QueryModelEquipmentSlaveInfo condition);

    /**
    * 根据条件串表分页查询
    * @param condition 查询条件
    * @return 分页结果
    */
    IPage<EquipmentSlaveInfo> getTablesPageByContition(QueryModelEquipmentSlaveInfo condition);

    /**
    * 根据条件串表不分页查询list
    * @param condition 查询条件
    * @return 分页结果
    */
    public List<EquipmentSlaveInfo> getTablesByContition(QueryModelEquipmentSlaveInfo condition);
}
