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
 * 备件基础信息表
 * </p>
 *
 * @author smilezmh
 * @since 2020-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sparepart_base_info")
@ApiModel(value="SparepartBaseInfo对象", description="备件基础信息表")
public class SparepartBaseInfo extends Model<SparepartBaseInfo> {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "备件编码")
    private String code;

    @ApiModelProperty(value = "备件名")
    private String name;

    @ApiModelProperty(value = "备件id，外链sparepart_type的id")
    private Integer sparepartTypeId;

    @ApiModelProperty(value = "备件code，外链sparepart_type的code")
    private String sparepartTypeCode;

    @ApiModelProperty(value = "备件name，外链sparepart_type的name")
    private String sparepartTypeName;

    @ApiModelProperty(value = "单位,瓶，个，米，卷，桶，台，根，卷，套，对，把")
    private String unit;

    @ApiModelProperty(value = "实时库存")
    private Double storeNum;

    @ApiModelProperty(value = "安全库存")
    private Double safeNum;

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
