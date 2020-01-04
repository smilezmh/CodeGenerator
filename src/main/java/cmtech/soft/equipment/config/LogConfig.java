package cmtech.soft.equipment.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class LogConfig {
    private static final Logger LOG = LoggerFactory.getLogger(LogConfig.class);

    @Bean
    public void logMethod() {
        LOG.info("==========print log==========");
    }

    public void logMethodWithContent(String content) {
        LOG.info(content);
    }

    public void erroLogMethodWithContent(String content) {
        LOG.error(content);
    }
}
