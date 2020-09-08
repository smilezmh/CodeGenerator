package cmtech.soft.equipment.utils.threadUtil;

import cmtech.soft.equipment.config.LogConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * 自定义线程池工厂，获得线程池
 */
@Component
public class CustomThreadPoolFactory implements AutoCloseable {
    static volatile CustomThreadPoolFactory customThreadPoolFactory;

    @Autowired
    LogConfig log;

    private  ExecutorService boundedService; // 有界队列

    private  ExecutorService unboundedThreadService;// 无界线程池

    private  ExecutorService unboundedQueueService;// 无界任务队列

    private CustomThreadPoolFactory() {
    }

    public static CustomThreadPoolFactory getPoolFactory() {
        if (customThreadPoolFactory == null) {
            synchronized (CustomThreadPoolFactory.class) {
                if (customThreadPoolFactory == null) {
                    customThreadPoolFactory = new CustomThreadPoolFactory();
                }
            }
        }

        return customThreadPoolFactory;
    }

    public synchronized  ExecutorService getBoundedService() {
        if (boundedService == null) {
            boundedService = new ThreadPoolExecutor(5, 50, 30000,
                    TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5000));
        }

        return boundedService;
    }

    public synchronized  ExecutorService getUnboundedThreadService() {
        if (unboundedThreadService == null) {
            unboundedThreadService =
                    new ThreadPoolExecutor(5, 999, 30000,
                            TimeUnit.MILLISECONDS, new SynchronousQueue<>());// 无界的线程
        }

        return unboundedThreadService;
    }

    public synchronized ExecutorService getUnboundedQueueService() {

        if (unboundedQueueService == null) {
            unboundedQueueService =
                    new ThreadPoolExecutor(5, 5, 30000,
                            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());// 无界任务队列
        }

        return unboundedQueueService;
    }

    @Override
    @PreDestroy
    public void close() {
        if (boundedService != null && !boundedService.isShutdown()) {
            boundedService.shutdown();
            log.logMethodWithContent("关闭有界线程池");
        }

        if (unboundedThreadService != null && !unboundedThreadService.isShutdown()) {
            unboundedThreadService.shutdown();
            log.logMethodWithContent("关闭无界线程线程池");
        }

        if (unboundedQueueService != null && !unboundedQueueService.isShutdown()) {
            unboundedQueueService.shutdown();
            log.logMethodWithContent("关闭无界队列线程池");
        }
    }
}

