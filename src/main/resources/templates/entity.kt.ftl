package ${cfg.prefix}.base.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * <p>
 * 查询条件QueryModel
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="${entity}查询条件QueryModel", description="${entity}查询条件QueryModel")
public class QueryModel${entity} implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "当前页")
    private Integer current=1;

    @ApiModelProperty(value = "每页几条数据")
    private Integer size=20;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "带有父子级关系的默认第一次查询，查询最上级，parentId没有值")
    private Boolean topLevel;

    @ApiModelProperty(value = "是否得到子级树结构，树结构的数据包含children " +
            "children是一个list。hasChildren属性不设置，设置后和懒加载冲突，页面不能正常加载数据")
    private Boolean isTreeList;
}
