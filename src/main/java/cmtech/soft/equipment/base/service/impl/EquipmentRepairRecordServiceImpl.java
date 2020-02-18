package cmtech.soft.equipment.base.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bestvike.linq.Linq;

import cmtech.soft.equipment.base.entity.EquipmentRepairRecord;
import cmtech.soft.equipment.base.mapper.EquipmentRepairRecordMapper;
import cmtech.soft.equipment.base.service.IEquipmentRepairRecordService;
import cmtech.soft.equipment.base.model.QueryModelEquipmentRepairRecord;
import cmtech.soft.equipment.utils.MyStrTool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cmtech.soft.equipment.utils.ErrorReturn;

import java.util.List;


/**
 * <p>
 * 设备维修记录表 服务实现类
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-10
 */
@Service
public class EquipmentRepairRecordServiceImpl extends ServiceImpl<EquipmentRepairRecordMapper, EquipmentRepairRecord> implements IEquipmentRepairRecordService {

    @Autowired
    EquipmentRepairRecordMapper mapper;

    // 搜索条件
    private QueryWrapper<EquipmentRepairRecord> wrapper;

    /**
     * 无分页查询list
     *
     * @param condition 无分页条件
     * @return list
     */
    @Override
    public List<EquipmentRepairRecord> getListByQueryModel(QueryModelEquipmentRepairRecord condition) {
        // 联表查询
        // return cityMapper.GetTwoTableInfo();

        wrapper = getListWrapper(condition);
        List<EquipmentRepairRecord> list = list(wrapper);
        // 处理一些结果列
        // dealWithViewName(list);
        return list;
    }

    /**
     * 根据条件随机取出一条数据
     *
     * @param condition 查询条件
     * @return 实体
     */
    @Override
    public EquipmentRepairRecord getRandomOneByQueryModel(QueryModelEquipmentRepairRecord condition) {
        QueryWrapper<EquipmentRepairRecord> queryWrapper = new QueryWrapper<EquipmentRepairRecord>();
        queryWrapper.eq("is_deleted", false);
        queryWrapper.lambda().eq(EquipmentRepairRecord::getId, condition.getId());
        queryWrapper.last("LIMIT 1");
        return mapper.selectOne(queryWrapper);
    }

