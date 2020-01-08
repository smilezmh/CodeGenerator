package cmtech.soft.equipment.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value="错误类型", description="错误类型")
public class ErrorReturn{
    @ApiModelProperty(value = "主键重复")
    public static final Integer CodeRepete=-1;
    @ApiModelProperty(value = "参数不能为空")
    public static final Integer ParameterNotNull=-2;
    @ApiModelProperty(value = "参数类型不正确")
    public static final Integer ParameterType=-3;
}
