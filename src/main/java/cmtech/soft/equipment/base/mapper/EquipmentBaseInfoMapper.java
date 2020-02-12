package cmtech.soft.equipment.base.mapper;

import cmtech.soft.equipment.base.entity.EquipmentBaseInfo;
import cmtech.soft.equipment.base.model.QueryModelEquipmentBaseInfo;
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
 * 设备基础信息主表 Mapper 接口
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-10
 */
public interface EquipmentBaseInfoMapper extends BaseMapper<EquipmentBaseInfo> {
// 如何写联表查询
//@Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code")
//public List<EquipmentBaseInfo> GetTwoTableInfo();

// @Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code $'去掉这句话'{ew.customSqlSegment}")
// public List<EquipmentBaseInfo> GetTwoTableInfoPage(IPage<EquipmentBaseInfo> cityPage, @Param(Constants.WRAPPER) Wrapper<EquipmentBaseInfo> wrapper);

    /**
    * 根据条件串表分页查询
    *
    * @param page  分页对象
    * @param condition 条件
    * @return list
    */
    public List<EquipmentBaseInfo> getTablesByContition(IPage<EquipmentBaseInfo> page, @Param("condition") QueryModelEquipmentBaseInfo condition);

    /**
    * 根据条件串表不分页查询list
    *
    * @param condition 条件
    * @return list
    */
    public List<EquipmentBaseInfo> getTablesByContition(@Param("condition") QueryModelEquipmentBaseInfo condition);
}
