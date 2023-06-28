/*
package com.njxnet.service.tmsp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

*/
/**
 * @Description war包启动
 * @ClassName RunApp
 * @Author zzd
 * @Date 2020/3/3 18:06
 * @Version 1.0
 **//*


@SpringBootApplication
@ComponentScan(basePackages = { "com.njxnet"})
public class RunApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RunApp.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(RunApp.class, args);
    }
}
*/
