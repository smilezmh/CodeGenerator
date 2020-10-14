package cmtech.soft.equipment.utils.commonUtil.threadUtil;

import cmtech.soft.biz.utils.commonUtil.logUtil.LogUtil;
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
    LogUtil log;

    private ExecutorService boundedService; // 有界队列

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

    public synchronized ExecutorService getBoundedService() {
        if (boundedService == null) {
            boundedService = new ThreadPoolExecutor(5, 50, 30000,
                    TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5000));
        }

        return boundedService;
    }

    @Override
    @PreDestroy
    public void close() {
        if (boundedService != null && !boundedService.isShutdown()) {
            boundedService.shutdown();
            log.info("关闭有界线程池");
        }
    }
}

