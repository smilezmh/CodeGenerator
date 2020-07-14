package cmtech.soft.equipment.base.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bestvike.linq.Linq;

import cmtech.soft.equipment.base.entity.EquipmentMaintenancePlan;
import cmtech.soft.equipment.base.mapper.EquipmentMaintenancePlanMapper;
import cmtech.soft.equipment.base.service.IEquipmentMaintenancePlanService;
import cmtech.soft.equipment.base.model.QueryModelEquipmentMaintenancePlan;
import cmtech.soft.equipment.utils.MyStrTool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cmtech.soft.equipment.utils.model.ErrorReturn;

import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-14
 */
@Service
public class EquipmentMaintenancePlanServiceImpl extends ServiceImpl<EquipmentMaintenancePlanMapper, EquipmentMaintenancePlan> implements IEquipmentMaintenancePlanService {

    @Autowired
    EquipmentMaintenancePlanMapper mapper;

    // 搜索条件
    private QueryWrapper<EquipmentMaintenancePlan> wrapper;

    /**
    * 无分页查询list
    * @param condition 无分页条件
    * @return list
    */
    @Override
    public List<EquipmentMaintenancePlan> getListByQueryModel(QueryModelEquipmentMaintenancePlan condition) {
        // 联表查询
        // return cityMapper.GetTwoTableInfo();

        wrapper = getListWrapper(condition);
        List<EquipmentMaintenancePlan> list = list(wrapper);
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
    public EquipmentMaintenancePlan getRandomOneByQueryModel(QueryModelEquipmentMaintenancePlan condition){
        QueryWrapper<EquipmentMaintenancePlan> queryWrapper=new QueryWrapper<EquipmentMaintenancePlan>();
        queryWrapper.eq("is_deleted",false);
        queryWrapper.lambda().eq(EquipmentMaintenancePlan::getId,condition.getId());
        queryWrapper.last("LIMIT 1");
        return mapper.selectOne(queryWrapper);
     }

    /**
    * 根据条件查询分页结果
    * @param condition 查询条件
    * @return 分页结果
    */
    @Override
    public IPage<EquipmentMaintenancePlan> getPageByContition(QueryModelEquipmentMaintenancePlan condition) {
        // 查询条件
        // QueryWrapper<EquipmentMaintenancePlan> queryWrapper = new QueryWrapper<>();
        // 分页对象
        // IPage<EquipmentMaintenancePlan> iPage = new Page<EquipmentMaintenancePlan>();
        // iPage.setSize(condition.getSize());
        // iPage.setCurrent(condition.getCurrent());

        // 连表查询
        // Page<> pagecity=new Page<>(page,limit);
        // QueryWrapper<> wrapper=new QueryWrapper<>();
        // wrapper.like("city.name","Ka");
        // pagecity.setRecords(cityMapper.GetTwoTableInfoPage(pagecity,wrapper));

        // 查询条件
        wrapper = getPageWrapper(condition);
        IPage<EquipmentMaintenancePlan> iPage = new Page<EquipmentMaintenancePlan>();
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
    public int saveOrUpdateWithIdReturnBack(EquipmentMaintenancePlan entity) {
        int id = 0;

        if (entity.getId() == null) {
            if (isCodeRepeat(entity)) { // 业务主键重复,计划单号不能重复，同一种设备加保养类型也是唯一组合
                return ErrorReturn.CodeRepete;
            }

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
    public Integer insertBatchWithCodeRepeatCheck(List<EquipmentMaintenancePlan> entities) {
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
    public boolean isExistsByQueryModel(QueryModelEquipmentMaintenancePlan condition) {
        QueryWrapper<EquipmentMaintenancePlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EquipmentMaintenancePlan::getId, condition.getId());
        return mapper.selectCount(queryWrapper) > 0;
    }

    /**
    * 根据更新条件更新了几条数据
    *
    * @param condition 跟更新条件
    * @return 更新了几条数据
    */
    @Override
    public Integer updateByQueryModel(QueryModelEquipmentMaintenancePlan condition) {
        EquipmentMaintenancePlan entity = new EquipmentMaintenancePlan();
        entity.setIsDeleted(true);

        UpdateWrapper<EquipmentMaintenancePlan> updateWrapper = new UpdateWrapper<EquipmentMaintenancePlan>();
        updateWrapper.lambda().eq(EquipmentMaintenancePlan::getId, condition.getId());
        int num = mapper.update(entity, updateWrapper);
        return num;
    }

    /**
    * 根据条件串表分页查询
    * @param condition 查询条件
    * @return 分页结果
    */
    @Override
    public IPage<EquipmentMaintenancePlan> getTablesPageByContition(QueryModelEquipmentMaintenancePlan condition){
        IPage<EquipmentMaintenancePlan> page=new Page<EquipmentMaintenancePlan>(condition.getCurrent(),condition.getSize());
        return page.setRecords(mapper.getTablesByContition(page,condition));
    }

    /**
    * 根据条件串表不分页查询list
    * @param condition 查询条件
    * @return 分页结果
    */
    @Override
    public List<EquipmentMaintenancePlan> getTablesByContition(QueryModelEquipmentMaintenancePlan condition){
        return mapper.getTablesByContition(condition);
    }

    /**
    * 设置分页查询条件
    * @param condition 搜索条件QueryModel
    * @return 分页查询wrapper
    */
    private QueryWrapper<EquipmentMaintenancePlan> getPageWrapper(QueryModelEquipmentMaintenancePlan condition) {
        wrapper = new QueryWrapper<EquipmentMaintenancePlan>();
        // 查询需要的结果列
        // queryWrapper.select("id", "code", "name", "remark");
        if(!MyStrTool.isNullOrEmpty(condition.getCode())){// 计划单号
            wrapper.lambda().like(EquipmentMaintenancePlan::getCode,condition.getCode());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getEquipmentCode())){// 设备编号
            wrapper.lambda().eq(EquipmentMaintenancePlan::getEquipmentCode,condition.getEquipmentCode());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getEquipmentDetail())){// 设备类型等信息
            wrapper.lambda().like(EquipmentMaintenancePlan::getEquipmentDetail,condition.getEquipmentDetail());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getPackageCode())){// 套餐编码
            wrapper.lambda().eq(EquipmentMaintenancePlan::getPackageCode,condition.getPackageCode());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getMaintenanceType())){// 保养类型
            wrapper.lambda().eq(EquipmentMaintenancePlan::getMaintenanceType,condition.getMaintenanceType());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getMaintenanceContents())){// 保养内容
            wrapper.lambda().like(EquipmentMaintenancePlan::getMaintenanceContents,condition.getMaintenanceContents());
        }

