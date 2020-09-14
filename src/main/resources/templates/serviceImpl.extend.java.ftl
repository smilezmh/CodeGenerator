package ${cfg.prefix}.complexBiz.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${cfg.prefix}.base.service.impl.${table.serviceImplName};
import ${cfg.prefix}.complexBiz.service.I${entity}ExtendService;

/**
 * ${table.comment!} 扩展服务实现类
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${entity}ExtendServiceImpl extends  ${table.serviceImplName}<${table.mapperName}, ${entity}> implements I${entity}ExtendService {

    /**
    * 必须要注入进来，才能调用顶层ServiceImpl<${entity}Mapper, ${entity}>的方法，
    * 因为mybatis plus获取的是直接父类的第一个参数的泛型来获取表信息的
    */
    @Autowired
    ${table.serviceName} baseService;


}
</#if>
