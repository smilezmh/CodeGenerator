package cmtech.soft.equipment.utils.commonUtil.logUtil;

public interface ILogService {
    void info(String content);

    void info(String content, Object... args);

    void error(String content);

    void error(String content, Object... args);

    void warn(String content);

    void warn(String content, Object... args);

    void debug(String content);

    void debug(String content, Object... args);
}
