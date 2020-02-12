package cmtech.soft.equipment.base.mapper;

import cmtech.soft.equipment.base.entity.EquipmentMaintenanceContent;
import cmtech.soft.equipment.base.model.QueryModelEquipmentMaintenanceContent;
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
 * 设备保养内容 Mapper 接口
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-14
 */
public interface EquipmentMaintenanceContentMapper extends BaseMapper<EquipmentMaintenanceContent> {
// 如何写联表查询
//@Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code")
//public List<EquipmentMaintenanceContent> GetTwoTableInfo();

// @Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code $'去掉这句话'{ew.customSqlSegment}")
// public List<EquipmentMaintenanceContent> GetTwoTableInfoPage(IPage<EquipmentMaintenanceContent> cityPage, @Param(Constants.WRAPPER) Wrapper<EquipmentMaintenanceContent> wrapper);

    /**
    * 根据条件串表分页查询
    *
    * @param page  分页对象
    * @param condition 条件
    * @return list
    */
    public List<EquipmentMaintenanceContent> getTablesByContition(IPage<EquipmentMaintenanceContent> page, @Param("condition") QueryModelEquipmentMaintenanceContent condition);

    /**
    * 根据条件串表不分页查询list
    *
    * @param condition 条件
    * @return list
    */
    public List<EquipmentMaintenanceContent> getTablesByContition(@Param("condition") QueryModelEquipmentMaintenanceContent condition);
}
