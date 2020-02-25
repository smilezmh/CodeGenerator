package cmtech.soft.equipment.base.service.IServiceExtend;

import cmtech.soft.equipment.base.entity.EquipmentMaintenanceContentRelation;
import cmtech.soft.equipment.base.service.IEquipmentMaintenanceContentRelationService;

import java.util.List;

public interface IEquipmentMaintenanceContentRelationExtendService extends IEquipmentMaintenanceContentRelationService  {
    /**
     * 根据设备编码查找设备保养信息（保养套餐和设备保养内容）
     * @param equipmentCode 设备编码
     * @return 设备保养实体
     */
    List<EquipmentMaintenanceContentRelation> getEntitiesByEquipmentCode(String equipmentCode, String maintenanceType);
}
