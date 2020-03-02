package cmtech.soft.equipment.base.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * 设备保养内容和设备保养类型、设备类型关系维护表
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EquipmentMaintenanceContentRelation对象", description="设备保养内容和设备保养类型、设备类型关系维护表")
@TableName("equipment_maintenance_content_relation")
public class EquipmentMaintenanceContentRelation extends Model<EquipmentMaintenanceContentRelation> {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "套餐编码")
    private String packageCode;

    @ApiModelProperty(value = "保养内容id，外链equipment_maintenance_content的id")
    private Integer maintenanceContentId;

    @ApiModelProperty(value = "保养内容编码,外链equipment_maintenance_content的code")
    private String code;

    @ApiModelProperty(value = "保养内容，外链equipment_maintenance_content中的name")
    private String name;

    @ApiModelProperty(value = "保养类型-日、月、周、季度、半年、年")
    private String maintenanceType;

    @ApiModelProperty(value = "设备类型id，外链equipment_type类型id")
    private Integer equipmentTypeId;

    @ApiModelProperty(value = "设备类型code，外链equipment_type类型code")
    private String equipmentTypeCode;

    @ApiModelProperty(value = "设备类型name，外链equipment_type类型name")
    private String equipmentTypeName;

    @ApiModelProperty(value = "设备id，外链equipment_base_info的id（冗余）")
    private Integer equipmentId;

    @ApiModelProperty(value = "设备code，外链equipment_base_info的code（冗余）")
    private String equipmentCode;

    @ApiModelProperty(value = "设备name，外链equipment_base_info的name")
    private String equipmentName;

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
