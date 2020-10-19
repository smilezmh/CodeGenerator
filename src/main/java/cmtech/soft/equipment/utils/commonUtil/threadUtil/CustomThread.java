package cmtech.soft.equipment.utils.commonUtil.threadUtil;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义线程，自定义实现的单线程的线程池。使用说明：
 * 1.符合业务场景，每次来就执行单个任务。并可以用来控制单个线程。
 * 2.和线程池类似，做了任务缓存：存放务没有及时完成的老任务。更符合实际使用场景。当然也可以不使用缓存execute(onceTask, false)。
 * 3.缓存使用栈来实现，后来的任务优先处理。 同步方法，synchronized void execute(onceTask）保证线程并发时同一个任务不会重复加入缓存中,这点和线程池不一样
 * 4.wonderfulInterrupt()方法可以终止线程，不是立刻终止。可以终止那些处于wait或者block状态的线程。
 * 5.使用线程池可以有类似的效果，但是当多个业务使用同一个线程池不能控制单个线程来控制某个业务。
 * 6.线程run方法有while循环，为了减少不必要cpu消耗，防止线程一直在空载运行，使用LockSupport.park()。使线程变成wait状态，进入等待队列。
 * 7.每次获得一个新任务时，调用LockSupport.unpark(this)；会使处于因为LockSupport.park()或其他原因而进入等待或阻塞状态的线程
 * （等待状态会重新获得锁运行，从阻塞状态变成运行后还要判断锁所以没问题），重新获得许可运行。运行后执行run方法，先执行最新的任务，和未完成的任务。
 * 8.如果想跟线程池一样，按顺序执行就用setInOrder方法
 * <p>
 * 注意：
 * 1.这里的线程需要手动停止调用wonderfulInterrupt，否则会park，在while循环中，不会自动结束。
 * <p>
 * 2.防止线程wait或者长时间等待，或者运行需要大量时间完成的任务（放到线程池处理重的任务）、尽量使线程少阻塞，
 * 使缓存任务数大量增加，影响后续任务的处理。
 *
 * @author 赵梦辉
 * @since 2020-09-14
 */
public class CustomThread extends Thread {
    private Deque<Runnable> cacheTasks = new LinkedList<>();// 缓存的任务,栈
    private Queue<Runnable> cacheTasksQueue = new LinkedList<>();// 缓存的任务，队列
    private volatile Boolean inOrder = false;
    private volatile Runnable onceTask;// 一次性任务
    private Lock lock = new ReentrantLock();

    // 中断线程
    private volatile boolean interruptFlag = false;

    /**
     * 按顺序执行，以前的缓存还是按倒序执行
     */
    public void setInOrder() {
        if (!inOrder)
            try {
                lock.lock();
                inOrder = true;

                if (cacheTasks != null && !cacheTasks.isEmpty()) {
                    for (Runnable runnable : cacheTasks) {
                        cacheTasksQueue.offer(runnable);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
    }

    /**
     * 按倒序执行，以前的缓存还是按顺序执行
     */
    public void setReverseOrder() {
        if (inOrder)
            try {
                lock.lock();
                inOrder = false;

                if (cacheTasksQueue != null && !cacheTasksQueue.isEmpty()) {
                    for (Runnable runnable : cacheTasksQueue) {
                        cacheTasks.push(runnable);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
    }

    public void wonderfulInterrupt() {
        interrupt();// 如果正在等待。会让线程重新获得锁并继续运行。
        interruptFlag = true;
    }

    public CustomThread() {
    }

    public CustomThread(Runnable onceTask) {
        execute(onceTask, true);
    }

    public void execute(Runnable onceTask) {// 同步方法，保证同一个任务不会重复加入缓存中
        // LockSupport.unpark又会使处于wait的线程从等待队列中出去
        try {
            LockSupport.unpark(this);
            lock.lock();
            this.onceTask = onceTask;

            if (!inOrder)
                cacheTasks.push(onceTask);
            else
                cacheTasksQueue.offer(onceTask);
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }

    public void execute(Runnable onceTask, Boolean isUseTaskCache) {
        // LockSupport.unpark又会使处于wait的线程从等待队列中出去
        LockSupport.unpark(this);

        if (isUseTaskCache) {
            execute(onceTask);
        } else {
            try {
                lock.lock();
                this.onceTask = onceTask;
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
        }
    }

    public void run() {
        try {
            // 保证线程没有终止信号不终止
            while (!interruptFlag) {
                Runnable firstTask = onceTask;

                if (!inOrder)
                    try {
                        lock.lock();
                        onceTask = null;
                    } catch (Exception e) {
                    } finally {
                        lock.unlock();
                    }
                else {
                    firstTask = null;
                }

                if ((firstTask != null || (firstTask = getTask()) != null)) {
                    firstTask.run();

                    if (!inOrder)
                        removeFinishTaskFromCache(firstTask);
                } else {
                    LockSupport.park();// 减少不必要cpu消耗，防止线程一直在空载运行。使线程变成wait状态，进入等待队列
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            onceTask = null;
            cacheTasks = null;
            cacheTasksQueue = null;
        }
    }

    /**
     * 从任务缓存队列中获取任务，单线程调用不用加锁
     *
     * @return 获取任务
     */
    private Runnable getTask() {
        Runnable runnable = null;

        try {
            lock.lock();
            if (!inOrder && cacheTasks != null && !cacheTasks.isEmpty()) {
                runnable = cacheTasks.pop();
            }

            if (inOrder && cacheTasksQueue != null && !cacheTasksQueue.isEmpty()) {
                runnable = cacheTasksQueue.poll();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            return runnable;
        }
    }

    /**
     * 删除完成的任务
     * <p>
     * 说明：加锁是为了在防止完成的任务 onceTask（firstTask.run()执行后）在将完成的任务放入缓存队列前即
     * cacheTasks.push(onceTask)之前，执行此从缓存队列删除完成任务方法，那么因为onceTask还不在缓存队列中，所以并
     * 没有从缓存任务队列中删除掉完成的任务onceTask，然后再执行cacheTasks.push(onceTask)
     * 又被加入到缓存任务队列中（cacheTasks.push(onceTask)）
     * 那么完成的任务还会执行一遍。
     *
     * @param finishTask 完成的任务
     */
    private void removeFinishTaskFromCache(Runnable finishTask) {
        try {
            lock.lock();

            if (!inOrder && cacheTasks != null && !cacheTasks.isEmpty() && cacheTasks.contains(finishTask)) {
                cacheTasks.remove(finishTask);
            }

            if (inOrder && cacheTasksQueue != null && !cacheTasksQueue.isEmpty() && cacheTasksQueue.contains(finishTask)) {
                cacheTasksQueue.remove(finishTask);
            }
        } catch (Exception e) {
            wonderfulInterrupt();
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
