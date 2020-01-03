package cmtech.soft.equipment.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cmtech.soft.equipment.base.entity.EquipmentBaseInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import cmtech.soft.equipment.base.model.QueryModelEquipmentBaseInfo;

import java.util.List;

/**
 * <p>
 * 设备基础信息主表 服务类
 * </p>
 *
 * @author cmmes
 * @since 2020-01-03
 */
public interface IEquipmentBaseInfoService extends IService<EquipmentBaseInfo> {
     /**
     * 根据条件查询分页结果
     * @param condition 查询条件
     * @return 分页结果
     */
    IPage<EquipmentBaseInfo> getPageByContition(QueryModelEquipmentBaseInfo condition);

    /**
    * 插入数据库实体，并返回实体在数据库的id
    * @param entity 插入的实体
    * @return  返回实体在数据库的id
    */
    int saveOrUpdateWithIdReturnBack(EquipmentBaseInfo entity);

    /**
    * 无分页查询list
    * @param condition 无分页条件
    * @return list
    */
    List<EquipmentBaseInfo> getListByQueryModel(QueryModelEquipmentBaseInfo condition);
}
