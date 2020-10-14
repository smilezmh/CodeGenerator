package ${cfg.prefix}.complexBiz.service;

import ${cfg.prefix}.base.service.${table.serviceName};
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diboot.core.binding.RelationsBinder;

import ${cfg.prefix}.base.model.QueryModel${entity};
import ${cfg.prefix}.complexBiz.model.vo.${entity}Vo;
import ${cfg.prefix}.utils.commonUtil.GenericsUtils;

import java.util.List;

/**
 * <p>
 * ${table.comment!} 扩展服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface I${entity}ExtendService extends ${table.serviceName} {
    /**
     * 根据条件分页查询记录
     *
     * @param condition 查询条件
     * @return 分页记录
     */
    IPage<${entity}Vo> getPageByCondition(QueryModel${entity} condition);

    /**
     * 根据条件查询List
     *
     * @param condition 查询条件
     * @return List vo
     */
    List<${entity}Vo> getListByCondition(QueryModel${entity} condition);
}
</#if>
