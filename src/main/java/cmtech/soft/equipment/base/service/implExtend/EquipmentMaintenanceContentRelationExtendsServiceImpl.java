package cmtech.soft.equipment.base.implExtend;

import cmtech.soft.equipment.base.entity.EquipmentBaseInfo;
import cmtech.soft.equipment.base.entity.EquipmentMaintenanceContentRelation;
import cmtech.soft.equipment.base.mapper.EquipmentBaseInfoMapper;
import cmtech.soft.equipment.base.model.QueryModelEquipmentMaintenanceContentRelation;
import cmtech.soft.equipment.base.service.IServiceExtend.IEquipmentMaintenanceContentRelationExtendsService;
import cmtech.soft.equipment.base.service.impl.EquipmentMaintenanceContentRelationServiceImpl;
import cmtech.soft.equipment.utils.MyStrTool;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bestvike.linq.Linq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * EquipmentMaintenanceContentRelationServiceImpl 的拓展类
 */
@Service("extends")
public class EquipmentMaintenanceContentRelationExtendsServiceImpl extends EquipmentMaintenanceContentRelationServiceImpl implements IEquipmentMaintenanceContentRelationExtendsService {

    @Autowired
    private EquipmentBaseInfoMapper equipmentBaseInfoMapper;

    @Autowired
    private EquipmentMaintenanceContentRelationServiceImpl service;

    /**
     * 根据设备编码查找设备保养信息（保养套餐和设备保养内容）
     *
     * @param equipmentCode   设备编码
     * @param maintenanceType 保养类型编码
     * @return 设备保养实体
     */
    @Override
    public List<EquipmentMaintenanceContentRelation> getEntitiesByEquipmentCode(String equipmentCode, String maintenanceType) {

        List<EquipmentMaintenanceContentRelation> entities = new ArrayList<EquipmentMaintenanceContentRelation>();

        if (MyStrTool.isNullOrEmpty(equipmentCode) || MyStrTool.isNullOrEmpty(maintenanceType)) {
            return entities;
        }

        QueryModelEquipmentMaintenanceContentRelation condition = new QueryModelEquipmentMaintenanceContentRelation();
        condition.setEquipmentCode(equipmentCode);
        condition.setMaintenanceType(maintenanceType);
        condition.setIsDeleted(false);
        QueryWrapper<EquipmentMaintenanceContentRelation> wrapper = new QueryWrapper<>();// 查询条件

        // 首先根据设备编码和保养类型找有没有针对设备维护的套餐
        if (!isExistsByQueryModel(condition)) {// 如果没有针对设备维护的套餐
            // 首先根据设备编码找出设备类型编码,从设备基础信息接口找出设备类型
            QueryWrapper<EquipmentBaseInfo> baseInfoQueryWrapper = new QueryWrapper<EquipmentBaseInfo>();
            baseInfoQueryWrapper.eq("code", equipmentCode);
            baseInfoQueryWrapper.eq("is_deleted", false);
            EquipmentBaseInfo baseInfo = equipmentBaseInfoMapper.selectOne(baseInfoQueryWrapper);// 如果找到多个会报错

            if (baseInfo != null && !MyStrTool.isNullOrEmpty(baseInfo.getTypeCode())) {// 根据设备类型和保养类型找套保养餐
                condition.setEquipmentTypeCode(baseInfo.getTypeCode());
                condition.setEquipmentCode("");
            }
        }

        entities = getListByQueryModel(condition);

        return entities;
    }

    /**
     * 批量更新套餐信息
     * @param contentRelations 新的套餐数据
     * @return 更新是否成功
     */
    @Override
    public boolean updataBatchWithCodeRepeate(List<EquipmentMaintenanceContentRelation> contentRelations) {
        if(contentRelations==null||MyStrTool.isNullOrEmpty(contentRelations.get(0).getPackageCode())){
                return false;
        }
        // 先找到套餐信息
        String packageCode = contentRelations.get(0).getPackageCode();// 要插入的套餐编码
        // 找到套餐编码下的信息
        QueryModelEquipmentMaintenanceContentRelation condition = new QueryModelEquipmentMaintenanceContentRelation();
        condition.setPackageCode(packageCode);
        List<EquipmentMaintenanceContentRelation> oldRelations = getListByQueryModel(condition);

        if (oldRelations == null || oldRelations.isEmpty()) {// 如果编辑的是套餐编码，那么套餐编码找不出信息
            return insertBatchWithCodeRepeatCheck(contentRelations) > 0;// 插入信息
        } else {// 已有的套餐编码（前端套餐编码原则不允许改变）
            // 原来设备类型编码
            String oldEquipmentTypeCode = oldRelations.get(0).getEquipmentTypeCode();
            // 原来保养类型编码
            String oldMaintenanceTypeCode = oldRelations.get(0).getMaintenanceType();
            // 原来设备编码
            String oldEquipmentCode = oldRelations.get(0).getEquipmentCode();
            // 要插入的新的设备类型编码
            String newEquipmentTypeCode = contentRelations.get(0).getEquipmentTypeCode();
            // 要插入的新的保养类型编码
            String newMaintenanceTypeCode = contentRelations.get(0).getMaintenanceType();
            // 要插入的新的设备编码
            String newEquipmentCode = contentRelations.get(0).getEquipmentCode();

            // 不能和除原来套餐编码数据重复
            // 如果编辑了 设备类型编码  保养类型编码 设备编码
            if (!oldEquipmentTypeCode.equals(newEquipmentTypeCode) || !oldMaintenanceTypeCode.equals(newMaintenanceTypeCode) || !oldEquipmentCode.equals(newEquipmentCode)) {
                if (isTypeRepeate(contentRelations)) {
                    return false;
                }
            }

            List<Integer> ids = Linq.of(oldRelations).select(x -> x.getId()).toList();// 原来数据的id

            if (oldRelations.size() > contentRelations.size()) {// 原来的数据多，新的数据全部更新，原来数据删除
                for (int i = 0; i < contentRelations.size(); i++) {
                    contentRelations.get(i).setId(ids.get(i));
                }

                List<EquipmentMaintenanceContentRelation> deleteData=new ArrayList<EquipmentMaintenanceContentRelation>();
                // 最后几条插入
                for(int i=0;i<(ids.size()-contentRelations.size());i++){
                    EquipmentMaintenanceContentRelation deleteEntity=new EquipmentMaintenanceContentRelation();
                    deleteEntity.setId(ids.get(ids.size()-1-i)).setIsDeleted(true);
                    deleteData.add(deleteEntity);
                }

                contentRelations.addAll(deleteData);
            }else {
                for(int i=0;i<ids.size();i++){
                    contentRelations.get(i).setId(ids.get(i));
                }
            }

            return service.saveOrUpdateBatch(contentRelations);
        }
    }
}
