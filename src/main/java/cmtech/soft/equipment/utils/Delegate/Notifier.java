package cmtech.soft.equipment.utils.Delegate;

import lombok.Data;

/**
 * 通知者，不知道通知的对象object是谁
 */
@Data
public class Notifier implements INotifier {
    private EventHanderler eventHanderler=new EventHanderler();

    /**
     *
     * @param object 通知的对象listener
     * @param methodName 通知的对象listener的方法
     * @param args 参数
     */
    public void addListener(Object object, String methodName, Object... args) {
        eventHanderler.addEvent(object, methodName, args);
    }

    @Override
    public void nofify() {
        eventHanderler.invokeAll();
    }
}
