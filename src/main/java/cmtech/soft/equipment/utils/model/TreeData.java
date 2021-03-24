package cmtech.soft.equipment.utils.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 树结构返回数据
 * @param <T> entity实体
 */
@Data
public class TreeData<T> {
    @ApiModelProperty(value = "需要高亮显示的数据")
    private List<Integer> highLightList;
    @ApiModelProperty(value = "返回树结构数据")
    private List<T> list;
    @ApiModelProperty(value = "需要展开的列")
    private List<Integer> expandRowKeys;
}
