package cmtech.soft.equipment.base.mapper;

import cmtech.soft.equipment.base.entity.EquipmentFaultCategory;
import cmtech.soft.equipment.base.model.QueryModelEquipmentFaultCategory;
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
 *  Mapper 接口
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-10
 */
public interface EquipmentFaultCategoryMapper extends BaseMapper<EquipmentFaultCategory> {
// 如何写联表查询
//@Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code")
//public List<EquipmentFaultCategory> GetTwoTableInfo();

// @Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code $'去掉这句话'{ew.customSqlSegment}")
// public List<EquipmentFaultCategory> GetTwoTableInfoPage(IPage<EquipmentFaultCategory> cityPage, @Param(Constants.WRAPPER) Wrapper<EquipmentFaultCategory> wrapper);

    /**
    * 根据条件串表分页查询
    *
    * @param page  分页对象
    * @param condition 条件
    * @return list
    */
    public List<EquipmentFaultCategory> getTablesByContition(IPage<EquipmentFaultCategory> page, @Param("condition") QueryModelEquipmentFaultCategory condition);

    /**
    * 根据条件串表不分页查询list
    *
    * @param condition 条件
    * @return list
    */
    public List<EquipmentFaultCategory> getTablesByContition(@Param("condition") QueryModelEquipmentFaultCategory condition);
}
