package cmtech.soft.equipment.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

//@EnableTransactionManagemen
@Configuration
@MapperScan("cmtech.soft.equipment.base.mapper")
public class MybatisPlusConfig {

//    @Bean
//    @Profile({"dev","test"})// 设置 dev test 环境开启
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        /*<!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->*/
//        //performanceInterceptor.setMaxTime(1000);
//        /*<!--SQL是否格式化 默认false-->*/
//        //performanceInterceptor.setFormat(true);
//        return performanceInterceptor;
//    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
    @Bean
    @Scope
    public QueryWrapper  gQueryWrapper(){return new QueryWrapper();} ;
    @Bean
    @Scope
    public IPage gIpage(){return new Page();}

}
