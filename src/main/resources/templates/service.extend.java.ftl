package ${cfg.prefix}.complexBiz.service;

import ${cfg.prefix}.complexBiz.service.${superServiceClass};


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

}
</#if>
