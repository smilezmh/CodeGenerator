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
 * @since 2020-01-10
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EquipmentSlaveInfo查询条件QueryModel", description="EquipmentSlaveInfo查询条件QueryModel")
public class QueryModelEquipmentSlaveInfo implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "当前页")
    private Integer current=1;

    @ApiModelProperty(value = "每页几条数据")
    private Integer size=10;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "主设备id，外链equipment_base_info中的id")
    private Integer equipmentId;

    @ApiModelProperty(value = "主设备资产编号,外链equipment_base_info中的code")
    private String equipmentCode;

    @ApiModelProperty(value = "子设备资产编码")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "规格/型号")
    private String specification;

    @ApiModelProperty(value = "出厂编号")
    private String factoryNo;
}
