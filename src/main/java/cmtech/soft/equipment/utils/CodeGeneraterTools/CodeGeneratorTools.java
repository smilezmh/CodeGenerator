package cmtech.soft.equipment.utils.CodeGeneraterTools;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;


import java.io.File;
import java.util.*;

public class CodeGeneratorTools {

    private static String dataBaseUrl="jdbc:mysql://10.136.11.199:3306/Equipment?serverTimezone=UTC&characterEncoding=utf-8";// 数据库url
    private static String packageNamesPrefix="cmtech.soft.equipment"; // 项目开始引用的package前缀
    private static String packagesPrefix="/src/main/java/cmtech/soft/equipment/";// 生成包结构前缀
    private static String driver="com.mysql.cj.jdbc.Driver";// sql driver
    private static String username="root"; // database username
    private static String pwd="Dp5VqJtAQr"; // database pwd

    public static void Generator(String[] tableName) {
        AutoGenerator mpg = new AutoGenerator();
        String projectPath = System.getProperty("user.dir");

        //============================== 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/src/main/java")// 文件输出路径
                .setActiveRecord(true)// 是否支持 AR
                .setAuthor("smilezmh") //设置作者名字
                .setFileOverride(true) //文件覆盖(全新文件)
                .setIdType(IdType.AUTO)//主键策略
                .setBaseResultMap(true) //SQL 映射文件
                .setBaseColumnList(true)//SQL 片段
                .setOpen(false);
        gc.setSwagger2(true);
        //============================== 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL)
                .setUrl(dataBaseUrl)
                .setDriverName(driver)
                .setUsername(username)
                //.setSchemaName("city")
                .setPassword(pwd);
        //==============================包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageNamesPrefix)//配置父包路径
                .setModuleName("base")//配置业务包路径
                .setMapper("mapper")
                .setEntity("entity")
                .setService("service")
                .setController("controller");
        //.setServiceImpl("service.impl"); 会自动生成 impl，可以不设定
        //============================== 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("prefix", packageNamesPrefix);
                this.setMap(map);
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();

        // 使用自定义文件模版来生成代码
        // 自定义controller的代码模板
        focList.add(new FileOutConfig("/templates/controller.java.ftl") {// controller
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                String expand = projectPath + packagesPrefix+pc.getModuleName()  + "/controller";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getControllerName());
                return entityFile;
            }});

        focList.add(new FileOutConfig("/templates/entity.kt.ftl") {// model类
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                String expand = projectPath + packagesPrefix+pc.getModuleName()  + "/model";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), "QueryModel"+tableInfo.getEntityName());
                return entityFile;
            }});

        focList.add(new FileOutConfig("/templates/view.ftl") {// view视图
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                String expand = projectPath + packagesPrefix+pc.getModuleName()  + "/view";
                String entityFile = String.format((expand + File.separator + "%s" + ".vue"), tableInfo.getEntityName());
                return entityFile;
            }});

        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + packagesPrefix+pc.getModuleName()+"/mapper/"+
                tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);

        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建,这里调用默认的方法
                checkDir(filePath);
                //对于已存在的文件，只需重复生成 entity 和 mapper.xml
                File file = new File(filePath);
                boolean exist = file.exists();
                if(exist){
                    if (filePath.endsWith("Mapper.xml")||FileType.ENTITY==fileType){
                        return true;
                    }else {
                        return false;
                    }
                }
                //不存在的文件都需要创建
                return  true;
            }
        });
        //============================== 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel)//设置命名规则  underline_to_camel 底线变驼峰
                .setColumnNaming(NamingStrategy.underline_to_camel)//设置设置列命名  underline_to_camel 底线变驼峰
                //.setSuperEntityClass("com.maoxs.pojo")//设置继承类
                //.setSuperControllerClass("com.maoxs.controller")//设置继承类
                .setEntityLombokModel(true)//是否加入lombok
                .setInclude(tableName)//设置表名
                //.setSuperEntityColumns("id") //设置超级超级列
                .setControllerMappingHyphenStyle(true)//设置controller映射联字符
                .setRestControllerStyle(true)
                .setTablePrefix(pc.getModuleName() + "_");//表的前缀
        //        //============================== 生成配置

        mpg.setCfg(cfg)
                .setTemplate(new TemplateConfig().setXml(null))
                .setGlobalConfig(gc)
                .setDataSource(dsc)
                .setPackageInfo(pc)
                .setStrategy(strategy)
                // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
                .setTemplateEngine(new FreemarkerTemplateEngine());
                // 使用beetl engine
                //.setTemplateEngine(new BeetlTemplateEngine());
        mpg.execute();
    }

    public static void main(String[] args) {
        while(true){
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入表名：");
            String name = sc.nextLine();
            String[] strings=new String[]{""};
            strings[0]=name;
            Generator(strings);
        }
        // Generator(new String[]{"test"});
    }

}
