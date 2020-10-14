package cmtech.soft.equipment.utils.commonUtil.logUtil;

import org.springframework.stereotype.Component;

@Component("logExtend")
public class LogUtilExtend extends LogUtil implements ILogService {
    public void info(String content, Object... args) {
        executorService.execute(() -> LOG.info(content, args));
    }

    public void error(String content, Object... args) {
        executorService.execute(() -> LOG.error(content, args));
    }

    public void warn(String content, Object... args) {
        executorService.execute(() -> LOG.warn(content, args));
    }

    public void debug(String content, Object... args) {
        executorService.execute(() -> LOG.debug(content, args));
    }
}
