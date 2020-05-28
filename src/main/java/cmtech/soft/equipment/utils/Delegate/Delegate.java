package cmtech.soft.equipment.utils.Delegate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Delegate implements IDelegate {

    private Object obj;// 要执行方法的对象
    private String methodHandle;// 要执行方法名
    private Object[] parms;// 要执行方法参数

    public Delegate(Object object, String method, Object... args){
        this.obj=object;
        this.methodHandle=method;
        this.parms=args;
    }

    public Object invoke(Object object, String method, Object... args) {
        Object result = null;
        Class<? extends Object> clazz = object.getClass();
        Method queryMethod = getMethod(clazz, method, args);
        if(queryMethod != null) {
            try {
                result = queryMethod.invoke(object, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            try {
                throw new NoSuchMethodException(clazz.getName() + " 类中没有找到 " + method + " 方法。");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Object invoke() {
        return this.invoke(obj, methodHandle,parms);
    }

    private Method getMethod(Class<? extends Object> clazz, String name, Object[] args) {
        Method queryMethod = null;
        Method[] methods = clazz.getMethods();
        for(Method method:methods) {
            if(method.getName().equals(name)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if(parameterTypes.length == args.length) {
                    boolean isSameMethod = true;
                    for(int i=0; i<parameterTypes.length; i++) {
                        Object arg = args[i];
                        if(arg == null) {
                            arg = "";
                        }
                        if(!parameterTypes[i].equals(args[i].getClass())) {
                            isSameMethod = false;
                        }
                    }
                    if(isSameMethod) {
                        queryMethod = method;
                        break ;
                    }
                }
            }
        }
        return queryMethod;
    }
}
