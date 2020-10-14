package cmtech.soft.equipment.utils.commonUtil;

import com.diboot.core.util.SqlExecutor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class SqlUtil {
    private static SqlExecutor sqlExecutor = new SqlExecutor();

    public SqlExecutor getSqlExecutor() {
        return sqlExecutor;
    }

    public static boolean executeUpdate(String sql, Object... params) throws Exception {
        return sqlExecutor.executeUpdate(sql, Arrays.asList(params));
    }

    public static List<Map<String, Object>> executeQuery(String sql, Object... params) throws Exception {
        return sqlExecutor.executeQuery(sql, Arrays.asList(params));
    }
}
