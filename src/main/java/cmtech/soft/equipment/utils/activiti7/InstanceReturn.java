package cmtech.soft.equipment.utils.activiti7;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="根据动流程实例id返回信息", description="根据动流程实例id返回信息")
public class InstanceReturn implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "流程实例是否已经结束")
    private boolean isComplete;

    @ApiModelProperty(value = "流程实例如未结束，返回当前任务集合")
    private List<Map<String, Object>> tasks;

    @ApiModelProperty(value = "流程实例如结束，返回当前任务开始时间")
    private Date startTime;

    @ApiModelProperty(value = "流程实例如结束，返回当前任务结束时间")
    private Date endTime;

    @ApiModelProperty(value = "流程实例如结束，返回当前任务发起人")
    private String startUser;

    @ApiModelProperty(value = "历史任务每一步的信息")
    private  List<HistoryActInfos> historyActs;

}


