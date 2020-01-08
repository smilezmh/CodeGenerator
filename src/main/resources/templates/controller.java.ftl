package ${package.Controller};

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;

import java.util.List;
import ${package.Service}.${table.serviceName};
import ${cfg.prefix}.base.model.QueryModel${entity};
import ${package.Entity}.${entity};
import ${cfg.prefix}.utils.MyStrTool;

import ${cfg.prefix}.utils.HttpResult; // 自定义返回结果
import ${cfg.prefix}.utils.Aop.InterceptAction; // 自定义拦截aop
import ${cfg.prefix}.utils.HttpStatus; // 自定义状态返回
import cmtech.soft.equipment.utils.ErrorReturn;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * ${table.comment!} 基本接口
 *
 * @author ${author}
 * @since ${date}
 */
@Api(tags = "${table.comment!} 基本接口")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    @Autowired
    private ${table.serviceName} service;

    @ApiOperation("批量修改或插入")
    @PostMapping(value = "saveOrUpdataBath")
    @InterceptAction("批量修改或插入")
    public HttpResult saveOrUpdataBath(@RequestBody(required = true) List<${entity}> entities)  {
        HttpResult result=new HttpResult();

        if (entities == null) {
            return result.error(HttpStatus.SC_BAD_REQUEST, "参数不能为空");
        }

        boolean flag = service.saveOrUpdateBatch(entities);

        if (flag) {
            result.setCode(HttpStatus.SC_OK);
            result.setMsg("批量修改或插入成功！");
            result.setData(true);
        } else {
            // 失败的结果
            result.setCode(HttpStatus.SC_ACCEPTED);
            result.setMsg("批量修改或插入失败！");
            result.setData(false);
        }
        return result;
    }

    @ApiOperation("批量插入带有业务主键判断是否重复判断")
    @PostMapping(value = "insertBatchWithCodeRepeatCheck")
    @InterceptAction("批量插入带有业务主键判断是否重复判断")
    public HttpResult insertBatchWithCodeRepeatCheck(@RequestBody(required = true) List<${entity}> entities) {
        HttpResult result = new HttpResult();

        if (entities == null) {
            return result.error(HttpStatus.SC_BAD_REQUEST, "参数不能为空");
        }

        Integer num = service.insertBatchWithCodeRepeatCheck(entities);

        if (num > 0) {
            result.setCode(HttpStatus.SC_OK);
            result.setMsg("批量插入成功！");
            result.setData(true);
        } else if (num == ErrorReturn.CodeRepete) {
            // 失败的结果
            result.setCode(HttpStatus.SC_RESET_CONTENT);
            result.setMsg("主键重复！");
            result.setData(false);
        } else {
            // 失败的结果
            result.setCode(HttpStatus.SC_NO_CONTENT);
            result.setMsg("批量插入失败！");
            result.setData(false);
        }

        return result;
    }

    @ApiOperation("根据条件分页查询信息")
    @PostMapping(value = "getPageByContition")
    public HttpResult getPageByContition(@RequestBody(required = false) QueryModel${entity} condition) {
        HttpResult result = new HttpResult();
        IPage<${entity}> page = service.getPageByContition(condition);

        if (page != null && page.getRecords() != null && page.getRecords().size() > 0) {
            result.setCode(HttpStatus.SC_OK);
            result.setData(page);
            result.setMsg("根据条件分页查询信息成功！");
        } else {
            // 204 No Content
            result.setCode(HttpStatus.SC_NO_CONTENT);
            result.setData(page);
            result.setMsg("根据条件分页查询信息为空！");
        }
        return result;
    }

    @ApiOperation("根据条件无分页查询list信息")
    @PostMapping(value = "getListByQueryModel")
    public HttpResult getListByQueryModel(@RequestBody(required = false) QueryModel${entity} condition) {
        HttpResult result = new HttpResult();
        List<${entity}> list=service.getListByQueryModel(condition);

        if (list != null && list.size() > 0) {
            result.setCode(HttpStatus.SC_OK);
            result.setData(list);
            result.setMsg("根据条件无分页查询list信息成功！");
        } else {
            // 204 No Content
            result.setCode(HttpStatus.SC_NO_CONTENT);
            result.setData(list);
            result.setMsg("根据条件无分页查询list信息为空！");
        }
        return result;
    }

    @ApiOperation("根据条件查询list第一个实体信息")
    @PostMapping(value = "getFirstOneByQueryModel")
    public HttpResult getFirstOneByQueryModel(@RequestBody(required = false) QueryModel${entity} condition) {
    HttpResult result = new HttpResult();
        List<${entity}> list=service.getListByQueryModel(condition);

        if (list != null && list.size() > 0) {
            result.setCode(HttpStatus.SC_OK);
            result.setData(list.get(0));
            result.setMsg("根据条件查询list第一个实体信息成功！");
        } else {
            // 204 No Content
            result.setCode(HttpStatus.SC_NO_CONTENT);
            result.setData(null);
            result.setMsg("根据条件查询list第一个实体信息为空！");
        }
        return result;
    }

    @ApiOperation("根据条件查询list任意一个实体信息")
    @PostMapping(value = "getRandomOneByQueryModel")
    public HttpResult getRandomOneByQueryModel(@RequestBody(required = false) QueryModel${entity} condition) {
        HttpResult result = new HttpResult();
        ${entity} entity=service.getRandomOneByQueryModel(condition);

        if (entity != null) {
            result.setCode(HttpStatus.SC_OK);
            result.setData(entity);
            result.setMsg("根据条件查询list任意一个实体信息成功！");
        } else {
            // 204 No Content
            result.setCode(HttpStatus.SC_NO_CONTENT);
            result.setData(null);
            result.setMsg("根据条件查询list任意一个实体信息为空！");
        }

        return result;
    }

    @ApiOperation("根据业务主键code查询单条数据")
    @GetMapping(value = "getEntityByCode")
    public HttpResult getEntityByCode(@RequestParam(defaultValue = "") String code) {
        HttpResult result = new HttpResult();

        if (MyStrTool.isNullOrEmpty(code)) {
            return result.error(HttpStatus.SC_BAD_REQUEST, "参数不能为空");
        }

        QueryWrapper<${entity}> queryWrapper=new QueryWrapper<${entity}>();

        // 查询需要的结果列
        // queryWrapper.select("id", "code", "name", "remark");
        // 需要取消注释
        // queryWrapper.eq("code", no);
        // 是否删除
        queryWrapper.eq("is_deleted", false);
        // 如果是多条随机取出一条，不会报异常
        queryWrapper.last("LIMIT 1");
        ${entity} entity = service.getOne(queryWrapper);

        if (entity != null) {
            result.setCode(HttpStatus.SC_OK);
            result.setData(entity);
            result.setMsg("根据业务主键code查询单条数据成功！");
        } else {
            // 204 No Content
            result.setCode(HttpStatus.SC_NO_CONTENT);
            result.setData(entity);
            result.setMsg("根据业务主键code查询单条数据成功！");
        }

        return result;
    }

    @ApiOperation("根据主键id查询单条数据")
    @GetMapping(value = "getEntityById")
    public HttpResult getEntityById(@RequestParam(required = true) Integer id) {
        HttpResult result = new HttpResult();

        if (id == null) {
            return result.error(HttpStatus.SC_BAD_REQUEST, "参数不能为空");
        }

        QueryWrapper<${entity}> queryWrapper=new QueryWrapper<${entity}>();

        // 查询需要的结果列
        // queryWrapper.select("id", "code", "name", "remark");
        queryWrapper.eq("id", id);
        // 是否删除
        queryWrapper.eq("is_deleted", false);
        // 如果是多条随机取出一条，不会报异常
        queryWrapper.last("LIMIT 1");
        ${entity} entity = service.getOne(queryWrapper);

        if (entity != null) {
            result.setCode(HttpStatus.SC_OK);
            result.setData(entity);
            result.setMsg("根据主键id查询单条数据成功！");
        } else {
            // 204 No Content
            result.setCode(HttpStatus.SC_NO_CONTENT);
            result.setData(entity);
            result.setMsg("根据主键id查询单条数据为空！");
        }

        return result;
    }

    @ApiOperation("带id返回值的插入或者更新方法")
    @PostMapping(value = "saveOrUpdateWithIdReturnBack")
    @InterceptAction("带id返回值的插入或者更新方法")
    public HttpResult saveOrUpdateWithIdReturnBack(@RequestBody(required = true) ${entity} entity) {
        HttpResult result = new HttpResult();

        if (entity == null) {
            return HttpResult.error(HttpStatus.SC_BAD_REQUEST, "请求参数不能为空");
        }

        int id=service.saveOrUpdateWithIdReturnBack(entity);

        if (id>0) {
            result.setData(id);
            result.setMsg("带id返回值的插入或者更新成功！");
            result.setCode(HttpStatus.SC_OK);
        } else if(id == ErrorReturn.CodeRepete){
            result.setData(ErrorReturn.CodeRepete);
            result.setMsg("主键重复！");
            // 205 SC_RESET_CONTENT
            result.setCode(HttpStatus.SC_RESET_CONTENT);
        }else {
            result.setData(0);
            result.setMsg("带id返回值的插入或者更新方法失败！");
            // 204 No Content
            result.setCode(HttpStatus.SC_NO_CONTENT);
        }

        return result;
    }

    @ApiOperation("根据查询条件查询数据是否存在")
    @PostMapping(value = "isExistsByQueryModel")
    @InterceptAction("根据查询条件查询数据是否存在")
    public HttpResult isExistsByQueryModel(@RequestBody(required = true) QueryModel${entity} condition) {
        HttpResult result = new HttpResult();

        if (condition == null) {
        return HttpResult.error(HttpStatus.SC_BAD_REQUEST, "请求参数不能为空");
        }

        boolean flag=service.isExistsByQueryModel(condition);

        result.setData(flag);

        if (flag) {
            result.setMsg("根据查询条件查询数据存在！");
            result.setCode(HttpStatus.SC_OK);
        } else {
            result.setMsg("根据查询条件查询数据是不存在！");
            // 204 No Content
            result.setCode(HttpStatus.SC_NO_CONTENT);
        }

        return result;
    }

    @ApiOperation("根据更新条件更新了几条数据")
    @PostMapping(value = "updateByQueryModel")
    @InterceptAction("根据更新条件更新了几条数据")
    public HttpResult updateByQueryModel(@RequestBody(required = true) QueryModel${entity} condition) {
        HttpResult result = new HttpResult();

        if (condition == null) {
        return HttpResult.error(HttpStatus.SC_BAD_REQUEST, "请求参数不能为空");
        }

        Integer num = service.updateByQueryModel(condition);

        result.setData(num);

        if (num > 0) {
            result.setMsg("根据更新条件更新了" + num + "条数据！");
            result.setCode(HttpStatus.SC_OK);
        } else {
            result.setMsg("根据查询条件根据更新条件更新了0条数据！");
            // 204 No Content
            result.setCode(HttpStatus.SC_NO_CONTENT);
        }

        return result;
    }
}
</#if>
