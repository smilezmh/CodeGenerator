package cmtech.soft.equipment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//注解表示这个是一个配置文件，让spring来加载该类配置
@EnableSwagger2                      //注解表示启用Swagger2
public class SwaggerConfig {
    @Bean                            //注解表示交由bean容器去管理
    public Docket newsApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.enable(true);
        //apiInfo()用来创建该Api的基本信息（这些基本信息会展现在文档页面中）。
        //select()函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现，本例采用指定扫描的包路径来定义，Swagger会扫描该包下所有Controller定义的API，并产生文档内容（除了被@ApiIgnore指定的请求）。
        docket.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cmtech.soft.equipment"))
                .paths(PathSelectors.any()).build();
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("诚模MES API").description("设备项目")
                .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open").contact("test")
                .license("China Red Star Licence Version 1.0").licenseUrl("#").version("1.0").build();
    }

}

