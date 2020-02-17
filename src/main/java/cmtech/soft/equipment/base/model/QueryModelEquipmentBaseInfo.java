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
@ApiModel(value="EquipmentBaseInfo查询条件QueryModel", description="EquipmentBaseInfo查询条件QueryModel")
public class QueryModelEquipmentBaseInfo implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "当前页")
    private Integer current=1;

    @ApiModelProperty(value = "每页几条数据")
    private Integer size=10;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "资产编号")
    private String code;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "设备类型code,外链equipmen_type中的code")
    private String typeCode;

    @ApiModelProperty(value = "位置编码,外链equipment_position中的code")
    private String positionCode;

    @ApiModelProperty(value = "规格/型号")
    private String specification;

    @ApiModelProperty(value = "成本中心（设备的）")
    private String costCenterNo;

    @ApiModelProperty(value = "采购订单号")
    private String purchaseOrderNo;

    @ApiModelProperty(value = "状态（已验收/未验收/报废）")
    private String status;

    @ApiModelProperty(value = "资产挂账人（部门对应人）")
    private String putAccountPerson;

    @ApiModelProperty(value = "资产描述")
    private String equipmentDesc;
}
