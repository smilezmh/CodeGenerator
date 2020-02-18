package cmtech.soft.equipment.base.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

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
@ApiModel(value="EquipmentRepairRecord查询条件QueryModel", description="EquipmentRepairRecord查询条件QueryModel")
public class QueryModelEquipmentRepairRecord implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "当前页")
    private Integer current=1;

    @ApiModelProperty(value = "每页几条数据")
    private Integer size=10;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "维修单号，业务主键")
    private String code;

    @ApiModelProperty(value = "设备资产编号,外链equipment_base_info的code")
    private String equipmentCode;

    @ApiModelProperty(value = "保修人编号")
    private String repairApplicantNo;

    @ApiModelProperty(value = "保修人电话")
    private String repairApplicantTel;

    @ApiModelProperty(value = "设备位置code")
    private String equipmentPosCode;

    @ApiModelProperty(value = "紧急程度code")
    private String emergencyCode;

    @ApiModelProperty(value = "故障类别code,外链equipment_fault_category中的code")
    private String faultCategoryCode;

    @ApiModelProperty(value = "故障描述")
    private String faultDesc;

    @ApiModelProperty(value = "维修人工号")
    private String repairPersonNo;

    @ApiModelProperty(value = "故障原因分析")
    private String repeirResonAnalyse;

    @ApiModelProperty(value = "维修内容记录")
    private String repairContent;

    @ApiModelProperty(value = "转外部维修单号")
    private String outerRepairNo;

    @ApiModelProperty(value = "送修时间搜索范围开始时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendRepairTimeRangeT1;

    @ApiModelProperty(value = "送修时间搜索范围结束时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendRepairTimeRangeT2;

    @ApiModelProperty(value = "维修开始时间范围开始时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date repairStartTimeRangeT1;

    @ApiModelProperty(value = "维修开始时间范围结束时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date repairStartTimeRangeT2;

    @ApiModelProperty(value = "维修结束时间范围开始时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date repairEndTimeRangeT1;

    @ApiModelProperty(value = "维修结束时间范围结束时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date repairEndTimeRangeT2;

    @ApiModelProperty(value = "设备出厂编码，外链equipment_base_info的factory_no")
    private String equipmentFactoryNo;

    @ApiModelProperty(value = "设备资产描述，外链equipment_base_info的desc")
    private String equipmentDesc;

}
