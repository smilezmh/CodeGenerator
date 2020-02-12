package cmtech.soft.equipment.base.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EquipmentMaintenancePlan对象", description="保养计划表")
public class EquipmentMaintenancePlan extends Model<EquipmentMaintenancePlan> {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "保养计划单号")
    private String code;

    @ApiModelProperty(value = "冗余字段")
    private String name;

    @ApiModelProperty(value = "设备id，外链equipment_base_info的id")
    private Integer equipmentId;

    @ApiModelProperty(value = "设备资产编号，外链equipment_base_info的code")
    private String equipmentCode;

    @ApiModelProperty(value = "设备资产描述，外链equipment_base_info的desc")
    private String equipmentDesc;

    @ApiModelProperty(value = "设备出厂编号，外链equipment_base_info的factory_no")
    private String equipmentFactoryNo;

    @ApiModelProperty(value = "保养类型，日、周、月、季度、半年、年")
    private String maintenanceType;

    @ApiModelProperty(value = "保养内容，结果列")
    private String maintenanceContents;

    @ApiModelProperty(value = "计划开始保养时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maintenancePlanStartTime;

    @ApiModelProperty(value = "计划单是否有效")
    private Integer isValid;

    @ApiModelProperty(value = "是否允许推迟")
    private Integer isDelayAllowed;

    @ApiModelProperty(value = "推迟时间（天）")
    private Integer planDelay;

    @ApiModelProperty(value = "下次保养时间，服务计算，推送消息")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nextMaintenaceTime;

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
