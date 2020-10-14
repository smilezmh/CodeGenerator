package ${package.Controller};

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;
import java.net.URLEncoder;

import ${package.Service}.${table.serviceName};
import ${cfg.prefix}.base.mapper.${entity}Mapper;
import ${cfg.prefix}.base.model.QueryModel${entity};
import ${cfg.prefix}.base.model.excelModel.${entity}Excel;
import ${package.Entity}.${entity};

import ${cfg.prefix}.utils.*;
import ${cfg.prefix}.utils.Aop.InterceptAction; // 自定义拦截aop
import ${cfg.prefix}.utils.model.ErrorReturn;
import ${cfg.prefix}.utils.model.TreeData;

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

    @Autowired
    private ${entity}Mapper mapper;

    @GetMapping("generateNo")
    @ApiOperation("生成单号")
    public HttpResultT<String> generateNo(@RequestParam(required = true,value = "prefix") String prefix,@RequestParam(required = true,value = "colName") String colName,
                                 @RequestParam(required = true,value = "noLength") Integer noLength,@RequestParam(required = false,value = "isRelatedToDate") Boolean isRelatedToDate) {
        HttpResultT<String> result=new HttpResultT();
        String no=service.generateNo(prefix, colName, noLength,isRelatedToDate);

        if(!MyStrTool.isNullOrEmpty(no)){
            result.setData(no);
        }else {
            result.error("生成单号失败！");
        }

        return result;
    }

    @PostMapping("download")
    @ApiOperation("下载excel表格")
    @InterceptAction("下载excel表格")
    public void download(@RequestBody (required = false) QueryModel${entity} condition, HttpServletResponse response) throws IOException {
        String filename="data";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(filename, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xlsx");
        response.setHeader("Content-Transfer-Encoding", "chunked");
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 获取数据
        List<${entity}Excel> excelList=service.getExcelListByQueryModel(condition) ;
        EasyExcel.write(response.getOutputStream(), ${entity}Excel.class).sheet("sheet").doWrite(excelList);
    }

    @PostMapping("upload")
    @ApiOperation("上传excel表格")
    @InterceptAction("上传excel表格")
    public HttpResultT<Boolean> upload(@RequestParam(value = "file") MultipartFile file) throws IOException {
        HttpResultT<Boolean> result=new HttpResultT();
        ExcelService<${entity}> excelService=new ExcelService<${entity}>(mapper){};
        EasyExcel.read(file.getInputStream(), ${entity}Excel.class, new ExcelReaderListener<${entity},${entity}Excel>(excelService){}).sheet().doRead();
        result.setData(true);
        return result;
    }

    @ApiOperation("批量修改或插入")
    @PostMapping(value = "saveOrUpdataBath")
    @InterceptAction("批量修改或插入")
    public HttpResultT<Boolean> saveOrUpdataBath(@RequestBody(required = true) List<${entity}> entities)  {
        HttpResultT<Boolean> result=new HttpResultT();

        if (entities == null) {
            return result.error("参数不能为空");
        }

        boolean flag = service.saveOrUpdateBatch(entities);

        if (flag) {
            result.setData(true);
        } else {
            // 失败的结果
            result.error("批量修改或插入失败！");
        }
        return result;
    }

    @ApiOperation("批量插入带有业务主键判断是否重复判断")
    @PostMapping(value = "insertBatchWithCodeRepeatCheck")
    @InterceptAction("批量插入带有业务主键判断是否重复判断")
    public HttpResultT<Boolean> insertBatchWithCodeRepeatCheck(@RequestBody(required = true) List<${entity}> entities) {
        HttpResultT<Boolean> result=new HttpResultT();

        if (entities == null) {
            return result.error("参数不能为空");
        }

        Integer num = service.insertBatchWithCodeRepeatCheck(entities);

        if (num > 0) {
            result.setData(true);
        } else if (num == ErrorReturn.CodeRepete) {
            result.error("主键重复！");
        } else {
            result.error("批量插入失败！");
        }

        return result;
    }

    @ApiOperation("根据条件分页查询信息")
    @PostMapping(value = "getPageByContition")
    public HttpResultT<IPage<${entity}>> getPageByContition(@RequestBody(required = false) QueryModel${entity} condition) {
        HttpResultT<IPage<${entity}>> result = new HttpResultT();
        IPage<${entity}> page = service.getPageByContition(condition);

        if (page != null && page.getRecords() != null && page.getRecords().size() > 0) {
            result.setData(page);
        } else {
            // 204 No Content
            result.empty();
        }
        return result;
    }

    @ApiOperation("根据条件无分页查询list信息")
    @PostMapping(value = "getListByQueryModel")
    public HttpResultT<List<${entity}>> getListByQueryModel(@RequestBody(required = false) QueryModel${entity} condition) {
        HttpResultT<List<${entity}>> result = new HttpResultT();
        List<${entity}> list=service.getListByQueryModel(condition);

        if (list != null && list.size() > 0) {
            result.setData(list);
        } else {
            // 204 No Content
            result.empty();
        }
        return result;
    }

    @ApiOperation("根据条件查询list第一个实体信息")
    @PostMapping(value = "getFirstOneByQueryModel")
    public HttpResultT<${entity}> getFirstOneByQueryModel(@RequestBody(required = false) QueryModel${entity} condition) {
        HttpResultT<${entity}> result = new HttpResultT();
        List<${entity}> list=service.getListByQueryModel(condition);

        if (list != null && list.size() > 0) {
            result.setData(list.get(0));
        } else {
            // 204 No Content
            result.empty();
        }
        return result;
    }

    @ApiOperation("根据条件查询list任意一个实体信息")
    @PostMapping(value = "getRandomOneByQueryModel")
    public HttpResultT<${entity}> getRandomOneByQueryModel(@RequestBody(required = false) QueryModel${entity} condition) {
        HttpResultT<${entity}> result = new HttpResultT();
        ${entity} entity=service.getRandomOneByQueryModel(condition);

        if (entity != null) {
            result.setData(entity);
        } else {
            // 204 No Content
            result.empty();
        }

        return result;
    }

    @ApiOperation("根据业务主键code查询单条数据")
    @GetMapping(value = "getEntityByCode")
    public HttpResultT<${entity}> getEntityByCode(@RequestParam(defaultValue = "") String code) {
        HttpResultT<${entity}> result = new HttpResultT();

        if (MyStrTool.isNullOrEmpty(code)) {
            return result.error("参数不能为空");
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
            result.setData(entity);
        } else {
            // 204 No Content
            result.empty();
        }

        return result;
    }

    @ApiOperation("根据主键id查询单条数据")
    @GetMapping(value = "getEntityById")
    public HttpResultT<${entity}> getEntityById(@RequestParam(required = true) Integer id) {
        HttpResultT<${entity}> result = new HttpResultT();

        if (id == null) {
            return result.error("参数不能为空");
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
            result.setData(entity);
        } else {
            // 204 No Content
            result.empty();
        }

        return result;
    }

    @ApiOperation("带id返回值的插入或者更新方法")
    @PostMapping(value = "saveOrUpdateWithIdReturnBack")
    @InterceptAction("带id返回值的插入或者更新方法")
    public HttpResultT<Integer> saveOrUpdateWithIdReturnBack(@RequestBody(required = true) ${entity} entity) {
        HttpResultT<Integer> result = new HttpResultT();

        if (entity == null) {
            return result.error("请求参数不能为空");
        }

        int id=service.saveOrUpdateWithIdReturnBack(entity);

        if (id>0) {
            result.setData(id);
        } else if(id == ErrorReturn.CodeRepete){
            result.error("主键重复！");
        }else {
            result.error("带id返回值的插入或者更新方法失败！");
        }

        return result;
    }

    @ApiOperation("带entity返回值的插入或者更新方法")
    @PostMapping(value = "saveOrUpdateWithEntityReturnBack")
    @InterceptAction("带entity返回值的插入或者更新方法")
    public HttpResultT<${entity}> saveOrUpdateWithEntityReturnBack(@RequestBody(required = true) ${entity} entity) {
        HttpResultT<${entity}>  result = new HttpResultT();

        if (entity == null) {
            return result.error("请求参数不能为空");
        }

        ${entity} entity1=service.saveOrUpdateWithEntityReturnBack(entity);

        if (entity1!=null&&entity1.getId()>0) {
            result.setData(entity1);
        } else if(entity1!=null&&entity1.getId() == ErrorReturn.CodeRepete){
            result.error("主键重复！");
        }else {
            result.error("带entity返回值的插入或者更新方法失败！");
        }

        return result;
    }

    @ApiOperation("根据查询条件查询数据是否存在")
    @PostMapping(value = "isExistsByQueryModel")
    @InterceptAction("根据查询条件查询数据是否存在")
    public HttpResultT<Boolean> isExistsByQueryModel(@RequestBody(required = true) QueryModel${entity} condition) {
        HttpResultT<Boolean> result = new HttpResultT();

        if (condition == null) {
        return result.error("请求参数不能为空");
        }

        boolean flag=service.isExistsByQueryModel(condition);

        result.setData(flag);

        if (!flag) {
            result.error("根据查询条件查询数据是不存在！");
        }

        return result;
    }

    @ApiOperation("根据更新条件更新了几条数据")
    @PostMapping(value = "updateByQueryModel")
    @InterceptAction("根据更新条件更新了几条数据")
    public HttpResultT<Integer> updateByQueryModel(@RequestBody(required = true) QueryModel${entity} condition) {
        HttpResultT<Integer> result = new HttpResultT();

        if (condition == null) {
            return result.error("请求参数不能为空");
        }

        Integer num = service.updateByQueryModel(null,condition);

        result.setData(num);

        if (num > 0) {
            result.setMsg("根据更新条件更新了" + num + "条数据！");
        } else {
            result.error("根据查询条件根据更新条件更新了0条数据！");
        }

        return result;
    }

    @ApiOperation("根据条件串表分页查询信息")
    @PostMapping(value = "getTablesPageByContition")
    public HttpResultT<IPage<${entity}>> getTablesPageByContition(@RequestBody(required = false) QueryModel${entity} condition) {
        HttpResultT<IPage<${entity}>> result = new HttpResultT();
        IPage<${entity}> page = service.getTablesPageByContition(condition);

        if (page != null && page.getRecords() != null && page.getRecords().size() > 0) {
            result.setData(page);
        } else {
            result.empty();
        }

        return result;
    }

    @ApiOperation("根据条件串表不分页查询list")
    @PostMapping(value = "getTablesByContition")
    public HttpResultT<List<${entity}>> getTablesByContition(@RequestBody(required = false) QueryModel${entity} condition) {
        HttpResultT<List<${entity}>> result = new HttpResultT();
        List<${entity}> list = service.getTablesByContition(condition);

        if (list!=null && !list.isEmpty()) {
            result.setData(list);
        } else {
            // 204 No Content
            result.empty();
        }

        return result;
    }

    @ApiOperation("根据id获取子级数据")
    @GetMapping(value = "getSubList")
    public HttpResultT<List<${entity}>> getSubList(@RequestParam(required = true) Integer id) {
        HttpResultT<List<${entity}>> result = new HttpResultT();
        List<${entity}> list=service.getSubList(id);

        if (list != null && list.size() > 0) {
            result.setData(list);
        } else {
            // 204 No Content
            result.empty();
        }
        return result;
    }

    @ApiOperation("有子级关系的根据条件查询")
    @PostMapping(value = "getListHasChildrenByContition")
    public HttpResultT<TreeData<${entity}>> getListHasChildrenByContition(@RequestBody(required = true) QueryModel${entity} condition) {
        HttpResultT<TreeData<${entity}>> result = new HttpResultT();
        TreeData<${entity}> treeData=service.getListHasChildrenByContition(condition);
        List<${entity}> list=treeData.getList();

        if (list != null && list.size() > 0) {
            result.setData(treeData);
        } else {
            // 204 No Content
            result.empty();
        }
        return result;
    }
}
</#if>
