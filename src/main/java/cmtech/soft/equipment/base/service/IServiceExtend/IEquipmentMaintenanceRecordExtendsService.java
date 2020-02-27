package cmtech.soft.equipment.base.service.IServiceExtend;

import cmtech.soft.equipment.base.entity.EquipmentMaintenanceRecord;
import cmtech.soft.equipment.base.service.IEquipmentMaintenanceRecordService;

public interface IEquipmentMaintenanceRecordExtendsService extends IEquipmentMaintenanceRecordService {
    /**
     * 插入或更新保养记录，如果是插入就同时更新对应的计划单的下次保养时间
     * @param entity 插入的实体
     * @return  返回实体在数据库的id
     */
    int saveOrUpdateWithIdReturnBackExtends(EquipmentMaintenanceRecord entity);
}
