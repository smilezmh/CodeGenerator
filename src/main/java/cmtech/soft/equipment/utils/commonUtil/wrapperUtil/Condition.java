package cmtech.soft.equipment.utils.commonUtil.wrapperUtil;

import cmtech.soft.biz.utils.commonUtil.baseUtil.BindQuery;
import cmtech.soft.biz.utils.commonUtil.baseUtil.Comparison;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public abstract class Condition {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "id")
    @BindQuery(comparison = Comparison.IN, field = "id")
    private List<Integer> ids;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "当前页")
    @BindQuery(ignore = true)
    private Integer current = 1;

    @ApiModelProperty(value = "每页几条数据")
    @BindQuery(ignore = true)
    private Integer size = 20;

    @ApiModelProperty(value = "编码集合")
    @BindQuery(comparison = Comparison.IN, field = "code")
    private List<String> codes;
}
