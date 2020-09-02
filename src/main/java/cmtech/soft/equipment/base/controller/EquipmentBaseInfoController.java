package cmtech.soft.equipment.base.controller;

import cmtech.soft.equipment.base.mapper.EquipmentBaseInfoMapper;
import cmtech.soft.equipment.base.model.excelModel.EquipmentBaseInfoExcel;
import cmtech.soft.equipment.utils.*;
import cmtech.soft.equipment.utils.model.ErrorReturn;
import com.alibaba.excel.EasyExcel;
import com.bestvike.linq.IEnumerable;
import com.bestvike.linq.Linq;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import cmtech.soft.equipment.base.service.IEquipmentBaseInfoService;
import cmtech.soft.equipment.base.model.QueryModelEquipmentBaseInfo;
import cmtech.soft.equipment.base.entity.EquipmentBaseInfo;

import cmtech.soft.equipment.utils.Aop.InterceptAction; // 自定义拦截aop

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 设备基础信息主表 基本接口
 *
 * @author smilezmh
 * @since 2020-01-10
 */
@Api(tags = "设备基础信息主表 基本接口")
@RestController
@RequestMapping("/base/equipment-base-info")
public class EquipmentBaseInfoController {
    @Autowired
    private IEquipmentBaseInfoService service;

    @Autowired
    private EquipmentBaseInfoMapper mapper;

    @PostMapping("download")
    @ApiOperation("下载excel表格")
    @InterceptAction("下载excel表格")
    public void download(@RequestBody (required = false) QueryModelEquipmentBaseInfo condition, HttpServletResponse response) throws IOException {
        String filename="data";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(filename, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xlsx");
        response.setHeader("Content-Transfer-Encoding", "chunked");
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 获取数据
        List<EquipmentBaseInfoExcel> excelList=service.getExcelListByQueryModel(condition) ;
        EasyExcel.write(response.getOutputStream(), EquipmentBaseInfoExcel.class).sheet("sheet").doWrite(excelList);
    }

    @PostMapping("upload")
    @ApiOperation("上传excel表格")
    @InterceptAction("上传excel表格")
    public HttpResult upload(@RequestParam(value = "file") MultipartFile file) throws IOException {
        HttpResult result=new HttpResult();
        ExcelService<EquipmentBaseInfo> excelService=new ExcelService<EquipmentBaseInfo>(mapper){};
        EasyExcel.read(file.getInputStream(), EquipmentBaseInfoExcel.class, new ExcelReaderListener<EquipmentBaseInfo,EquipmentBaseInfoExcel>(excelService){}).sheet().doRead();
        result.setCode(HttpStatus.SC_OK);
        result.setMsg("上传excel成功！");
        result.setData(true);
        return result;
    }

    @ApiOperation("批量修改或插入")
    @PostMapping(value = "saveOrUpdataBath")
    @InterceptAction("批量修改或插入")
    public HttpResult saveOrUpdataBath(@RequestBody(required = true) List<EquipmentBaseInfo> entities)  {
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
    public HttpResult insertBatchWithCodeRepeatCheck(@RequestBody(required = true) List<EquipmentBaseInfo> entities) {
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
    public HttpResult getPageByContition(@RequestBody(required = false) QueryModelEquipmentBaseInfo condition) {
        HttpResult result = new HttpResult();
        IPage<EquipmentBaseInfo> page = service.getPageByContition(condition);

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
    public HttpResult getListByQueryModel(@RequestBody(required = false) QueryModelEquipmentBaseInfo condition) {
        HttpResult result = new HttpResult();
        List<EquipmentBaseInfo> list=service.getListByQueryModel(condition);

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
    public HttpResult getFirstOneByQueryModel(@RequestBody(required = false) QueryModelEquipmentBaseInfo condition) {
    HttpResult result = new HttpResult();
        List<EquipmentBaseInfo> list=service.getListByQueryModel(condition);

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
    public HttpResult getRandomOneByQueryModel(@RequestBody(required = false) QueryModelEquipmentBaseInfo condition) {
        HttpResult result = new HttpResult();
        EquipmentBaseInfo entity=service.getRandomOneByQueryModel(condition);

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

        QueryWrapper<EquipmentBaseInfo> queryWrapper=new QueryWrapper<EquipmentBaseInfo>();

        // 查询需要的结果列
        // queryWrapper.select("id", "code", "name", "remark");
        // 需要取消注释
        // queryWrapper.eq("code", no);
        // 是否删除
        queryWrapper.eq("is_deleted", false);
        // 如果是多条随机取出一条，不会报异常
        queryWrapper.last("LIMIT 1");
        EquipmentBaseInfo entity = service.getOne(queryWrapper);

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

        QueryWrapper<EquipmentBaseInfo> queryWrapper=new QueryWrapper<EquipmentBaseInfo>();

        // 查询需要的结果列
        // queryWrapper.select("id", "code", "name", "remark");
        queryWrapper.eq("id", id);
        // 是否删除
        queryWrapper.eq("is_deleted", false);
        // 如果是多条随机取出一条，不会报异常
        queryWrapper.last("LIMIT 1");
        EquipmentBaseInfo entity = service.getOne(queryWrapper);

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
    public HttpResult saveOrUpdateWithIdReturnBack(@RequestBody(required = true) EquipmentBaseInfo entity) {
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
    public HttpResult isExistsByQueryModel(@RequestBody(required = true) QueryModelEquipmentBaseInfo condition) {
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
    public HttpResult updateByQueryModel(@RequestBody(required = true) QueryModelEquipmentBaseInfo condition) {
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

    @ApiOperation("根据条件串表分页查询信息")
    @PostMapping(value = "getTablesPageByContition")
    public HttpResult getTablesPageByContition(@RequestBody(required = false) QueryModelEquipmentBaseInfo condition) {
        HttpResult result = new HttpResult();
        IPage<EquipmentBaseInfo> page = service.getTablesPageByContition(condition);

        if (page != null && page.getRecords() != null && page.getRecords().size() > 0) {
            result.setCode(HttpStatus.SC_OK);
            result.setData(page);
            result.setMsg("根据条件串表分页查询信息成功！");
        } else {
            // 204 No Content
            result.setCode(HttpStatus.SC_NO_CONTENT);
            result.setData(page);
            result.setMsg("根据条件串表分页查询信息为空！");
        }

        return result;
    }

    @ApiOperation("根据条件串表不分页查询list")
    @PostMapping(value = "getTablesByContition")
    public HttpResult getTablesByContition(@RequestBody(required = false) QueryModelEquipmentBaseInfo condition) {
        HttpResult result = new HttpResult();
        List<EquipmentBaseInfo> list = service.getTablesByContition(condition);

        if (list!=null && !list.isEmpty()) {
            result.setCode(HttpStatus.SC_OK);
            result.setData(list);
            result.setMsg("根据条件串表不分页查询list成功！");
        } else {
            // 204 No Content
            result.setCode(HttpStatus.SC_NO_CONTENT);
            result.setData(list);
            result.setMsg("根据条件串表不分页查询list为空！");
        }

        return result;
    }
}
