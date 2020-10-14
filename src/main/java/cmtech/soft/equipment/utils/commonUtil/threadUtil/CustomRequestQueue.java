package cmtech.soft.equipment.utils.commonUtil.threadUtil;


import java.util.LinkedList;
import java.util.Queue;

/**
 * 生产消费模式：请求队列
 */
public class CustomRequestQueue<T> {

    private Queue<T> requests = new LinkedList<>();

    private final Integer maxSize = 10000;

    // 实时数量
    private Integer size = 0;

    public synchronized T getRequest() {
        while (size <= 0) {

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        size = size - 1;
        T customRequestResponse = requests.poll();
        notifyAll();
        return customRequestResponse;
    }

    public synchronized void addRequest(T customRequest) {
        if (size >= maxSize) {// 不会造成线程wait。
            return;
        }

        size = size + 1;
        requests.offer(customRequest);
        notifyAll();
    }
}
