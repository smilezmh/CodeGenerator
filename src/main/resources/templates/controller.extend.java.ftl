package ${cfg.prefix}.complexBiz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;

import ${cfg.prefix}.complexBiz.service.I${entity}ExtendService;


<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * ${table.comment!} 扩展接口
 *
 * @author ${author}
 * @since ${date}
 */
@Api(tags = "${table.comment!} 扩展接口")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("extend/${table.name}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${entity}ExtendController {
</#if>
    @Autowired
    private I${entity}ExtendService extendService;


}
</#if>
