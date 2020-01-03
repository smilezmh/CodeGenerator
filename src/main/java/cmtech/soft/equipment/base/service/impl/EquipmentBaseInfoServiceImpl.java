package cmtech.soft.equipment.base.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cmtech.soft.equipment.base.entity.EquipmentBaseInfo;
import cmtech.soft.equipment.base.mapper.EquipmentBaseInfoMapper;
import cmtech.soft.equipment.base.service.IEquipmentBaseInfoService;
import cmtech.soft.equipment.base.model.QueryModelEquipmentBaseInfo;
import cmtech.soft.equipment.utils.MyStrTool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;


/**
 * <p>
 * 设备基础信息主表 服务实现类
 * </p>
 *
 * @author cmmes
 * @since 2020-01-03
 */
@Service
public class EquipmentBaseInfoServiceImpl extends ServiceImpl<EquipmentBaseInfoMapper, EquipmentBaseInfo> implements IEquipmentBaseInfoService {

    @Autowired
    EquipmentBaseInfoMapper mapper;

    // 搜索条件
    private QueryWrapper<EquipmentBaseInfo> wrapper;

    /**
    * 无分页查询list
    * @param condition 无分页条件
    * @return list
    */
    @Override
    public List<EquipmentBaseInfo> getListByQueryModel(QueryModelEquipmentBaseInfo condition) {
        wrapper = getListWrapper(condition);
        List<EquipmentBaseInfo> list = list(wrapper);
        // 处理一些结果列
        // dealWithViewName(list);
        return list;
    }

    /**
    * 根据条件查询分页结果
    * @param condition 查询条件
    * @return 分页结果
    */
    @Override
    public IPage<EquipmentBaseInfo> getPageByContition(QueryModelEquipmentBaseInfo condition) {
        // 查询条件
        // QueryWrapper<EquipmentBaseInfo> queryWrapper = new QueryWrapper<>();
        // 分页对象
        // IPage<EquipmentBaseInfo> iPage = new Page<EquipmentBaseInfo>();
        // iPage.setSize(condition.getSize());
        // iPage.setCurrent(condition.getCurrent());

        // 查询条件
        wrapper = getPageWrapper(condition);
        IPage<EquipmentBaseInfo> iPage = new Page<EquipmentBaseInfo>();
        // 设置当前页
        iPage.setCurrent(condition.getCurrent());
        // 设置每页几条数据
        iPage.setSize(condition.getSize());

        // 查询需要的结果列
        // queryWrapper.select("id", "code", "name", "remark", "responsible_name", "customer");

        //if (!MyStrTool.isNullOrEmpty(condition.getCode())) {
        //    queryWrapper.eq("code", condition.getCode());
        //}

        // 是否删除
        wrapper.eq("is_deleted", false);
        // 是否有效
        // wrapper.eq("is_valid", true);

        iPage = page(iPage, wrapper);
        // 处理结果列
        // dealWithViewName(iPage.getRecords());
        return iPage;
    }

    /**
    * 插入数据库实体，并返回实体在数据库的id
    * @param entity 插入的实体
    * @return  返回实体在数据库的id
    */
    @Override
    public int saveOrUpdateWithIdReturnBack(EquipmentBaseInfo entity) {
        int id = 0;

        if (entity.getId() == null) {
            id = mapper.insert(entity);
        } else if (entity.getId() > 0) {
            id = mapper.updateById(entity);
        }

        // 表示插入成功
        if (id > 0) {
            id = entity.getId();
        }

        return id;
    }

    /**
    * 设置分页查询条件
    * @param condition 搜索条件QueryModel
    * @return 分页查询wrapper
    */
    private QueryWrapper<EquipmentBaseInfo> getPageWrapper(QueryModelEquipmentBaseInfo condition) {
        wrapper = new QueryWrapper<EquipmentBaseInfo>();
        // 查询需要的结果列
        // queryWrapper.select("id", "code", "name", "remark");
        // 查找没有删除的数据
        wrapper.eq("is_deleted", false);
        // 默认按id降序
        wrapper.orderBy(true, false, "id");
        return wrapper;
    }

    /**
    * 设置list查询条件
    * @param condition 搜索条件QueryModel
    * @return list查询wrapper
    */
    private QueryWrapper<EquipmentBaseInfo> getListWrapper(QueryModelEquipmentBaseInfo condition) {
        wrapper = new QueryWrapper<EquipmentBaseInfo>();
        // 查询需要的结果列
        // queryWrapper.select("id", "code", "name", "remark");
        // 查找没有删除的数据
        wrapper.eq("is_deleted", false);
        // 默认按id降序
        wrapper.orderBy(true, false, "id");
        return wrapper;
    }

    /**
    * 处理返回页面视图结果列
    * @param list 实体list
    */
    private void dealWithViewName(List<EquipmentBaseInfo> list) {

        if (list != null && list.size() > 0) {
            for (EquipmentBaseInfo entity : list) {
                // 重新赋值
                //entity.setEcnDetail(ecnDetail);
            }
        }
    }
}
