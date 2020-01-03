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
    private Integer size=10;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;
}
