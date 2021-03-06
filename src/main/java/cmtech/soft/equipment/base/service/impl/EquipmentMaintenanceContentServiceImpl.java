package cmtech.soft.equipment.base.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bestvike.linq.Linq;

import cmtech.soft.equipment.base.entity.EquipmentMaintenanceContent;
import cmtech.soft.equipment.base.mapper.EquipmentMaintenanceContentMapper;
import cmtech.soft.equipment.base.service.IEquipmentMaintenanceContentService;
import cmtech.soft.equipment.base.model.QueryModelEquipmentMaintenanceContent;
import cmtech.soft.equipment.utils.MyStrTool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cmtech.soft.equipment.utils.model.ErrorReturn;

import java.util.List;


/**
 * <p>
 * 设备保养内容 服务实现类
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-14
 */
@Service
public class EquipmentMaintenanceContentServiceImpl extends ServiceImpl<EquipmentMaintenanceContentMapper, EquipmentMaintenanceContent> implements IEquipmentMaintenanceContentService {

    @Autowired
    EquipmentMaintenanceContentMapper mapper;

    // 搜索条件
    private QueryWrapper<EquipmentMaintenanceContent> wrapper;

    /**
     * 无分页查询list
     *
     * @param condition 无分页条件
     * @return list
     */
    @Override
    public List<EquipmentMaintenanceContent> getListByQueryModel(QueryModelEquipmentMaintenanceContent condition) {
        // 联表查询
        // return cityMapper.GetTwoTableInfo();

        wrapper = getListWrapper(condition);
        List<EquipmentMaintenanceContent> list = list(wrapper);
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
    public EquipmentMaintenanceContent getRandomOneByQueryModel(QueryModelEquipmentMaintenanceContent condition) {
        QueryWrapper<EquipmentMaintenanceContent> queryWrapper = new QueryWrapper<EquipmentMaintenanceContent>();
        queryWrapper.eq("is_deleted", false);
        queryWrapper.lambda().eq(EquipmentMaintenanceContent::getId, condition.getId());
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
    public IPage<EquipmentMaintenanceContent> getPageByContition(QueryModelEquipmentMaintenanceContent condition) {
        // 查询条件
        // QueryWrapper<EquipmentMaintenanceContent> queryWrapper = new QueryWrapper<>();
        // 分页对象
        // IPage<EquipmentMaintenanceContent> iPage = new Page<EquipmentMaintenanceContent>();
        // iPage.setSize(condition.getSize());
        // iPage.setCurrent(condition.getCurrent());

        // 连表查询
        // Page<> pagecity=new Page<>(page,limit);
        // QueryWrapper<> wrapper=new QueryWrapper<>();
        // wrapper.like("city.name","Ka");
        // pagecity.setRecords(cityMapper.GetTwoTableInfoPage(pagecity,wrapper));

        // 查询条件
        wrapper = getPageWrapper(condition);
        IPage<EquipmentMaintenanceContent> iPage = new Page<EquipmentMaintenanceContent>();
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
    public int saveOrUpdateWithIdReturnBack(EquipmentMaintenanceContent entity) {
        int id = 0;

        if (entity.getId() == null) {
            if (isCodeRepeat(entity)) { // 业务主键重复,保养内容编码不能重复
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
    public Integer insertBatchWithCodeRepeatCheck(List<EquipmentMaintenanceContent> entities) {
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
    public boolean isExistsByQueryModel(QueryModelEquipmentMaintenanceContent condition) {
        QueryWrapper<EquipmentMaintenanceContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EquipmentMaintenanceContent::getId, condition.getId());
        return mapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 根据更新条件更新了几条数据
     *
     * @param condition 跟更新条件
     * @return 更新了几条数据
     */
    @Override
    public Integer updateByQueryModel(QueryModelEquipmentMaintenanceContent condition) {
        EquipmentMaintenanceContent entity = new EquipmentMaintenanceContent();
        entity.setIsDeleted(true);

        UpdateWrapper<EquipmentMaintenanceContent> updateWrapper = new UpdateWrapper<EquipmentMaintenanceContent>();
        updateWrapper.lambda().eq(EquipmentMaintenanceContent::getId, condition.getId());
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
    public IPage<EquipmentMaintenanceContent> getTablesPageByContition(QueryModelEquipmentMaintenanceContent condition) {
        IPage<EquipmentMaintenanceContent> page = new Page<EquipmentMaintenanceContent>(condition.getCurrent(), condition.getSize());
        return page.setRecords(mapper.getTablesByContition(page, condition));
    }

    /**
     * 根据条件串表不分页查询list
     *
     * @param condition 查询条件
     * @return 分页结果
     */
    @Override
    public List<EquipmentMaintenanceContent> getTablesByContition(QueryModelEquipmentMaintenanceContent condition) {
        return mapper.getTablesByContition(condition);
    }

    /**
     * 设置分页查询条件
     *
     * @param condition 搜索条件QueryModel
     * @return 分页查询wrapper
     */
    private QueryWrapper<EquipmentMaintenanceContent> getPageWrapper(QueryModelEquipmentMaintenanceContent condition) {
        wrapper = new QueryWrapper<EquipmentMaintenanceContent>();
        // 查询需要的结果列
        // queryWrapper.select("id", "code", "name", "remark");

        if (!MyStrTool.isNullOrEmpty(condition.getCode())) {
            wrapper.like("code", condition.getCode());
        }

        if (!MyStrTool.isNullOrEmpty(condition.getName())) {
            wrapper.like("name", condition.getName());
        }

        if (!MyStrTool.isNullOrEmpty(condition.getEquipmentTypeCode())) {
            wrapper.eq("equipment_type_code", condition.getEquipmentTypeCode());
        }

        if (!MyStrTool.isNullOrEmpty(condition.getEquipmentCode())) {
            wrapper.eq("equipment_code", condition.getEquipmentCode());
        }

        // 查找没有删除的数据
        wrapper.eq("is_deleted", false);
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
    private QueryWrapper<EquipmentMaintenanceContent> getListWrapper(QueryModelEquipmentMaintenanceContent condition) {
        wrapper = new QueryWrapper<EquipmentMaintenanceContent>();
        // 查询需要的结果列
        // queryWrapper.select("id", "code", "name", "remark");
        if (!MyStrTool.isNullOrEmpty(condition.getCode())) {
            wrapper.eq("code", condition.getCode());
        }

        if (!MyStrTool.isNullOrEmpty(condition.getName())) {
            wrapper.like("name", condition.getName());
        }

        if (!MyStrTool.isNullOrEmpty(condition.getEquipmentTypeCode())) {
            wrapper.eq("equipment_type_code", condition.getEquipmentTypeCode());
        }

        if (!MyStrTool.isNullOrEmpty(condition.getEquipmentCode())) {
            wrapper.eq("equipment_code", condition.getEquipmentCode());
        }
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
    private void dealWithViewName(List<EquipmentMaintenanceContent> list) {

        if (list != null && list.size() > 0) {
            for (EquipmentMaintenanceContent entity : list) {
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
    private boolean isCodeRepeat(EquipmentMaintenanceContent entity) {
        QueryWrapper<EquipmentMaintenanceContent> queryWrapper = new QueryWrapper<EquipmentMaintenanceContent>();
        queryWrapper.eq("is_deleted", false);
        queryWrapper.eq("code", entity.getCode());
        return mapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 对实体集合，判断业务主键是否重复，重复不允许插入
     *
     * @param list 实体list
     * @return 是否重复
     */
    private boolean isCodesRepeat(List<EquipmentMaintenanceContent> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }

        QueryWrapper<EquipmentMaintenanceContent> queryWrapper = new QueryWrapper<EquipmentMaintenanceContent>();
        queryWrapper.eq("is_deleted", false);
        List<String> codes = Linq.of(list).select(x -> x.getCode()).toList();
        queryWrapper.in("code", codes);
        return mapper.selectCount(queryWrapper) > 0;
    }
}
