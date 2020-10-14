package ${cfg.prefix}.complexBiz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;

import ${cfg.prefix}.complexBiz.service.I${entity}ExtendService;
import ${cfg.prefix}.utils.HttpResultT;
import ${cfg.prefix}.utils.commonUtil.V;
import ${cfg.prefix}.complexBiz.model.vo.${entity}Vo;
import ${cfg.prefix}.base.model.QueryModel${entity};

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

import java.util.List;
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

    @ApiOperation("分页查询巡检记录")
    @PostMapping("getPageByCondition")
    HttpResultT<IPage<${entity}Vo>> getPageByCondition(@RequestBody QueryModel${entity} condition) {
        HttpResultT<IPage<${entity}Vo>> httpResultT = new HttpResultT<>();
        IPage<${entity}Vo> pages = extendService.getPageByCondition(condition);

        if (V.notEmpty(pages.getRecords())) {
            httpResultT.setData(pages);
        } else {
            httpResultT.empty();
        }
        return httpResultT;
    }

    @ApiOperation("分页查询巡检记录")
    @PostMapping("getListByCondition")
    HttpResultT<List<${entity}Vo>> getListByCondition(@RequestBody QueryModel${entity} condition) {
        HttpResultT<List<${entity}Vo>> httpResultT = new HttpResultT<>();
        List<${entity}Vo> list = extendService.getListByCondition(condition);

        if (V.notEmpty(list)) {
            httpResultT.setData(list);
        } else {
            httpResultT.empty();
        }
        return httpResultT;
    }
}
</#if>
