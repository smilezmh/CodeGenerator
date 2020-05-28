package cmtech.soft.equipment.utils.Delegate;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 事件处理，ReflectionUtils中invoke也有相关方法,实现方式不一样
 * @author smilezmh
 * @date  2020-05-28
 */
@Data
@Component
public class Event implements IDelegate{

    private Object obj;// 要执行方法的对象
    private String methodHandle;// 要执行方法名
    private Object[] parms;// 要执行方法参数
    private Class<?>[] paramsTypes;// 要执行方法参数类型

    public Event(){};

    public Event(Object obj,String methodHandle,Object... parms){
        this.obj=obj;
        this.methodHandle=methodHandle;
        this.parms=parms;
        generateParamsTypes(this.parms);
    }

    private void generateParamsTypes(Object[] parms){
        this.paramsTypes=new Class<?>[parms.length];
        int i=0;

        for(Object obj:parms){
            this.paramsTypes[i]=obj.getClass();
            i++;
        }
    }

    /**
     * 用有参构造方法实例化调用
     */
    @Override
    public Object invoke(){
        Object result=null;

        try {
            Method method=obj.getClass().getMethod(methodHandle, paramsTypes);
            result=method.invoke(obj, parms);
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }

}
