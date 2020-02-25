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
 * @since 2020-01-14
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EquipmentMaintenanceRecord查询条件QueryModel", description="EquipmentMaintenanceRecord查询条件QueryModel")
public class QueryModelEquipmentMaintenanceRecord implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "当前页")
    private Integer current=1;

    @ApiModelProperty(value = "每页几条数据")
    private Integer size=10;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "保养单号")
    private String code;

    @ApiModelProperty(value = "设备资产编号，外链equipment_base_info的code")
    private String equipmentCode;

    @ApiModelProperty(value = "设备详情")
    private String equipmentDetail;

    @ApiModelProperty(value = "保养类型，日、周、月、季度、半年、年")
    private String maintenanceType;

    @ApiModelProperty(value = "保养内容，结果列")
    private String maintenanceContents;

    @ApiModelProperty(value = "保养计划单号，外链equipment_maintenance_plan中的code")
    private String maitenancePlanNo;

    @ApiModelProperty(value = "保养开始范围开始时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maintenanceStartTimeRangeT1;

    @ApiModelProperty(value = "保养开始范围结束时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maintenanceStartTimeRangeT2;

    @ApiModelProperty(value = "保养结束范围开始时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maintenanceEndTimeRangeT1;

    @ApiModelProperty(value = "保养结束范围结束时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maintenanceEndTimeRangeT2;
}
