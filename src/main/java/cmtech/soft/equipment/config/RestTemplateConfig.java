package cmtech.soft.equipment.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    /**
     * 第三方的api
     * @param factory httpclientfactory
     * @return RestTemplate
     */
    @Bean
    @Qualifier("apiTemplete")
    public RestTemplate restTemplate(@Qualifier("httpFactory") ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    /**
     * 默认使用 ribbon 来进行负载均衡
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced
    @Qualifier("ribbonTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Qualifier("httpFactory")
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory=new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(5000);
        return factory;
    }
}
