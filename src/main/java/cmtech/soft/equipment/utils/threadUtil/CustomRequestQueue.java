package cmtech.soft.equipment.utils.threadUtil;

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
        if (size >= maxSize) {
            return;
        }

        requests.offer(customRequest);
        size = size + 1;
        notifyAll();
    }
}