        if(condition.getIsValid()!=null){// 是否开启推送
            wrapper.lambda().eq(EquipmentMaintenancePlan::getIsValid,condition.getIsValid());
        }

        if(condition.getIsDelayAllowed()!=null){// 是否允许延迟
            wrapper.lambda().eq(EquipmentMaintenancePlan::getIsDelayAllowed,condition.getIsDelayAllowed());
        }

        if (condition.getMaintenancePlanStartTimeRangeT1() != null) { // 计划开始时间
            wrapper.lambda().ge(EquipmentMaintenancePlan::getMaintenancePlanStartTime, condition.getMaintenancePlanStartTimeRangeT1());
        }

        if (condition.getMaintenancePlanStartTimeRangeT2() != null) {// 送修计划结束时间
            wrapper.lambda().le(EquipmentMaintenancePlan::getMaintenancePlanStartTime, condition.getMaintenancePlanStartTimeRangeT2());
        }

        if (condition.getNextMaintenaceTimeRangeT1() != null) { // 下次维护开始时间
            wrapper.lambda().ge(EquipmentMaintenancePlan::getNextMaintenaceTime, condition.getNextMaintenaceTimeRangeT1());
        }

        if (condition.getNextMaintenaceTimeRangeT2() != null) {// 下次维护结束时间
            wrapper.lambda().le(EquipmentMaintenancePlan::getNextMaintenaceTime, condition.getNextMaintenaceTimeRangeT2());
        }

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
    private QueryWrapper<EquipmentMaintenancePlan> getListWrapper(QueryModelEquipmentMaintenancePlan condition) {
        wrapper = new QueryWrapper<EquipmentMaintenancePlan>();
        // 查询需要的结果列
        // queryWrapper.select("id", "code", "name", "remark");

        if(!MyStrTool.isNullOrEmpty(condition.getCode())){// 计划单号
            wrapper.lambda().like(EquipmentMaintenancePlan::getCode,condition.getCode());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getEquipmentCode())){// 设备编号
            wrapper.lambda().eq(EquipmentMaintenancePlan::getEquipmentCode,condition.getEquipmentCode());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getEquipmentDetail())){// 设备类型等信息
            wrapper.lambda().like(EquipmentMaintenancePlan::getEquipmentDetail,condition.getEquipmentDetail());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getPackageCode())){// 套餐编码
            wrapper.lambda().eq(EquipmentMaintenancePlan::getPackageCode,condition.getPackageCode());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getMaintenanceType())){// 保养类型
            wrapper.lambda().eq(EquipmentMaintenancePlan::getMaintenanceType,condition.getMaintenanceType());
        }

        if(!MyStrTool.isNullOrEmpty(condition.getMaintenanceContents())){// 保养内容
            wrapper.lambda().like(EquipmentMaintenancePlan::getMaintenanceContents,condition.getMaintenanceContents());
        }

        if(condition.getIsValid()!=null){// 是否开启推送
            wrapper.lambda().eq(EquipmentMaintenancePlan::getIsValid,condition.getIsValid());
        }

        if(condition.getIsDelayAllowed()!=null){// 是否允许延迟
            wrapper.lambda().eq(EquipmentMaintenancePlan::getIsDelayAllowed,condition.getIsDelayAllowed());
        }

        if (condition.getMaintenancePlanStartTimeRangeT1() != null) { // 计划开始时间
            wrapper.lambda().ge(EquipmentMaintenancePlan::getMaintenancePlanStartTime, condition.getMaintenancePlanStartTimeRangeT1());
        }

        if (condition.getMaintenancePlanStartTimeRangeT2() != null) {// 送修计划结束时间
            wrapper.lambda().le(EquipmentMaintenancePlan::getMaintenancePlanStartTime, condition.getMaintenancePlanStartTimeRangeT2());
        }

        if (condition.getNextMaintenaceTimeRangeT1() != null) { // 下次维护开始时间
            wrapper.lambda().ge(EquipmentMaintenancePlan::getNextMaintenaceTime, condition.getNextMaintenaceTimeRangeT1());
        }

        if (condition.getNextMaintenaceTimeRangeT2() != null) {// 下次维护结束时间
            wrapper.lambda().le(EquipmentMaintenancePlan::getNextMaintenaceTime, condition.getNextMaintenaceTimeRangeT2());
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
    private void dealWithViewName(List<EquipmentMaintenancePlan> list) {

        if (list != null && list.size() > 0) {
            for (EquipmentMaintenancePlan entity : list) {
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
    private boolean isCodeRepeat(EquipmentMaintenancePlan entity) {
        boolean flag=false;
        QueryWrapper<EquipmentMaintenancePlan> queryWrapper = new QueryWrapper<EquipmentMaintenancePlan>();
        queryWrapper.eq("is_deleted", false);
        queryWrapper.eq("code", entity.getCode());
        int num=mapper.selectCount(queryWrapper);

        if(num>0){// 先看保养单号是不是重复
            flag=true;
        }else {// 一种设备编码+保养类型只能有一种组合
            queryWrapper = new QueryWrapper<EquipmentMaintenancePlan>();
            queryWrapper.lambda().eq(EquipmentMaintenancePlan::getEquipmentCode,entity.getEquipmentCode());// 设备编码
            queryWrapper.lambda().eq(EquipmentMaintenancePlan::getMaintenanceType,entity.getMaintenanceType());// 保养类型

            if(mapper.selectCount(queryWrapper)>0){
                flag=true;
            }
        }

        return flag;
    }

    /**
    * 对实体集合，判断业务主键是否重复，重复不允许插入
    * @param list 实体list
    * @return 是否重复
    */
    private boolean isCodesRepeat(List<EquipmentMaintenancePlan> list) {
        if(list==null||list.isEmpty()){
            return false;
        }

        QueryWrapper<EquipmentMaintenancePlan> queryWrapper = new QueryWrapper<EquipmentMaintenancePlan>();
        queryWrapper.eq("is_deleted", false);
        List<String> codes= Linq.of(list).select(x->x.getCode()).toList();
        queryWrapper.in("code", codes);
        return mapper.selectCount(queryWrapper) > 0;
     }
}
