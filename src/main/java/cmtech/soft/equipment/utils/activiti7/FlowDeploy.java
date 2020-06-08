package cmtech.soft.equipment.utils.activiti7;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="部署流程对象", description="部署流程对象")
public class FlowDeploy implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "部署的流程名")
    private String flowName;
    @ApiModelProperty(value = "流程bpmn名")
    private String flowBpmName;
    @ApiModelProperty(value = "流程xml名")
    private String flowXmlName;
}
