package cmtech.soft.equipment.base.model;

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
 * @author smilezmh
 * @since 2020-01-14
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EquipmentMaintenanceContent查询条件QueryModel", description="EquipmentMaintenanceContent查询条件QueryModel")
public class QueryModelEquipmentMaintenanceContent implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "当前页")
    private Integer current=1;

    @ApiModelProperty(value = "每页几条数据")
    private Integer size=10;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "设备code，外链equipment_base_info中的code（冗余）")
    private String equipmentCode;

    @ApiModelProperty(value = "设备类型code，外链equipment_type中的code(冗余字段)")
    private String equipmentTypeCode;
}
