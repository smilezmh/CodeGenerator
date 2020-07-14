package cmtech.soft.equipment.utils.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "发送邮箱内容", description = "发送邮箱内容")
public class MailContent implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "目标邮件")
    private ArrayList<String> recipientTos;
    @ApiModelProperty(value = "发送邮件至")
    private String message;
    @ApiModelProperty(value = "主题")
    private String subject;
    @ApiModelProperty(value = "流程id")
    private String flowInstanceId;
    @ApiModelProperty(value = "业务类型")
    private String code;
}
