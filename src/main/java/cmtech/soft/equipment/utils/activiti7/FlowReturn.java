package cmtech.soft.equipment.utils.activiti7;

import cmtech.soft.equipment.utils.HttpResult;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="流程实例返回对象", description="流程实例返回对象")
public class FlowReturn implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "流程任务id")
    private String taskId;
    @ApiModelProperty(value = "流程实例id")
    private String processInstanceId;
    @ApiModelProperty(value = "流程定义id")
    private String processDefinitionId;
    @ApiModelProperty(value = "流程任务定义key")
    private String taskDefinitionKey;
    @ApiModelProperty(value = "流程任务定义name")
    private String name;
    @ApiModelProperty(value = "流程任务创建时间")
    private Date createTime;
    @ApiModelProperty(value = "流程自定义参数")
    private Map<String,Object> vars;

    public String getInstanceId(HttpResult httpResult){
        return Optional.ofNullable((FlowReturn) JSON.parseObject(JSON.toJSONString(httpResult.getData()).getBytes(), FlowReturn.class))
                .map(x -> x.getProcessInstanceId()).orElseGet(() -> "");
    }
}
