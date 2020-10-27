package cmtech.soft.equipment.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "返回前端对象", description = "返回前端对象")
@Data
public class HttpResultT<T> {

    @ApiModelProperty(value = "返回http码")
    private int code = HttpStatus.SC_OK;

    @ApiModelProperty(value = "返回消息")
    private String msg = "操作成功";

    @ApiModelProperty(value = "返回数据")
    private T data;

    public static <T> HttpResultT<T> create() {
        HttpResultT<T> httpResultT = new HttpResultT();
        return httpResultT;
    }

    public HttpResultT<T> build(T t) {
        this.data = t;
        return this;
    }

    public HttpResultT<T> build(String msg) {
        this.msg = msg;
        return this;
    }

    public HttpResultT<T> build(int code) {
        this.code = code;
        return this;
    }

    public HttpResultT<T> ok(T t) {
        this.code = HttpStatus.SC_OK;
        this.msg = "操作成功";
        this.data = t;
        return this;
    }

    public HttpResultT<T> ok(T t, String msg) {
        this.code = HttpStatus.SC_OK;
        this.msg = msg;
        this.data = t;
        return this;
    }

    public HttpResultT<T> error(String msg) {
        this.code = HttpStatus.SC_Allert;
        this.msg = msg;
        this.data = null;
        return this;
    }

    public HttpResultT<T> error() {
        this.code = HttpStatus.SC_Allert;
        this.msg = "操作失败";
        this.data = null;
        return this;
    }

    public HttpResultT<T> empty() {
        this.code = HttpStatus.SC_NO_CONTENT;
        this.msg = "返回为空";
        this.data = null;
        return this;
    }
}
