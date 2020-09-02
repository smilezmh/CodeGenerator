package cmtech.soft.equipment.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@ApiModel(value="返回前端对象", description="返回前端对象")
@Data
@Component
@Scope("prototype")
public class HttpResultT<T> {

    @ApiModelProperty(value = "返回http码")
    private int code;

    @ApiModelProperty(value = "返回消息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public HttpResultT<T> ok(T t){
        this.code=HttpStatus.SC_OK;
        this.msg="返回成功";
        this.data=t;
        return this;
    }

    public HttpResultT<T> error(String msg){
        this.code=HttpStatus.SC_Allert;
        this.msg=msg;
        this.data=null;
        return this;
    }

    public HttpResultT<T> empty(String msg){
        this.code=HttpStatus.SC_NO_CONTENT;
        this.msg="返回为空";
        this.data=null;
        return this;
    }

}
