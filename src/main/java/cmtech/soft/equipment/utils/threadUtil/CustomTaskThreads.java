package cmtech.soft.equipment.utils.threadUtil;

public class CustomTaskThreads {

    public static Thread MessageArrivedThtead= CustomThreadFactory.getThreadByBizName(PerTaskName.MessageArrived);

    public static Thread SaveCurrentMachineProcessToSqlThread =
             CustomThreadFactory.getThreadByBizName(PerTaskName.SaveCurrentMachineProcessToSqlThread);

    public static Thread SaveMachineProcessChangeToSqlThread =
            CustomThreadFactory.getThreadByBizName(PerTaskName.SaveCurrentMachineProcessToSqlThread);
}
