package cmtech.soft.equipment.utils.commonUtil.logUtil;

import cmtech.soft.biz.utils.commonUtil.threadUtil.CustomThread;
import cmtech.soft.biz.utils.commonUtil.threadUtil.CustomThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public abstract class LogUtil implements ILogService {
    protected static CustomThread executorService = (CustomThread) CustomThreadFactory.getThreadByBizName("LogThread");

    protected static final Logger LOG = LoggerFactory.getLogger(LogUtil.class);

    public void info(String content) {
        executorService.execute(() -> LOG.info(content));
    }

    public void error(String content) {
        executorService.execute(() -> LOG.error(content));
    }

    public void warn(String content) {
        executorService.execute(() -> LOG.warn(content));
    }

    public void debug(String content) {
        executorService.execute(() -> LOG.debug(content));
    }
}
