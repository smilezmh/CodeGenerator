package cmtech.soft.equipment.utils.Delegate;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventHanderler implements IEventHandler {
    public List<IDelegate> delegates;

    public EventHanderler() {
        delegates = new ArrayList<IDelegate>();
    }

    public void addEvent(Object obj, String methodHander, Object... params) {
        IDelegate idelegate = new Event(obj, methodHander, params);
        delegates.add(idelegate);
    }

    public void addDelegate(Object obj, String methodHander, Object... params) {
        IDelegate idelegate=new Delegate(obj,methodHander,params);
        delegates.add(idelegate);
    }

    @Override
    public void invokeAll() {
        for (IDelegate iDelegate : delegates) {
            iDelegate.invoke();
        }
    }

    @Override
    public Object[] invokeAlls() {
        Object[] objects = new Object[delegates.size()];
        int i = 0;

        for (IDelegate iDelegate : delegates) {
            objects[i] = iDelegate.invoke();
            i++;
        }

        return objects;
    }
}
