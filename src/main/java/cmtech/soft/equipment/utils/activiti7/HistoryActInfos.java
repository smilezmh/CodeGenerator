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
@ApiModel(value="历史任务每一步的信息", description="历史任务每一步的信息")
public class HistoryActInfos implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "活动名称")
    private String activitiName;
    @ApiModelProperty(value = "执行人")
    private String assignee;
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
    @ApiModelProperty(value = "变量")
    private List<Map<String,Object>> varibles;
}