    /**
     * 根据条件查询分页结果
     *
     * @param condition 查询条件
     * @return 分页结果
     */
    @Override
    public IPage<EquipmentRepairRecord> getPageByContition(QueryModelEquipmentRepairRecord condition) {
        // 查询条件
        // QueryWrapper<EquipmentRepairRecord> queryWrapper = new QueryWrapper<>();
        // 分页对象
        // IPage<EquipmentRepairRecord> iPage = new Page<EquipmentRepairRecord>();
        // iPage.setSize(condition.getSize());
        // iPage.setCurrent(condition.getCurrent());

        // 连表查询
        // Page<> pagecity=new Page<>(page,limit);
        // QueryWrapper<> wrapper=new QueryWrapper<>();
        // wrapper.like("city.name","Ka");
        // pagecity.setRecords(cityMapper.GetTwoTableInfoPage(pagecity,wrapper));

        // 查询条件
        wrapper = getPageWrapper(condition);
        IPage<EquipmentRepairRecord> iPage = new Page<EquipmentRepairRecord>();
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
     *
     * @param entity 插入的实体
     * @return 返回实体在数据库的id
     */
    @Override
    public int saveOrUpdateWithIdReturnBack(EquipmentRepairRecord entity) {
        int id = 0;

        if (entity.getId() == null) {
            if (isCodeRepeat(entity)) { // 业务主键重复
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
    public Integer insertBatchWithCodeRepeatCheck(List<EquipmentRepairRecord> entities) {
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
    public boolean isExistsByQueryModel(QueryModelEquipmentRepairRecord condition) {
        QueryWrapper<EquipmentRepairRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EquipmentRepairRecord::getId, condition.getId());
        return mapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 根据更新条件更新了几条数据
     *
     * @param condition 跟更新条件
     * @return 更新了几条数据
     */
    @Override
    public Integer updateByQueryModel(QueryModelEquipmentRepairRecord condition) {
        EquipmentRepairRecord entity = new EquipmentRepairRecord();
        entity.setIsDeleted(true);

        UpdateWrapper<EquipmentRepairRecord> updateWrapper = new UpdateWrapper<EquipmentRepairRecord>();
        updateWrapper.lambda().eq(EquipmentRepairRecord::getId, condition.getId());
        int num = mapper.update(entity, updateWrapper);
        return num;
    }

    /**
     * 根据条件串表分页查询
     *
     * @param condition 查询条件
     * @return 分页结果
     */
    @Override
    public IPage<EquipmentRepairRecord> getTablesPageByContition(QueryModelEquipmentRepairRecord condition) {
        IPage<EquipmentRepairRecord> page = new Page<EquipmentRepairRecord>(condition.getCurrent(), condition.getSize());
        return page.setRecords(mapper.getTablesByContition(page, condition));
    }

    /**
     * 根据条件串表不分页查询list
     *
     * @param condition 查询条件
     * @return 分页结果
     */
    @Override
    public List<EquipmentRepairRecord> getTablesByContition(QueryModelEquipmentRepairRecord condition) {
        return mapper.getTablesByContition(condition);
    }

    /**
     * 设置分页查询条件
     *
     * @param condition 搜索条件QueryModel
     * @return 分页查询wrapper
     */
    private QueryWrapper<EquipmentRepairRecord> getPageWrapper(QueryModelEquipmentRepairRecord condition) {
        wrapper = new QueryWrapper<EquipmentRepairRecord>();
        // 查询需要的结果列
        // queryWrapper.select("id", "code", "name", "remark");
        // 查找没有删除的数据
        wrapper.eq("is_deleted", false);

        if (condition != null) {

            if (!MyStrTool.isNullOrEmpty(condition.getCode())) {
                wrapper.like("code", condition.getCode());
            }

            if (!MyStrTool.isNullOrEmpty(condition.getEquipmentCode())) { // 资产编号
                wrapper.like("equipment_code", condition.getEquipmentCode());
            }

            if (!MyStrTool.isNullOrEmpty(condition.getEquipmentCode())) { // 资产编号
                wrapper.like("equipment_code", condition.getEquipmentCode());
            }

            if (!MyStrTool.isNullOrEmpty(condition.getRepairApplicantNo())) {
                wrapper.like("repair_applicant_no", condition.getRepairApplicantNo());// 保修人
            }

            if (!MyStrTool.isNullOrEmpty(condition.getRepairPersonNo())) {
                wrapper.like("repair_person_no", condition.getRepairPersonNo());// 维修人
            }

            if (!MyStrTool.isNullOrEmpty(condition.getRepairApplicantTel())) {
                wrapper.like("repair_applicant_tel", condition.getRepairApplicantTel());// 保修人电话
            }

            if (!MyStrTool.isNullOrEmpty(condition.getEquipmentPosCode())) {
                wrapper.eq("equipment_pos_code", condition.getEquipmentPosCode());// 设备位置
            }

            if (!MyStrTool.isNullOrEmpty(condition.getEmergencyCode())) {
                wrapper.eq("emergency_code", condition.getEmergencyCode());// 设备紧急程度
            }

            if (!MyStrTool.isNullOrEmpty(condition.getFaultCategoryCode())) {
                wrapper.eq("fault_category_code", condition.getFaultCategoryCode());// 设备故障类别
            }

            if (!MyStrTool.isNullOrEmpty(condition.getFaultDesc())) {
                wrapper.like("fault_desc", condition.getFaultDesc());// 设备故障描述
            }

            if (!MyStrTool.isNullOrEmpty(condition.getRepeirResonAnalyse())) {
                wrapper.like("repeir_reson_analyse", condition.getRepeirResonAnalyse());// 设备故障原因分析
            }

            if (!MyStrTool.isNullOrEmpty(condition.getRepairContent())) {
                wrapper.like("repair_content", condition.getRepairContent());// 设备维修内容记录
            }

            if (!MyStrTool.isNullOrEmpty(condition.getOuterRepairNo())) {
                wrapper.eq("outer_repair_no", condition.getOuterRepairNo());// 设备转外部维修单号
            }

            if (!MyStrTool.isNullOrEmpty(condition.getEquipmentFactoryNo())) {
                wrapper.eq("equipment_factory_no", condition.getEquipmentFactoryNo());// 设备出厂编码
            }

            if (!MyStrTool.isNullOrEmpty(condition.getEquipmentDesc())) {
                wrapper.like("equipment_desc", condition.getEquipmentDesc());// 设备资产描述
            }

            if (condition.getSendRepairTimeRangeT1() != null) { // 送修时间
                wrapper.ge("send_repair_time", condition.getSendRepairTimeRangeT1());
            }

            if (condition.getSendRepairTimeRangeT2() != null) {// 送修时间
                wrapper.le("send_repair_time", condition.getSendRepairTimeRangeT2());
            }

            if (condition.getRepairStartTimeRangeT1() != null) { // 维修开始时间
                wrapper.ge("repair_start_time", condition.getRepairStartTimeRangeT1());
            }

            if (condition.getRepairStartTimeRangeT2() != null) { // 维修开始时间
                wrapper.le("repair_start_time", condition.getRepairStartTimeRangeT2());
            }

            if (condition.getRepairEndTimeRangeT1() != null) { // 维修结束时间
                wrapper.ge("repair_end_time", condition.getRepairEndTimeRangeT1());
            }

            if (condition.getRepairEndTimeRangeT2() != null) { // 维修结束时间
                wrapper.le("repair_end_time", condition.getRepairEndTimeRangeT2());
            }
        }

        // 默认按id降序
        wrapper.orderBy(true, false, "id");
        return wrapper;
    }

    /**
     * 设置list查询条件
     *
     * @param condition 搜索条件QueryModel
     * @return list查询wrapper
     */
    private QueryWrapper<EquipmentRepairRecord> getListWrapper(QueryModelEquipmentRepairRecord condition) {
        wrapper = new QueryWrapper<EquipmentRepairRecord>();
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
     *
     * @param list 实体list
     */
    private void dealWithViewName(List<EquipmentRepairRecord> list) {

        if (list != null && list.size() > 0) {
            for (EquipmentRepairRecord entity : list) {
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
    private boolean isCodeRepeat(EquipmentRepairRecord entity) {
        QueryWrapper<EquipmentRepairRecord> queryWrapper = new QueryWrapper<EquipmentRepairRecord>();
        queryWrapper.eq("is_deleted", false);
        queryWrapper.eq("code", entity.getCode());// 维修单号
        return mapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 对实体集合，判断业务主键是否重复，重复不允许插入
     *
     * @param list 实体list
     * @return 是否重复
     */
    private boolean isCodesRepeat(List<EquipmentRepairRecord> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }

        QueryWrapper<EquipmentRepairRecord> queryWrapper = new QueryWrapper<EquipmentRepairRecord>();
        queryWrapper.eq("is_deleted", false);
        List<String> codes = Linq.of(list).select(x -> x.getCode()).toList();
        queryWrapper.in("code", codes);
        return mapper.selectCount(queryWrapper) > 0;
    }
}
