package cmtech.soft.equipment.utils.commonUtil;


import cmtech.soft.equipment.utils.commonUtil.logUtil.ILogService;
import cmtech.soft.equipment.utils.commonUtil.threadUtil.CustomThread;
import cmtech.soft.equipment.utils.commonUtil.threadUtil.CustomThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;

/**
 * 全局异常处理类
 */

@Component
@ControllerAdvice(value = "cmtech.soft.equipment")
public class GlobalExceptionCatch {
    @Autowired
    @Qualifier("logExtend")
    private ILogService log;
    String indexStr = "cmtech.soft.equipment";
    private static final CustomThread exceptionThread = (CustomThread) CustomThreadFactory.getThreadByBizName("ExceptionThread");
    private static String logMessage = "";// 初始化后只会在单线程中改变，所以是线程安全的

    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public void exceptionHandler(Exception ex) {
        exceptionThread.execute(() -> {
            logMessage = MessageFormat.format("\n----------异常信息开始----------\n异常原因: {0}\n", ex.getMessage());
            for (int i = 0; i < ex.getStackTrace().length; i++) {
                if (ex.getStackTrace()[i].getClassName().contains(indexStr) && !ex.getStackTrace()[i].getClassName().contains("$")) {
                    logMessage += MessageFormat.format("异常类名: {0}\n异常方法: {1}\n异常位置: {2}\n",
                            ex.getStackTrace()[i].getClassName(), ex.getStackTrace()[i].getMethodName()
                            , ex.getStackTrace()[i].getLineNumber());
                }
            }

            logMessage += MessageFormat.format("**********异常信息结束**********", null);
            log.error(logMessage);
            logMessage = "";
        });

    }

}
