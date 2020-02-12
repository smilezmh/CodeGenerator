package cmtech.soft.equipment.base.mapper;

import cmtech.soft.equipment.base.entity.SparepartType;
import cmtech.soft.equipment.base.model.QueryModelSparepartType;
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
 * 备件类型表 Mapper 接口
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-14
 */
public interface SparepartTypeMapper extends BaseMapper<SparepartType> {
// 如何写联表查询
//@Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code")
//public List<SparepartType> GetTwoTableInfo();

// @Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code $'去掉这句话'{ew.customSqlSegment}")
// public List<SparepartType> GetTwoTableInfoPage(IPage<SparepartType> cityPage, @Param(Constants.WRAPPER) Wrapper<SparepartType> wrapper);

    /**
    * 根据条件串表分页查询
    *
    * @param page  分页对象
    * @param condition 条件
    * @return list
    */
    public List<SparepartType> getTablesByContition(IPage<SparepartType> page, @Param("condition") QueryModelSparepartType condition);

    /**
    * 根据条件串表不分页查询list
    *
    * @param condition 条件
    * @return list
    */
    public List<SparepartType> getTablesByContition(@Param("condition") QueryModelSparepartType condition);
}
