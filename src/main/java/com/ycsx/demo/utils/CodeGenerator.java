package com.ycsx.demo.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * 代码生成器
 *
 * @since v2.6
 */
public class CodeGenerator {
    public static void main(String[] args) {
        generate();
    }

    private static void generate() {
        String url = "jdbc:mysql://localhost:3306/graduation?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
        String username = "root";
        String password = "1234";
        // 在这里输入生成的表名：
        String mysqlPassName = "pool_detail";

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("ycsxdyf")   // 设置作者
                            .fileOverride()     // 覆盖已生成文件
                            .outputDir("D:\\idea-2019.3.3\\Projects\\graduation2.0\\springboot\\src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.ycsx.demo") // 设置父包名
                            .moduleName(null) // 设置父包模块名，这里没有填‘空’
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\idea-2019.3.3\\Projects\\graduation2.0\\springboot\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径，记得最后加上\\
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok(); //Lombok
                    builder.controllerBuilder().enableHyphenStyle() //驼峰转连字符
                            .enableRestStyle(); //开启RestController
                    builder.addInclude(mysqlPassName) // 设置需要生成的表名，比如数据库中t_user，这里就照填
                            .addTablePrefix(""); // 设置过滤表前缀，t_user的t_就会被忽略，生成User类
                })
                .execute();

    }
}
