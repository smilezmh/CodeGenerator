package cmtech.soft.equipment.utils.ExeptionTools;

import cmtech.soft.equipment.config.LogConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理类
 */
@ControllerAdvice(value = "cmtech.soft.equipment")
public class GlobalExeptionCatch {
    @Autowired
    private LogConfig log;
    String indexStr = "cmtech.soft.equipment.base";

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> exceptionHandler(Exception ex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("className", ex.getStackTrace()[0].getClassName());
        map.put("methodName", ex.getStackTrace()[0].getMethodName());
        map.put("lineNum", ex.getStackTrace()[0].getLineNumber());
        map.put("message", ex.getMessage());

        log.erroLogMethodWithContent("--------异常信息开始--------");
        log.erroLogMethodWithContent("异常类：" + ex.getStackTrace()[0].getClassName());
        log.erroLogMethodWithContent("异常方法：" + ex.getStackTrace()[0].getMethodName());
        log.erroLogMethodWithContent("异常具体信息：" + ex.getMessage());
        log.erroLogMethodWithContent("异常位置：" + ex.getStackTrace()[0].getLineNumber());

        for (int i = 0; i < ex.getStackTrace().length; i++) {
            if (ex.getStackTrace()[i].getClassName().contains(indexStr) && i != 0 && !ex.getStackTrace()[i].getClassName().contains("$")) {
                log.erroLogMethodWithContent("异常类：" + ex.getStackTrace()[i].getClassName());
                log.erroLogMethodWithContent("异常方法：" + ex.getStackTrace()[i].getMethodName());
                log.erroLogMethodWithContent("异常位置：" + ex.getStackTrace()[i].getLineNumber());
            }
        }

        log.erroLogMethodWithContent("--------异常信息结束--------");

        return map;
    }

}
