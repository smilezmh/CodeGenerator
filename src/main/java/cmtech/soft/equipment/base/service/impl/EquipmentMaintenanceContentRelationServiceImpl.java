package cmtech.soft.equipment.base.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bestvike.linq.Linq;

import cmtech.soft.equipment.base.entity.EquipmentMaintenanceContentRelation;
import cmtech.soft.equipment.base.mapper.EquipmentMaintenanceContentRelationMapper;
import cmtech.soft.equipment.base.service.IEquipmentMaintenanceContentRelationService;
import cmtech.soft.equipment.base.model.QueryModelEquipmentMaintenanceContentRelation;
import cmtech.soft.equipment.utils.MyStrTool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cmtech.soft.equipment.utils.ErrorReturn;

import java.util.List;


/**
 * <p>
 * 设备保养内容和设备保养类型、设备类型关系维护表 服务实现类
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-14
 */
@Service
public class EquipmentMaintenanceContentRelationServiceImpl extends ServiceImpl<EquipmentMaintenanceContentRelationMapper, EquipmentMaintenanceContentRelation> implements IEquipmentMaintenanceContentRelationService {

    @Autowired
    EquipmentMaintenanceContentRelationMapper mapper;

    // 搜索条件
    private QueryWrapper<EquipmentMaintenanceContentRelation> wrapper;

    /**
    * 无分页查询list
    * @param condition 无分页条件
    * @return list
    */
    @Override
    public List<EquipmentMaintenanceContentRelation> getListByQueryModel(QueryModelEquipmentMaintenanceContentRelation condition) {
        // 联表查询
        // return cityMapper.GetTwoTableInfo();

        wrapper = getListWrapper(condition);
        List<EquipmentMaintenanceContentRelation> list = list(wrapper);
        // 处理一些结果列
        // dealWithViewName(list);
        return list;
    }

    /**
    * 根据条件随机取出一条数据
    * @param condition 查询条件
    * @return 实体
    */
    @Override
    public EquipmentMaintenanceContentRelation getRandomOneByQueryModel(QueryModelEquipmentMaintenanceContentRelation condition){
        QueryWrapper<EquipmentMaintenanceContentRelation> queryWrapper=new QueryWrapper<EquipmentMaintenanceContentRelation>();
        queryWrapper.eq("is_deleted",false);
        queryWrapper.lambda().eq(EquipmentMaintenanceContentRelation::getId,condition.getId());
        queryWrapper.last("LIMIT 1");
        return mapper.selectOne(queryWrapper);
     }

