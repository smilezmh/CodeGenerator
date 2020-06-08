package cmtech.soft.equipment.utils.activiti7;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "完成流程对象", description = "完成流程对象")
public class FlowComplete implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "任务执行者",required = true)
    private String assignee;
    @ApiModelProperty(value = "流程实例id")
    private String processInstanceId;
    @ApiModelProperty(value = "流程任务id")
    private String taskId;
    @ApiModelProperty(value = "本地自定义参数")
    private Map<String, Object> vars;
    @ApiModelProperty(value = "全局流程变量")
    private HashMap<String,Object> globalVars;
}
