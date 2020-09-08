package cmtech.soft.equipment.utils.threadUtil;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.LockSupport;

/**
 * 仿照线程池的形式封装一个任务队列存放没有完成的老任务
 */
public class CustomThread extends Thread {
    private Queue<Runnable> unfinishedWorks = new LinkedBlockingDeque<>();// 未完成的任务
    private Runnable onceWork;// 一次性任务
    private Runnable oldOnceWork; // 老任务
    private Runnable cycleWork; // 循环任务，有循环任务最好不设置onceWork，因为cycleWork，会一直执行。
    private Runnable exeptionWork; // 异常任务
    private Runnable finallyWork;// 最终任务

    // 中断线程
    private volatile boolean interruptFlag = false;

    public void interrupt() {
        LockSupport.unpark(this);// 有可能线程在park，要unpark
        interruptFlag = true;
        super.interrupt();
    }

    public CustomThread() {
        LockSupport.unpark(this);
    }

    public CustomThread(Runnable onceWork, Runnable cycleWork) {
        LockSupport.unpark(this);
        this.onceWork = onceWork;
        unfinishedWorks.add(onceWork);
        this.cycleWork = cycleWork;
    }

    public CustomThread(Runnable r) {
        super(r);
    }

    public void setOnceWork(Runnable onceWork) {
        LockSupport.unpark(this);
        this.onceWork = onceWork;
        unfinishedWorks.add(onceWork);
    }

    public void setCycleWork(Runnable cycleWork) {
        LockSupport.unpark(this);
        this.cycleWork = cycleWork;
    }

    public void setExeptionWork(Runnable exeptionWork) {
        this.exeptionWork = exeptionWork;
    }

    public void setFinallyWork(Runnable finallyWork) {
        this.finallyWork = finallyWork;
    }

    private void setRunnableNull() {
        unfinishedWorks = null;
        onceWork = null;
        oldOnceWork = null;
        cycleWork = null;
    }

    public void run() {
        try {
            // 保证线程没有终止信号不终止
            while (!interruptFlag) {
                // 一次性的任务，只有新任务来的时候才执行
                if (onceWork != null && oldOnceWork != onceWork) {

                    onceWork.run();

                    if (unfinishedWorks != null && unfinishedWorks.contains(onceWork))
                        unfinishedWorks.remove(onceWork);
                    oldOnceWork = onceWork;
                } else if (unfinishedWorks != null && unfinishedWorks.size() > 0) {// 没有新任务，有未完成任务
                    for (Runnable unfinishedWork : unfinishedWorks) {
                        unfinishedWork.run();

                        if (unfinishedWorks != null && unfinishedWorks.contains(unfinishedWork))
                            unfinishedWorks.remove(unfinishedWork);
                    }
                } else if (cycleWork == null) {
                    LockSupport.park();// 减少不必要cpu消耗
                }

                if (cycleWork != null)
                    cycleWork.run();
            }
        } catch (Exception e) {
            if (exeptionWork != null)
                exeptionWork.run();

        } finally {
            if (finallyWork != null)
                finallyWork.run();

            setRunnableNull();
        }
    }
}
