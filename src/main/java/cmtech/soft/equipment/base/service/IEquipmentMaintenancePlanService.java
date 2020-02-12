package cmtech.soft.equipment.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cmtech.soft.equipment.base.entity.EquipmentMaintenancePlan;
import com.baomidou.mybatisplus.extension.service.IService;
import cmtech.soft.equipment.base.model.QueryModelEquipmentMaintenancePlan;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-14
 */
public interface IEquipmentMaintenancePlanService extends IService<EquipmentMaintenancePlan> {
     /**
     * 根据条件查询分页结果
     * @param condition 查询条件
     * @return 分页结果
     */
    IPage<EquipmentMaintenancePlan> getPageByContition(QueryModelEquipmentMaintenancePlan condition);

    /**
    * 根据条件随机取出一条数据
    * @param condition 查询条件
    * @return 实体
    */
    EquipmentMaintenancePlan getRandomOneByQueryModel(QueryModelEquipmentMaintenancePlan condition);

    /**
    * 插入数据库实体，并返回实体在数据库的id
    * @param entity 插入的实体
    * @return  返回实体在数据库的id
    */
    int saveOrUpdateWithIdReturnBack(EquipmentMaintenancePlan entity);

    /**
    * 带有主键是否重复判断的批量插入方法
    *
    * @param entities 实体集合
    * @return 返回ErrorReturn.CodeRepete=-1就是主键重复，返回插入的数量
    */
    Integer insertBatchWithCodeRepeatCheck(List<EquipmentMaintenancePlan> entities);

    /**
    * 无分页查询list
    * @param condition 无分页条件
    * @return list
    */
    List<EquipmentMaintenancePlan> getListByQueryModel(QueryModelEquipmentMaintenancePlan condition);

    /**
    * 根据查询条件查询数据是否存在
    * @param condition 查询条件
    * @return 是或否
    */
    boolean isExistsByQueryModel(QueryModelEquipmentMaintenancePlan condition);

    /**
    * 根据更新条件更新了几条数据
    *
    * @param condition 更新条件
    * @return 更新了几条数据
    */
    Integer updateByQueryModel(QueryModelEquipmentMaintenancePlan condition);

    /**
    * 根据条件串表分页查询
    * @param condition 查询条件
    * @return 分页结果
    */
    IPage<EquipmentMaintenancePlan> getTablesPageByContition(QueryModelEquipmentMaintenancePlan condition);

    /**
    * 根据条件串表不分页查询list
    * @param condition 查询条件
    * @return 分页结果
    */
    public List<EquipmentMaintenancePlan> getTablesByContition(QueryModelEquipmentMaintenancePlan condition);
}
