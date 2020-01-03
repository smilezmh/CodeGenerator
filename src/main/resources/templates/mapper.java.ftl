package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};

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
//@Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code")
//public List<City> GetTwoTableInfo();

// @Select("SELECT id,city.Name ,countrycode,country.Name as countryName FROM city LEFT JOIN country  ON city.CountryCode=country.Code  ${ew.customSqlSegment}")
// public List<City> GetTwoTableInfoPage(Page<City> cityPage, @Param(Constants.WRAPPER) Wrapper<City> wrapper);
}
</#if>
