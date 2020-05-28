package cmtech.soft.equipment.utils.Delegate;


import cmtech.soft.equipment.utils.Delegate.Event;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventHanderler implements IEventHandler{
    public List<Event> events;

    public EventHanderler(){
        events=new ArrayList<Event>();
    }

    @Override
    public void addEvent(Object obj,String methodHander,Object... params){
        events.add(new Event(obj,methodHander,params));
    }

    @Override
    public void invokeAll(){
        for(Event event:events){
            event.invoke();
        }
    }
}
