package cmtech.soft.equipment.base.mapper;

import cmtech.soft.equipment.base.entity.SparepartBaseInfo;
import cmtech.soft.equipment.base.model.QueryModelSparepartBaseInfo;
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
 * 备件基础信息表 Mapper 接口
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-14
 */
public interface SparepartBaseInfoMapper extends BaseMapper<SparepartBaseInfo> {
// 如何写联表查询
//@Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code")
//public List<SparepartBaseInfo> GetTwoTableInfo();

// @Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code $'去掉这句话'{ew.customSqlSegment}")
// public List<SparepartBaseInfo> GetTwoTableInfoPage(IPage<SparepartBaseInfo> cityPage, @Param(Constants.WRAPPER) Wrapper<SparepartBaseInfo> wrapper);

    /**
    * 根据条件串表分页查询
    *
    * @param page  分页对象
    * @param condition 条件
    * @return list
    */
    public List<SparepartBaseInfo> getTablesByContition(IPage<SparepartBaseInfo> page, @Param("condition") QueryModelSparepartBaseInfo condition);

    /**
    * 根据条件串表不分页查询list
    *
    * @param condition 条件
    * @return list
    */
    public List<SparepartBaseInfo> getTablesByContition(@Param("condition") QueryModelSparepartBaseInfo condition);
}
