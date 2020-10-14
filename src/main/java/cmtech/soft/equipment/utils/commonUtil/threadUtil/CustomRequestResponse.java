package cmtech.soft.equipment.utils.commonUtil.threadUtil;

import lombok.Data;

/**
 * 前端发出的请求
 * @param <T> 请求返回数据
 */
@Data
public class CustomRequestResponse<T> {
    private String line;
    private T t;
    private String topic;

    public synchronized void setT(T t){
        this.t=t;
        notifyAll();
    }

    public synchronized T getT(){
        if(t==null){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return t;
    }
}
