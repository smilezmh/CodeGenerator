package cmtech.soft.equipment.utils.threadUtil;

/**
 * 每个线程一个任务，线程名字，用于精确控制
 */
public interface PerTaskName {
    public static final String MessageArrived="messageArrived";
    public static final String SaveCurrentMachineProcessToSqlThread="saveCurrentMachineProcessToSqlOnRequest";
    public static final String SaveMachineProcessChangeToSqlThread="saveMachineProcessChangeToSqlThread";

}
