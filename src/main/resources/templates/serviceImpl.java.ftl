package ${package.ServiceImpl};

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bestvike.linq.Linq;

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${cfg.prefix}.base.model.QueryModel${entity};
import ${cfg.prefix}.base.model.excelModel.${entity}Excel;
import ${cfg.prefix}.utils.MyStrTool;
import ${cfg.prefix}.utils.MyConvert;
import ${superServiceImplClassPackage};
import ${cfg.prefix}.utils.model.TreeData;
import ${cfg.prefix}.utils.model.ErrorReturn;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * ${table.comment!} 服务实现类
 *
 * @author ${author}
 * @since ${date}
 */
@Service("${entity}Basic")
@Primary
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Autowired
    ${entity}Mapper mapper;

    // 搜索条件
    private QueryWrapper<${entity}> wrapper;

    private List<${entity}> allList = new ArrayList<>();

    /**
     * 生成单号
     * @param prefix  前缀
     * @param colName 检索的数据列名
     * @param noLength 最后位数的长度
     * @param isRelatedToDate 是否和日期相关
     * @return 单号
     */
    @Override
    public String generateNo(String prefix,String colName,Integer noLength,Boolean isRelatedToDate) {
        List<Map<String, Object>> list = MyConvert.listEntityToListMap(getListByQueryModel(new QueryModel${entity}()));
        return MyStrTool.getNo(prefix, list, colName, noLength,isRelatedToDate);
    }

     /**
     * 根据条件获得导出excel数据
     *
     * @param condition 查询条件
     * @return excel数据
     */
    @Override
    public List<${entity}Excel> getExcelListByQueryModel(QueryModel${entity} condition) {
        wrapper = getListWrapper(condition);
        List<${entity}> list = list(wrapper);
        List<${entity}Excel> listReturn = new ArrayList<>();

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                ${entity} entity = list.get(i);
                if (entity != null) {
                    ${entity}Excel excel = new ${entity}Excel();
                    BeanUtils.copyProperties(entity,excel);
                    excel.setXid(i+1);
                    listReturn.add(excel);
                }
            }
        }
        return listReturn;
    }

    /**
    * 无分页查询list
    * @param condition 无分页条件
    * @return list
    */
    @Override
    public List<${entity}> getListByQueryModel(QueryModel${entity} condition) {
        // 联表查询
        // return cityMapper.GetTwoTableInfo();

        wrapper = getListWrapper(condition);
        List<${entity}> list = list(wrapper);
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
    public ${entity} getRandomOneByQueryModel(QueryModel${entity} condition){
        QueryWrapper<${entity}> queryWrapper=new QueryWrapper<${entity}>();
        queryWrapper.eq("is_deleted",false);
        queryWrapper.lambda().eq(${entity}::getId,condition.getId());
        queryWrapper.last("LIMIT 1");
        return mapper.selectOne(queryWrapper);
     }

    /**
    * 根据条件查询分页结果
    * @param condition 查询条件
    * @return 分页结果
    */
    @Override
    public IPage<${entity}> getPageByContition(QueryModel${entity} condition) {
        // 查询条件
        // QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        // 分页对象
        // IPage<${entity}> iPage = new Page<${entity}>();
        // iPage.setSize(condition.getSize());
        // iPage.setCurrent(condition.getCurrent());

        // 连表查询
        // Page<> pagecity=new Page<>(page,limit);
        // QueryWrapper<> wrapper=new QueryWrapper<>();
        // wrapper.like("city.name","Ka");
        // pagecity.setRecords(cityMapper.GetTwoTableInfoPage(pagecity,wrapper));

        // 查询条件
        wrapper = getPageWrapper(condition);
        IPage<${entity}> iPage = new Page<${entity}>();
        // 设置当前页
        iPage.setCurrent(condition.getCurrent());
        // 设置每页几条数据
        iPage.setSize(condition.getSize());

        // 查询需要的结果列
        // wrapper.select("id", "code", "name", "remark", "responsible_name", "customer");

        //if (!MyStrTool.isNullOrEmpty(condition.getCode())) {
        //    wrapper.eq("code", condition.getCode());
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
    public int saveOrUpdateWithIdReturnBack(${entity} entity) {
        int id = 0;

        if (entity.getId() == null) {
            //if (isCodeRepeat(entity)) { // 业务主键重复
            //    return ErrorReturn.CodeRepete;
            //}

            id = mapper.insert(entity);

            if(id>0){
                QueryModel${entity} conditionForId=new QueryModel${entity}(){{setCode(entity.getCode());}};
                QueryWrapper<${entity}> queryWrapperForIdSearch=new QueryWrapper<>();
                setWrapper(conditionForId,queryWrapperForIdSearch);
                ${entity} foundEntity=getOne(queryWrapperForIdSearch, false);
                id=foundEntity.getId();
            }
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
    public Integer insertBatchWithCodeRepeatCheck(List<${entity}> entities) {
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
    public boolean isExistsByQueryModel(QueryModel${entity} condition) {
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        setWrapper(condition, queryWrapper);
        return mapper.selectCount(queryWrapper) > 0;
    }

    /**
    * 根据更新条件更新了几条数据
    *
    * @param condition 跟更新条件
    * @return 更新了几条数据
    */
    @Override
    public Integer updateByQueryModel(${entity} entity , QueryModel${entity} condition) {
        // entity.setIsDeleted(true);

        UpdateWrapper<${entity}> updateWrapper = new UpdateWrapper<${entity}>();
        updateWrapper.lambda().eq(${entity}::getId, condition.getId());
        int num = mapper.update(entity, updateWrapper);
        return num;
    }

    /**
    * 根据条件串表分页查询
    * @param condition 查询条件
    * @return 分页结果
    */
    @Override
    public IPage<${entity}> getTablesPageByContition(QueryModel${entity} condition){
        IPage<${entity}> page=new Page<${entity}>(condition.getCurrent(),condition.getSize());
        return page.setRecords(mapper.getTablesByContition(page,condition));
    }

    /**
    * 根据条件串表不分页查询list
    * @param condition 查询条件
    * @return 分页结果
    */
    @Override
    public List<${entity}> getTablesByContition(QueryModel${entity} condition){
        return mapper.getTablesByContition(condition);
    }

    /**
    * 设置查询条件
    * @param condition 查询model
    * @param wrapper 查询条件
    */
    protected void setWrapper(QueryModel${entity} condition,QueryWrapper<${entity}> wrapper){
        if(!MyStrTool.isNullOrEmpty(condition.getCode())){
            wrapper.lambda().eq(${entity}::getCode,condition.getCode());
        }
    }

    /**
    * 设置分页查询条件
    * @param condition 搜索条件QueryModel
    * @return 分页查询wrapper
    */
    protected QueryWrapper<${entity}> getPageWrapper(QueryModel${entity} condition) {
        wrapper = new QueryWrapper<${entity}>();
        // 查询需要的结果列
        // queryWrapper.select("id", "code", "name", "remark");
        setWrapper(condition,wrapper);
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
    protected QueryWrapper<${entity}> getListWrapper(QueryModel${entity} condition) {
        wrapper = new QueryWrapper<${entity}>();
        // 查询需要的结果列
        // queryWrapper.select("id", "code", "name", "remark");
        setWrapper(condition,wrapper);
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
    private void dealWithViewName(List<${entity}> list) {

        if (list != null && list.size() > 0) {
            for (${entity} entity : list) {
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
    private boolean isCodeRepeat(${entity} entity) {
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<${entity}>();
        queryWrapper.eq("is_deleted", false);
        //queryWrapper.eq("code", entity.getCode());
        return mapper.selectCount(queryWrapper) > 0;
    }

    /**
    * 对实体集合，判断业务主键是否重复，重复不允许插入
    * @param list 实体list
    * @return 是否重复
    */
    private boolean isCodesRepeat(List<${entity}> list) {
        if(list==null||list.isEmpty()){
            return false;
        }

        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<${entity}>();
        queryWrapper.eq("is_deleted", false);
        List<String> codes= Linq.of(list).select(x->x.getCode()).toList();
        queryWrapper.in("code", codes);
        return mapper.selectCount(queryWrapper) > 0;
     }

    /**
     * 有子级关系的根据条件查询
     *
     * @param condition 查询条件
     * @return 查询集合
     */
    @Override
    public TreeData<${entity}> getListHasChildrenByContition(QueryModel${entity} condition) {
        TreeData<${entity}> treeData = new TreeData<>();
        List<${entity}> returnList = new ArrayList<>();
        treeData.setHighLightList(new ArrayList<>());
        // 查询条件
        wrapper = getListWrapper(condition);

        if (condition.getTopLevel() != null && condition.getTopLevel().booleanValue()) {// 首次查询是顶级数据查询，懒加载
            wrapper.lambda().isNull(${entity}::getParentId);
            returnList = list(wrapper);
            // 是否有子级属性 进行赋值
            setHasChildrenAttr(returnList, getAll());
        } else {// 普通查询，查询的时候将所有上级关系也一同加载，树结构的数据，如果加载树结构就不能设置hasChildren属性，否则前端无效
            List<${entity}> returnListBefore = new ArrayList<>();

            List<${entity}> list = list(wrapper);
            treeData.setHighLightList(list);
            returnListBefore = getAllSuplist(list);// 父级元素集合,包括自身
            returnListBefore = returnListBefore.stream().distinct().collect(Collectors.toList());//去重

            if (condition.getIsTreeList() != null && condition.getIsTreeList().booleanValue()) {
                //setHasChildrenAttr(returnListBefore, getAll());// 赋值是否有子级,如果加载树结构就不能设置hasChildren属性，否则前端无效
                // 加工数据：从上级加载到下级的结构
                for (${entity} entity : returnListBefore) {
                    if (entity.getParentId() == null) {
                        setSubList(entity, returnListBefore);// 设置子级树
                        returnList.add(entity);
                    }
                }

                returnListBefore.removeAll(list);// 差集

                treeData.setExpandRowKeys(Linq.of(returnListBefore).select(x -> x.getId()).toList());// 数据的上级都是需要展开的列（差集）
            } else {
                // 这种方式顺序可能发生改变
//                Set set = new HashSet();
//                set.addAll(returnListBefore);
//                returnList.addAll(set);
                returnList = returnListBefore;
            }
        }
        // 处理结果列
//         dealWithViewName(iPage.getRecords());
        treeData.setList(returnList);
        return treeData;
    }

    /**
     * 递归设置实体数据的子级树
     *
     * @param entity 实体
     * @param list   相关数据
     */
    private void setSubList(${entity} entity, List<${entity}> list) {
        List<${entity}> subList = setNextSubList(entity, list);

        for (${entity} entity1 : subList) {
            setSubList(entity1, list);
        }
    }

    /**
     * 设置实体数据并得到其子级
     *
     * @param entity 实体
     * @param list   实体和其相关子级数（包含自身）据集合
     * @return 其子级数据
     */
    private List<${entity}> setNextSubList(${entity} entity, List<${entity}> list) {
        List<${entity}> sublist = new ArrayList<>();

        sublist = Linq.of(list).where(x -> x.getParentId() != null).
                where(t -> t.getParentId().intValue() == entity.getId().intValue()).toList();

        if (!sublist.isEmpty()) {
            // 寻找子级
            entity.setChildren(sublist);
        }

        return sublist;
    }

    /**
     * 根据id获取下一级子级数据
     *
     * @param id 实体id
     * @return 获取下一级子级数据
     */
    @Override
    public List<${entity}> getSubList(Integer id) {
        List<${entity}> subList = new ArrayList<>();
        // 所有数据
        allList = getAll();

        if (hasChildren(id, allList)) {
            QueryWrapper<${entity}> queryWrapper = new QueryWrapper<${entity}>();
            queryWrapper.lambda().eq(${entity}::getIsDeleted, false);
            queryWrapper.lambda().eq(${entity}::getParentId, id);
            subList = list(queryWrapper);
            setHasChildrenAttr(subList, allList);
        }

        return subList;
    }

    /**
     * 获取一个集合的所有上级数据，包括自身
     *
     * @param list 集合
     * @return 一个集合的所有上级数据，包括自身
     */
    protected List<${entity}> getAllSuplist(List<${entity}> list) {
        List<${entity}> returnList = new ArrayList<>();

        for (${entity} entity : list) {
            // 上一级数据
            ${entity} parent = new ${entity}();
            // 先将自身加入到集合
            returnList.add(entity);
            if (entity.getParentId() != null) {
                // 上一级父级数据
                parent = getNextSupOne(entity.getParentId());
                returnList.add(parent);
            }

            while (parent.getParentId() != null) {
                // 上级的上级数据
                parent = getNextSupOne(parent.getParentId());
                returnList.add(parent);
            }
        }

        // 反转集合，从父级到子集
        Collections.reverse(returnList);

        return returnList;
    }

    /**
     * 获取上级对象
     *
     * @param parentId 实体的父id
     * @return 上级对象
     */
    protected ${entity} getNextSupOne(Integer parentId) {
        ${entity} one = new ${entity}();

        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();

        if (parentId != null) {
            queryWrapper.lambda().eq(${entity}::getId, parentId.intValue());
            queryWrapper.lambda().eq(${entity}::getIsDeleted, false);
            one = getOne(queryWrapper, false);
        }

        return one;
    }

    /**
     * 获取所有数据 id parentId
     *
     * @return 所有数据集合
     */
    protected List<${entity}> getAll() {
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<${entity}>();
        queryWrapper.lambda().eq(${entity}::getIsDeleted, false);
        queryWrapper.lambda().select(${entity}::getId, ${entity}::getParentId);
        // 所有数据
        allList = list(queryWrapper);
        return allList;
    }

    /**
     * 对传入的数据的是否有子级属性进行赋值
     *
     * @param list    传入集合
     * @param allList 所有数据
     */
    protected void setHasChildrenAttr(List<${entity}> list, List<${entity}> allList) {
        if (list != null && list.size() > 0) {
            for (${entity} entity : list) {
                // 找到有没有子级
                if (hasChildren(entity.getId(), allList)) {//找到子级
                    entity.setHasChildren(true);
                }
            }
        }
    }

    /**
     * 是否有子级
     *
     * @param id      实体id
     * @param allList 全体集合
     * @return 是否有子级
     */
    protected boolean hasChildren(Integer id, List<${entity}> allList) {
        boolean hasChildren = false;

        for (${entity} entity : allList) {
            if (id != null && entity.getParentId() != null && (id.intValue() == entity.getParentId().intValue())) {
                hasChildren = true;
                break;
            }
        }

        return hasChildren;
    }
}
</#if>
