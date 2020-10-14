package ${package.Service};

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${cfg.prefix}.base.model.excelModel.${entity}Excel;
import ${package.Entity}.${entity};
import ${cfg.prefix}.utils.model.TreeData;
import ${superServiceClassPackage};
import ${cfg.prefix}.base.model.QueryModel${entity};

import java.util.List;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {
     /**
     * 根据条件查询分页结果
     * @param condition 查询条件
     * @return 分页结果
     */
    IPage<${entity}> getPageByContition(QueryModel${entity} condition);

    /**
    * 根据条件随机取出一条数据
    * @param condition 查询条件
    * @return 实体
    */
    ${entity} getRandomOneByQueryModel(QueryModel${entity} condition);

    /**
    * 插入数据库实体，并返回实体在数据库的id
    * @param entity 插入的实体
    * @return  返回实体在数据库的id
    */
    int saveOrUpdateWithIdReturnBack(${entity} entity);

    /**
    * 插入数据库实体，并返回实体在数据库的entity
    * @param entity 插入的实体
    * @return  返回实体在数据库的entity
    */
    ${entity} saveOrUpdateWithEntityReturnBack(${entity} entity);

    /**
    * 带有主键是否重复判断的批量插入方法
    *
    * @param entities 实体集合
    * @return 返回ErrorReturn.CodeRepete=-1就是主键重复，返回插入的数量
    */
    Integer insertBatchWithCodeRepeatCheck(List<${entity}> entities);

    /**
    * 无分页查询list
    * @param condition 无分页条件
    * @return list
    */
    List<${entity}> getListByQueryModel(QueryModel${entity} condition);

    /**
    * 根据查询条件查询数据是否存在
    * @param condition 查询条件
    * @return 是或否
    */
    boolean isExistsByQueryModel(QueryModel${entity} condition);

    /**
    * 根据更新条件更新了几条数据
    *
    * @param condition 更新条件
    * @return 更新了几条数据
    */
    Integer updateByQueryModel(${entity} entity, QueryModel${entity} condition);

    /**
    * 根据条件串表分页查询
    * @param condition 查询条件
    * @return 分页结果
    */
    IPage<${entity}> getTablesPageByContition(QueryModel${entity} condition);

    /**
    * 根据条件串表不分页查询list
    * @param condition 查询条件
    * @return 分页结果
    */
    public List<${entity}> getTablesByContition(QueryModel${entity} condition);

     /**
     * 根据条件获得导出excel数据
     * @param condition 查询条件
     * @return excel数据
     */
    List<${entity}Excel> getExcelListByQueryModel(QueryModel${entity} condition);

    /**
     * 生成单号
     * @param prefix  前缀
     * @param colName 检索的数据列名
     * @param noLength 最后位数的长度
     * @param isRelatedToDate 是否和日期相关
     * @return 单号
     */
     String generateNo(String prefix,String colName,Integer noLength,Boolean isRelatedToDate);

    /**
     * 有子级关系的根据条件查询
     * @param condition 查询条件
     * @return 有子级关系的查询结果
     */
     TreeData<${entity}> getListHasChildrenByContition(QueryModel${entity} condition);

    /**
     * 根据id获取下一级子级数据
     * @param id 实体id
     * @return 获取下一级子级数据
     */
     List<${entity}> getSubList(Integer id);
}
</#if>
