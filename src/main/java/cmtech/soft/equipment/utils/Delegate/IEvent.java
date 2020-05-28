package cmtech.soft.equipment.utils.Delegate;

public interface IEvent {
    Object invoke(Object obj,String methodHandle,Object... parms);
    Object invoke();
}
