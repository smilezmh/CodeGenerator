package cmtech.soft.equipment.base.service.implExtend;

import cmtech.soft.equipment.base.entity.EquipmentBaseInfo;
import cmtech.soft.equipment.base.entity.EquipmentMaintenanceContentRelation;
import cmtech.soft.equipment.base.mapper.EquipmentBaseInfoMapper;
import cmtech.soft.equipment.base.model.QueryModelEquipmentMaintenanceContentRelation;
import cmtech.soft.equipment.base.service.IServiceExtend.IEquipmentMaintenanceContentRelationExtendService;
import cmtech.soft.equipment.base.service.impl.EquipmentMaintenanceContentRelationServiceImpl;
import cmtech.soft.equipment.utils.MyStrTool;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * EquipmentMaintenanceContentRelationServiceImpl 的拓展类
 */
@Service
public class EquipmentMaintenanceContentRelationExtendsServiceImpl extends EquipmentMaintenanceContentRelationServiceImpl implements IEquipmentMaintenanceContentRelationExtendService {

    @Autowired
    private EquipmentBaseInfoMapper equipmentBaseInfoMapper;

    /**
     * 根据设备编码查找设备保养信息（保养套餐和设备保养内容）
     * @param equipmentCode 设备编码
     * @param maintenanceType 保养类型编码
     * @return 设备保养实体
     */
    @Override
    public List<EquipmentMaintenanceContentRelation> getEntitiesByEquipmentCode(String equipmentCode, String maintenanceType){

        List<EquipmentMaintenanceContentRelation> entities=new ArrayList<EquipmentMaintenanceContentRelation>();

        if(MyStrTool.isNullOrEmpty(equipmentCode)||MyStrTool.isNullOrEmpty(maintenanceType)){
            return entities;
        }

        QueryModelEquipmentMaintenanceContentRelation condition=new QueryModelEquipmentMaintenanceContentRelation();
        condition.setEquipmentCode(equipmentCode);
        condition.setMaintenanceType(maintenanceType);
        QueryWrapper<EquipmentMaintenanceContentRelation> wrapper=new QueryWrapper<>();// 查询条件

        // 首先根据设备编码和保养类型找有没有针对设备维护的套餐
        if(!isExistsByQueryModel(condition)){// 如果没有针对设备维护的套餐
            // 首先根据设备编码找出设备类型编码,从设备基础信息接口找出设备类型
            QueryWrapper<EquipmentBaseInfo> baseInfoQueryWrapper=new QueryWrapper<EquipmentBaseInfo>();
            baseInfoQueryWrapper.eq("code",equipmentCode);
            baseInfoQueryWrapper.eq("is_deleted",false);
            EquipmentBaseInfo baseInfo= equipmentBaseInfoMapper.selectOne(baseInfoQueryWrapper);// 如果找到多个会报错

            if(baseInfo!=null&&!MyStrTool.isNullOrEmpty(baseInfo.getTypeCode())){// 根据设备类型和保养类型找套保养餐
                condition.setEquipmentTypeCode(baseInfo.getTypeCode());
                condition.setEquipmentCode("");
            }
        }

        entities= getListByQueryModel(condition);

        return entities;
    }
}
