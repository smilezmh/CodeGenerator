package ${package.ServiceImpl};

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${cfg.prefix}.base.model.QueryModel${entity};
import ${cfg.prefix}.utils.MyStrTool;
import ${superServiceImplClassPackage};

import java.util.List;


/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Autowired
    ${entity}Mapper mapper;

    // 搜索条件
    private QueryWrapper<${entity}> wrapper;

    /**
    * 无分页查询list
    * @param condition 无分页条件
    * @return list
    */
    @Override
    public List<${entity}> getListByQueryModel(QueryModel${entity} condition) {
        wrapper = getListWrapper(condition);
        List<${entity}> list = list(wrapper);
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
    public IPage<${entity}> getPageByContition(QueryModel${entity} condition) {
        // 查询条件
        // QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        // 分页对象
        // IPage<${entity}> iPage = new Page<${entity}>();
        // iPage.setSize(condition.getSize());
        // iPage.setCurrent(condition.getCurrent());

        // 查询条件
        wrapper = getPageWrapper(condition);
        IPage<${entity}> iPage = new Page<${entity}>();
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
    public int saveOrUpdateWithIdReturnBack(${entity} entity) {
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
    private QueryWrapper<${entity}> getPageWrapper(QueryModel${entity} condition) {
        wrapper = new QueryWrapper<${entity}>();
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
    private QueryWrapper<${entity}> getListWrapper(QueryModel${entity} condition) {
        wrapper = new QueryWrapper<${entity}>();
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
    private void dealWithViewName(List<${entity}> list) {

        if (list != null && list.size() > 0) {
            for (${entity} entity : list) {
                // 重新赋值
                //entity.setEcnDetail(ecnDetail);
            }
        }
    }
}
</#if>