    /**
    * 根据条件查询分页结果
    * @param condition 查询条件
    * @return 分页结果
    */
    @Override
    public IPage<EquipmentMaintenanceContentRelation> getPageByContition(QueryModelEquipmentMaintenanceContentRelation condition) {
        // 查询条件
        // QueryWrapper<EquipmentMaintenanceContentRelation> queryWrapper = new QueryWrapper<>();
        // 分页对象
        // IPage<EquipmentMaintenanceContentRelation> iPage = new Page<EquipmentMaintenanceContentRelation>();
        // iPage.setSize(condition.getSize());
        // iPage.setCurrent(condition.getCurrent());

        // 连表查询
        // Page<> pagecity=new Page<>(page,limit);
        // QueryWrapper<> wrapper=new QueryWrapper<>();
        // wrapper.like("city.name","Ka");
        // pagecity.setRecords(cityMapper.GetTwoTableInfoPage(pagecity,wrapper));

        // 查询条件
        wrapper = getPageWrapper(condition);
        IPage<EquipmentMaintenanceContentRelation> iPage = new Page<EquipmentMaintenanceContentRelation>();
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
    public int saveOrUpdateWithIdReturnBack(EquipmentMaintenanceContentRelation entity) {
        int id = 0;

        if (entity.getId() == null) {
            //if (isCodeRepeat(entity)) { // 业务主键重复
            //    return ErrorReturn.CodeRepete;
            //}

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
    * 带有主键是否重复判断的批量插入方法
    *
    * @param entities 实体集合
    * @return 返回ErrorReturn.CodeRepete=-1就是主键重复，返回插入的数量
    */
    public Integer insertBatchWithCodeRepeatCheck(List<EquipmentMaintenanceContentRelation> entities) {
        if (isCodesRepeat(entities)) {
            return ErrorReturn.CodeRepete;
        }

        Integer num = 0;

        if (saveBatch(entities)) {
            num = entities.size();
        }

        return num;
    }

    /**
    * 根据查询条件查询数据是否存在
    *
    * @param condition 查询条件
    * @return 是或否
    */
    @Override
    public boolean isExistsByQueryModel(QueryModelEquipmentMaintenanceContentRelation condition) {
        QueryWrapper<EquipmentMaintenanceContentRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EquipmentMaintenanceContentRelation::getId, condition.getId());
        return mapper.selectCount(queryWrapper) > 0;
    }

    /**
    * 根据更新条件更新了几条数据
    *
    * @param condition 跟更新条件
    * @return 更新了几条数据
    */
    @Override
    public Integer updateByQueryModel(QueryModelEquipmentMaintenanceContentRelation condition) {
        EquipmentMaintenanceContentRelation entity = new EquipmentMaintenanceContentRelation();
        entity.setIsDeleted(true);

        UpdateWrapper<EquipmentMaintenanceContentRelation> updateWrapper = new UpdateWrapper<EquipmentMaintenanceContentRelation>();
        updateWrapper.lambda().eq(EquipmentMaintenanceContentRelation::getId, condition.getId());
        int num = mapper.update(entity, updateWrapper);
        return num;
    }

    /**
    * 根据条件串表分页查询
    * @param condition 查询条件
    * @return 分页结果
    */
    @Override
    public IPage<EquipmentMaintenanceContentRelation> getTablesPageByContition(QueryModelEquipmentMaintenanceContentRelation condition){
        IPage<EquipmentMaintenanceContentRelation> page=new Page<EquipmentMaintenanceContentRelation>(condition.getCurrent(),condition.getSize());
        return page.setRecords(mapper.getTablesByContition(page,condition));
    }

    /**
    * 根据条件串表不分页查询list
    * @param condition 查询条件
    * @return 分页结果
    */
    @Override
    public List<EquipmentMaintenanceContentRelation> getTablesByContition(QueryModelEquipmentMaintenanceContentRelation condition){
        return mapper.getTablesByContition(condition);
    }

    /**
    * 设置分页查询条件
    * @param condition 搜索条件QueryModel
    * @return 分页查询wrapper
    */
    private QueryWrapper<EquipmentMaintenanceContentRelation> getPageWrapper(QueryModelEquipmentMaintenanceContentRelation condition) {
        wrapper = new QueryWrapper<EquipmentMaintenanceContentRelation>();
        // 查询需要的结果列
        // queryWrapper.select("id", "code", "name", "remark");
        // 查找没有删除的数据
        wrapper.eq("is_deleted", false);

        if(!MyStrTool.isNullOrEmpty(condition.getCode())){// 保养内容编码，外链equipment_maintenance_content的code,保养内容编码
            wrapper.eq("code",condition.getCode());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getName())){// 保养内容，外链equipment_maintenance_content的name
            wrapper.like("name",condition.getName());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getPackageCode())){// 套餐编码
            wrapper.eq("package_code",condition.getPackageCode());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getEquipmentTypeCode())){// 设备类型编码
            wrapper.eq("equipment_type_code",condition.getEquipmentTypeCode());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getEquipmentCode())){// 设备编码
            wrapper.eq("equipment_code",condition.getEquipmentCode());
        }

        // 默认按id降序
        wrapper.orderBy(true,true,"package_code");
        return wrapper;
    }

    /**
    * 设置list查询条件
    * @param condition 搜索条件QueryModel
    * @return list查询wrapper
    */
    private QueryWrapper<EquipmentMaintenanceContentRelation> getListWrapper(QueryModelEquipmentMaintenanceContentRelation condition) {
        wrapper = new QueryWrapper<EquipmentMaintenanceContentRelation>();
        // 查询需要的结果列
        // queryWrapper.select("id", "code", "name", "remark");
        if(!MyStrTool.isNullOrEmpty(condition.getCode())){// 保养内容编码，外链equipment_maintenance_content的code,保养内容编码
            wrapper.eq("code",condition.getCode());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getName())){// 保养内容，外链equipment_maintenance_content的name
            wrapper.eq("name",condition.getName());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getPackageCode())){// 套餐编码
            wrapper.eq("package_code",condition.getPackageCode());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getEquipmentTypeCode())){// 设备类型编码
            wrapper.eq("equipment_type_code",condition.getEquipmentTypeCode());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getEquipmentCode())){// 设备编码
            wrapper.eq("equipment_code",condition.getEquipmentCode());
        }
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
    private void dealWithViewName(List<EquipmentMaintenanceContentRelation> list) {

        if (list != null && list.size() > 0) {
            for (EquipmentMaintenanceContentRelation entity : list) {
                // 重新赋值
                //entity.setEcnDetail(ecnDetail);
            }
        }
    }

    /**
    * 对实体，判断业务主键是否重复，重复不允许插入
    *
    * @param entity 实体
    * @return 是否重复
    */
    private boolean isCodeRepeat(EquipmentMaintenanceContentRelation entity) {
        QueryWrapper<EquipmentMaintenanceContentRelation> queryWrapper = new QueryWrapper<EquipmentMaintenanceContentRelation>();
        queryWrapper.eq("is_deleted", false);
        //queryWrapper.eq("code", entity.getCode());
        return mapper.selectCount(queryWrapper) > 0;
    }

    /**
    * 对实体集合，判断业务主键是否重复，重复不允许插入
    * @param list 实体list
    * @return 是否重复
    */
    private boolean isCodesRepeat(List<EquipmentMaintenanceContentRelation> list) {
        if(list==null||list.isEmpty()){
            return false;
        }

        QueryWrapper<EquipmentMaintenanceContentRelation> queryWrapper = new QueryWrapper<EquipmentMaintenanceContentRelation>();
        queryWrapper.eq("is_deleted", false);
        // 如果保养内容id
        List<String> codes= Linq.of(list).select(x->x.getCode()).toList();
        queryWrapper.in("code", codes);
        return mapper.selectCount(queryWrapper) > 0;
     }
}
