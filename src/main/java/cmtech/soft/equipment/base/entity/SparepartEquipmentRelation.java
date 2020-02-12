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
 * 
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SparepartEquipmentRelation对象", description="设备备件关系表")
public class SparepartEquipmentRelation extends Model<SparepartEquipmentRelation> {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "备件id，外链sparepart_base_info的id")
    private Integer sparepartId;

    @ApiModelProperty(value = "备件code,外链sparepart_base_info的code")
    private String sparepartCode;

    @ApiModelProperty(value = "备件name,外链sparepart_base_info的name")
    private String sparepartName;

    @ApiModelProperty(value = "设备id,外链equipment_base_info的id")
    private Integer equipmentId;

    @ApiModelProperty(value = "设备资产编号,外链equipment_base_info的code")
    private String equipmentCode;

    @ApiModelProperty(value = "设备名,外链equipment_base_info的name")
    private String equipmentName;

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

    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
