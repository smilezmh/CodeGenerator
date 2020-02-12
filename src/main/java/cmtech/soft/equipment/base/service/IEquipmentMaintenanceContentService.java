package cmtech.soft.equipment.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cmtech.soft.equipment.base.entity.EquipmentMaintenanceContent;
import com.baomidou.mybatisplus.extension.service.IService;
import cmtech.soft.equipment.base.model.QueryModelEquipmentMaintenanceContent;

import java.util.List;

/**
 * <p>
 * 设备保养内容 服务类
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-14
 */
public interface IEquipmentMaintenanceContentService extends IService<EquipmentMaintenanceContent> {
     /**
     * 根据条件查询分页结果
     * @param condition 查询条件
     * @return 分页结果
     */
    IPage<EquipmentMaintenanceContent> getPageByContition(QueryModelEquipmentMaintenanceContent condition);

    /**
    * 根据条件随机取出一条数据
    * @param condition 查询条件
    * @return 实体
    */
    EquipmentMaintenanceContent getRandomOneByQueryModel(QueryModelEquipmentMaintenanceContent condition);

    /**
    * 插入数据库实体，并返回实体在数据库的id
    * @param entity 插入的实体
    * @return  返回实体在数据库的id
    */
    int saveOrUpdateWithIdReturnBack(EquipmentMaintenanceContent entity);

    /**
    * 带有主键是否重复判断的批量插入方法
    *
    * @param entities 实体集合
    * @return 返回ErrorReturn.CodeRepete=-1就是主键重复，返回插入的数量
    */
    Integer insertBatchWithCodeRepeatCheck(List<EquipmentMaintenanceContent> entities);

    /**
    * 无分页查询list
    * @param condition 无分页条件
    * @return list
    */
    List<EquipmentMaintenanceContent> getListByQueryModel(QueryModelEquipmentMaintenanceContent condition);

    /**
    * 根据查询条件查询数据是否存在
    * @param condition 查询条件
    * @return 是或否
    */
    boolean isExistsByQueryModel(QueryModelEquipmentMaintenanceContent condition);

    /**
    * 根据更新条件更新了几条数据
    *
    * @param condition 更新条件
    * @return 更新了几条数据
    */
    Integer updateByQueryModel(QueryModelEquipmentMaintenanceContent condition);

    /**
    * 根据条件串表分页查询
    * @param condition 查询条件
    * @return 分页结果
    */
    IPage<EquipmentMaintenanceContent> getTablesPageByContition(QueryModelEquipmentMaintenanceContent condition);

    /**
    * 根据条件串表不分页查询list
    * @param condition 查询条件
    * @return 分页结果
    */
    public List<EquipmentMaintenanceContent> getTablesByContition(QueryModelEquipmentMaintenanceContent condition);
}
