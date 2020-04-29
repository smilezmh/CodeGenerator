package cmtech.soft.equipment.base.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 设备基础信息主表
 * </p>
 *
 * @author smilezmh
 * @since 2020-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("equipment_base_info")
@ApiModel(value="EquipmentBaseInfo对象", description="设备基础信息主表")
public class EquipmentBaseInfo extends Model<EquipmentBaseInfo> {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "资产编号，财务,唯一可以作为业务主键")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "设备类型code,外链equipmen_type中的code")
    private String typeCode;

    @ApiModelProperty(value = "设备类型name,外链equipmen_type中的name")
    private String typeName;

    @ApiModelProperty(value = "位置编码,外链equipment_position中的code")
    private String positionCode;

    @ApiModelProperty(value = "位置,外链equipment_position中的name")
    private String positionName;

    @ApiModelProperty(value = "规格/型号")
    private String specification;

    @ApiModelProperty(value = "功率")
    private String power;

    @ApiModelProperty(value = "计量单位（台 套）")
    private String unit;

    @ApiModelProperty(value = "成本中心（设备的）")
    private String costCenterNo;

    @ApiModelProperty(value = "采购成本（原始价值，某些角色财务可以看到）")
    private String purchaseCost;

    @ApiModelProperty(value = "资产净值（财务才可以看到）")
    private String pureCost;

    @ApiModelProperty(value = "采购订单号")
    private String purchaseOrderNo;

    @ApiModelProperty(value = "制造厂商")
    private String manufacturer;

    @ApiModelProperty(value = "制造日期")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date madeDate;

    @ApiModelProperty(value = "启用日期")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date useDate;

    @ApiModelProperty(value = "保修期（月）")
    private Integer guaranteeTime;

    @ApiModelProperty(value = "保修电话，售后电话")
    private String afterSaleServiceTel;

    @ApiModelProperty(value = "出厂编号")
    private String factoryNo;

    @ApiModelProperty(value = "折旧年限，单位为年")
    private Integer depreciateTime;

    @ApiModelProperty(value = "状态（已验收/未验收/报废）")
    private String status;

    @ApiModelProperty(value = "资产挂账人（部门对应人）")
    private String putAccountPerson;

    @ApiModelProperty(value = "报废日期")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date discardDate;

    @ApiModelProperty(value = "资产描述")
    private String equipmentDesc;

    @ApiModelProperty(value = "照片存储位置")
    private String picUrl;

    @ApiModelProperty(value = "创建人id")
    private Integer createrId;

    @ApiModelProperty(value = "创建人")
    private String creater;

    @ApiModelProperty(value = "创建人id")
    private Integer modifierId;

    @ApiModelProperty(value = "修改人")
    private String modifier;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
