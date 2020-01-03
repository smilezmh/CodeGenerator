package cmtech.soft.equipment.base.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import java.util.List;

import cmtech.soft.equipment.base.service.IEquipmentBaseInfoService;
import cmtech.soft.equipment.base.model.QueryModelEquipmentBaseInfo;
import cmtech.soft.equipment.base.entity.EquipmentBaseInfo;
import cmtech.soft.equipment.utils.MyStrTool;

import cmtech.soft.equipment.utils.HttpResult; // 自定义返回结果
import cmtech.soft.equipment.utils.Aop.InterceptAction; // 自定义拦截aop
import cmtech.soft.equipment.utils.HttpStatus; // 自定义状态返回

import org.springframework.web.bind.annotation.RestController;

/**
 * 设备基础信息主表 基本接口
 *
 * @author cmmes
 * @since 2020-01-03
 */
@ApiOperation("设备基础信息主表 基本接口")
@RestController
@RequestMapping("/base/equipment-base-info")
public class EquipmentBaseInfoController {
    @Autowired
    private IEquipmentBaseInfoService service;

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
        } else {
            result.setData(0);
            result.setMsg("带id返回值的插入或者更新方法失败！");
            // 204 No Content
            result.setCode(HttpStatus.SC_NO_CONTENT);
        }

        return result;
    }
}
