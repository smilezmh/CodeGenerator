package ${package.Mapper};

import ${package.Entity}.${entity};
import ${cfg.prefix}.base.model.QueryModel${entity};
import ${superMapperClassPackage};
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {
// 如何写联表查询
//@Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code")
//public List<${entity}> GetTwoTableInfo();

// @Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code $'去掉这句话'{ew.customSqlSegment}")
// public List<${entity}> GetTwoTableInfoPage(IPage<${entity}> cityPage, @Param(Constants.WRAPPER) Wrapper<${entity}> wrapper);

    /**
    * 根据条件串表分页查询
    *
    * @param page  分页对象
    * @param condition 条件
    * @return list
    */
    public List<${entity}> getTablesByContition(IPage<${entity}> page, @Param("condition") QueryModel${entity} condition);

    /**
    * 根据条件串表不分页查询list
    *
    * @param condition 条件
    * @return list
    */
    public List<${entity}> getTablesByContition(@Param("condition") QueryModel${entity} condition);
}
</#if>
