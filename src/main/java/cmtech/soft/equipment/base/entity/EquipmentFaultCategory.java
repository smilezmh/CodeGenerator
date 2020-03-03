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
 * 设备故障类型
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EquipmentFaultCategory对象", description="设备故障类型")
public class EquipmentFaultCategory extends Model<EquipmentFaultCategory> {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "故障类别code")
    private String code;

    @ApiModelProperty(value = "故障类别name")
    private String name;

    @ApiModelProperty(value = "故障类型对应的处理方法")
    private String dealDesc;

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
