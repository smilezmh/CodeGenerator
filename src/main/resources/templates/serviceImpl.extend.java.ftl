package ${cfg.prefix}.complexBiz.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diboot.core.binding.RelationsBinder;

import ${cfg.prefix}.base.model.QueryModel${entity};
import ${cfg.prefix}.complexBiz.model.vo.${entity}Vo;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${cfg.prefix}.base.service.impl.${table.serviceImplName};
import ${cfg.prefix}.complexBiz.service.I${entity}ExtendService;

import java.util.List;

/**
 * ${table.comment!} 扩展服务实现类
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${entity}ExtendServiceImpl extends  ${table.serviceImplName}<${table.mapperName}, ${entity}> implements I${entity}ExtendService {

   /**
     * 根据条件分页查询记录
     *
     * @param condition 查询条件
     * @return 分页记录
     */
    @Override
    public IPage<${entity}Vo> getPageByCondition(QueryModel${entity} condition) {
        IPage<${entity}Vo> returnPages = new Page<>();
        IPage<${entity}> pages = getPageByContition(condition);
        GenericsUtils.copyProperties(pages, returnPages);
        returnPages.setRecords(RelationsBinder.convertAndBind(returnPages.getRecords(), ${entity}Vo.class));
        return returnPages;
    }

    /**
     * 根据条件查询List
     *
     * @param condition 查询条件
     * @return List vo
     */
    public List<${entity}Vo> getListByCondition(QueryModel${entity} condition) {
        List<${entity}> list = getListByQueryModel(condition);
        List<${entity}Vo> returnList = RelationsBinder.convertAndBind(list, ${entity}Vo.class);
        return returnList;
    }
}
</#if>
