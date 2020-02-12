package cmtech.soft.equipment.base.mapper;

import cmtech.soft.equipment.base.entity.EquipmentRepairRecord;
import cmtech.soft.equipment.base.model.QueryModelEquipmentRepairRecord;
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
 * 设备维修记录表 Mapper 接口
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-10
 */
public interface EquipmentRepairRecordMapper extends BaseMapper<EquipmentRepairRecord> {
// 如何写联表查询
//@Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code")
//public List<EquipmentRepairRecord> GetTwoTableInfo();

// @Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code $'去掉这句话'{ew.customSqlSegment}")
// public List<EquipmentRepairRecord> GetTwoTableInfoPage(IPage<EquipmentRepairRecord> cityPage, @Param(Constants.WRAPPER) Wrapper<EquipmentRepairRecord> wrapper);

    /**
    * 根据条件串表分页查询
    *
    * @param page  分页对象
    * @param condition 条件
    * @return list
    */
    public List<EquipmentRepairRecord> getTablesByContition(IPage<EquipmentRepairRecord> page, @Param("condition") QueryModelEquipmentRepairRecord condition);

    /**
    * 根据条件串表不分页查询list
    *
    * @param condition 条件
    * @return list
    */
    public List<EquipmentRepairRecord> getTablesByContition(@Param("condition") QueryModelEquipmentRepairRecord condition);
}
