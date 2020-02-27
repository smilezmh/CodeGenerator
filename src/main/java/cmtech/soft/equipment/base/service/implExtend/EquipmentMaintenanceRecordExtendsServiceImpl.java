package cmtech.soft.equipment.base.service.implExtend;

import cmtech.soft.equipment.base.entity.EquipmentMaintenancePlan;
import cmtech.soft.equipment.base.entity.EquipmentMaintenanceRecord;
import cmtech.soft.equipment.base.mapper.EquipmentMaintenancePlanMapper;
import cmtech.soft.equipment.base.service.IServiceExtend.IEquipmentMaintenanceRecordExtendsService;
import cmtech.soft.equipment.base.service.impl.EquipmentMaintenanceRecordServiceImpl;
import cmtech.soft.equipment.utils.ErrorReturn;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("maintenanceRecordExtends")
public class EquipmentMaintenanceRecordExtendsServiceImpl extends EquipmentMaintenanceRecordServiceImpl implements IEquipmentMaintenanceRecordExtendsService {

    @Autowired
    private EquipmentMaintenancePlanMapper planMapper;// 计划单号查询条件

    /**
     * 插入或更新保养记录，如果是插入就同时更新对应的计划单的下次保养时间
     *
     * @param entity 插入的实体
     * @return 返回实体在数据库的id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrUpdateWithIdReturnBackExtends(EquipmentMaintenanceRecord entity) {
        int id = 0;

        if (entity.getId() == null) {
            if (isCodeRepeat(entity)) { // 业务主键重复，保养单号不能重复
                return ErrorReturn.CodeRepete;
            }

            id = mapper.insert(entity);// 插入保养记录
            QueryWrapper<EquipmentMaintenancePlan> planWrapper = new QueryWrapper<EquipmentMaintenancePlan>();// 搜索条件
            planWrapper.lambda().eq(EquipmentMaintenancePlan::getCode, entity.getMaitenancePlanNo())
                    .eq(EquipmentMaintenancePlan::getIsDeleted, false);// 计划单号
            EquipmentMaintenancePlan maintenancePlanEntity = new EquipmentMaintenancePlan();
            // 找出计划单号的id
            maintenancePlanEntity = planMapper.selectOne(planWrapper);// 如果找出多个订单号会报错
            maintenancePlanEntity.setNextMaintenaceTime(entity.getNextMaintenanceTime());
            // 更新保养计划单
            planMapper.updateById(maintenancePlanEntity);
        } else if (entity.getId() > 0) {
            id = mapper.updateById(entity);
        }

        // 表示插入成功
        if (id > 0) {
            id = entity.getId();
        }

        return id;
    }
}
