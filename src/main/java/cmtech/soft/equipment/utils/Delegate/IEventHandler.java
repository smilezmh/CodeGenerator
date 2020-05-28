package cmtech.soft.equipment.utils.Delegate;

public interface IEventHandler {
     void addEvent(Object obj,String methodHander,Object... params);
     void invokeAll();
}
