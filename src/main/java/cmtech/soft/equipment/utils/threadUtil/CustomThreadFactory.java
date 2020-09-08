package cmtech.soft.equipment.utils.threadUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;

/**
 * 自定义线程工厂 配合SingleThreadPerTask 根据者任务名获得一个专用线程 用于精细的控制
 */
public class CustomThreadFactory implements ThreadFactory {

    private static ConcurrentHashMap<String, Thread> threadMap = new ConcurrentHashMap<>();// 可以并发的读和写

    public static void addBizThread(String taskName) {
        Thread newThread = new CustomThread();
        newThread.setName(taskName);
        threadMap.put(taskName, newThread);
    }

    public static Thread getThread(Runnable runnable) {
        Thread newThread = new CustomThread(runnable);
        newThread.setDaemon(true);
        return newThread;
    }

    /**
     * 模仿单例写法，根据任务名获得一个专用线程，用于精细的控制。
     *
     * @param taskName
     * @return
     */
    public static Thread getThreadByBizName(String taskName) {
         Thread thread = threadMap.get(taskName);// 允许并发读取，不用加锁。而且读到的是最新的值

        if (thread == null || thread.getState().equals(Thread.State.TERMINATED)) {
            synchronized (CustomThreadFactory.class) {
                if (threadMap.get(taskName) == null || threadMap.get(taskName).getState().equals(Thread.State.TERMINATED)) {
                    addBizThread(taskName);// 线程死亡重新加一个线程
                    thread = threadMap.get(taskName);
                }
            }
        }

        if(thread!=null&&thread.getState().equals(Thread.State.NEW)){// 新的线程就启动
            thread.start();
        }

        return thread;
    }

    public Thread newThread(Runnable r){
        return getThread(r);
    }

}
