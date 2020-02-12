package cmtech.soft.equipment.base.mapper;

import cmtech.soft.equipment.base.entity.EquipmentMaintenanceContentRelation;
import cmtech.soft.equipment.base.model.QueryModelEquipmentMaintenanceContentRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 设备保养内容和设备保养类型、设备类型关系维护表 Mapper 接口
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-14
 */
public interface EquipmentMaintenanceContentRelationMapper extends BaseMapper<EquipmentMaintenanceContentRelation> {
// 如何写联表查询
//@Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code")
//public List<EquipmentMaintenanceContentRelation> GetTwoTableInfo();

// @Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code $'去掉这句话'{ew.customSqlSegment}")
// public List<EquipmentMaintenanceContentRelation> GetTwoTableInfoPage(IPage<EquipmentMaintenanceContentRelation> cityPage, @Param(Constants.WRAPPER) Wrapper<EquipmentMaintenanceContentRelation> wrapper);

    /**
    * 根据条件串表分页查询
    *
    * @param page  分页对象
    * @param condition 条件
    * @return list
    */
    public List<EquipmentMaintenanceContentRelation> getTablesByContition(IPage<EquipmentMaintenanceContentRelation> page, @Param("condition") QueryModelEquipmentMaintenanceContentRelation condition);

    /**
    * 根据条件串表不分页查询list
    *
    * @param condition 条件
    * @return list
    */
    public List<EquipmentMaintenanceContentRelation> getTablesByContition(@Param("condition") QueryModelEquipmentMaintenanceContentRelation condition);
}
