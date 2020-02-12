package cmtech.soft.equipment.base.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 设备维修记录表
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EquipmentRepairRecord对象", description="设备维修记录表")
public class EquipmentRepairRecord extends Model<EquipmentRepairRecord> {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "维修单号，业务主键")
    private String code;

    @ApiModelProperty(value = "设备id,外链equipment_base_info的id")
    private Integer equipmentId;

    @ApiModelProperty(value = "设备资产编号,外链equipment_base_info的code")
    private String equipmentCode;

    @ApiModelProperty(value = "设备名,外链equipment_base_info的name")
    private String equipmentName;

    @ApiModelProperty(value = "保修人id")
    private Integer repairApplicantId;

    @ApiModelProperty(value = "保修人编号")
    private String repairApplicantNo;

    @ApiModelProperty(value = "保修人名字")
    private String repairApplicantName;

    @ApiModelProperty(value = "保修人电话")
    private String repairApplicantTel;

    @ApiModelProperty(value = "设备位置id")
    private Integer equipmentPosId;

    @ApiModelProperty(value = "设备位置code")
    private String equipmentPosCode;

    @ApiModelProperty(value = "设备位置name")
    private String equipmentPosName;

    @ApiModelProperty(value = "紧急程度id")
    private Integer emergencyId;

    @ApiModelProperty(value = "紧急程度code")
    private String emergencyCode;

    @ApiModelProperty(value = "紧急程度name")
    private String emergencyName;

    @ApiModelProperty(value = "故障类别id,外链equipment_fault_category中的id")
    private Integer faultCategoryId;

    @ApiModelProperty(value = "故障类别code,外链equipment_fault_category中的code")
    private String faultCategoryCode;

    @ApiModelProperty(value = "故障类别name,外链equipment_fault_category中的name")
    private String faultCategoryName;

    @ApiModelProperty(value = "故障描述")
    private String faultDesc;

    @ApiModelProperty(value = "维修人id")
    private Integer repairPersonId;

    @ApiModelProperty(value = "维修人工号")
    private String repairPersonNo;

    @ApiModelProperty(value = "维修人名字")
    private String repairPersonName;

    @ApiModelProperty(value = "故障原因分析")
    private String repeirResonAnalyse;

    @ApiModelProperty(value = "维修内容记录")
    private String repairContent;

    @ApiModelProperty(value = "转外部维修单号")
    private String outerRepairNo;

    @ApiModelProperty(value = "送修时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendRepairTime;

    @ApiModelProperty(value = "维修开始时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date repairStartTime;

    @ApiModelProperty(value = "维修结束时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date repairEndTime;

    @ApiModelProperty(value = "设备出厂编码，外链equipment_base_info的factory_no")
    private String equipmentFactoryNo;

    @ApiModelProperty(value = "设备资产描述，外链equipment_base_info的desc")
    private String equipmentDesc;

    @ApiModelProperty(value = "创建人id")
    private Integer createrId;

    @ApiModelProperty(value = "创建人")
    private String creater;

    @ApiModelProperty(value = "修改人id")
    private Long modifierId;

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
