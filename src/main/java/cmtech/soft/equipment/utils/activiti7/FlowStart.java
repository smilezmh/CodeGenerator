package cmtech.soft.equipment.utils.activiti7;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="启动流程对象", description="启动流程对象")
public class FlowStart implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "流程id")
    private String flowId;
    @ApiModelProperty(value = "流程变量")
    private HashMap<String,Object> vars;
    @ApiModelProperty(value = "流程发起人工号")
    private String startPersonNo;
    @ApiModelProperty(value = "流程发起人名字")
    private String startPersonNameDetail;
    @ApiModelProperty(value = "单号，编码")
    private String no;
    @ApiModelProperty(value = "业务名")
    private String bizName;
}
