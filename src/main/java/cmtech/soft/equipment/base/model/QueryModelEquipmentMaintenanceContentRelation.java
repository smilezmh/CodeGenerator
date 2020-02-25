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
@ApiModel(value="EquipmentMaintenanceContentRelation查询条件QueryModel", description="EquipmentMaintenanceContentRelation查询条件QueryModel")
public class QueryModelEquipmentMaintenanceContentRelation implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "当前页")
    private Integer current=1;

    @ApiModelProperty(value = "每页几条数据")
    private Integer size=10;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "保养内容编码,外链equipment_maintenance_content的code")
    private String code;

    @ApiModelProperty(value = "保养内容，外链equipment_maintenance_content中的name")
    private String name;

    @ApiModelProperty(value = "套餐编码")
    private String packageCode;

    @ApiModelProperty(value = "设备类型code，外链equipment_type类型code")
    private String equipmentTypeCode;

    @ApiModelProperty(value = "设备code，外链equipment_base_info的code（冗余）")
    private String equipmentCode;

    @ApiModelProperty(value = "保养类型-日、月、周、季度、半年、年")
    private String maintenanceType;
}
