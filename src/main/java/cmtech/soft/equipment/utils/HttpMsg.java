package cmtech.soft.equipment.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="接口中间返回结果", description="接口中间返回结果")
public class HttpMsg<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "范型")
    private T t;
    @ApiModelProperty(value = "信息")
    private String msg;
}
