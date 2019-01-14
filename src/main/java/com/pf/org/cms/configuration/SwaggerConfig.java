package com.pf.org.cms.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.Contact;


@EnableSwagger2
@Configuration
public class SwaggerConfig {


    @Bean("用户模块")
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户模块")
                .select()
                //下面的paths设置该模块拦截的路由
                .paths(PathSelectors.regex("/user.*"))
                .build()
                .apiInfo(userApiInfo());
    }


    @Bean("系统管理")
    public Docket xitongApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统管理")
                .select()
                //下面的paths设置该模块拦截的路由
                .paths(PathSelectors.regex("/system.*"))
                .build()
                .apiInfo(xitongApiInfo());
    }

    @Bean("全局")
    //当然你要是不想一个一个的设置最简单的是设置一个全局模块，拦截所有的路由
    public Docket quanJuApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("全局")
                //下面的paths设置该模块拦截的路由
                .pathMapping("/")
                .select()
                .build()
                .apiInfo(quanInfo());
    }

    private ApiInfo userApiInfo() {
//        apiInfo是接口文档的基本说明信息，包括 标题 、描述、服务网址、联系人、版本等信息；
//        主要用户项目说明
//        这个是用户管理模块的说明
        ApiInfo apiInfo = new ApiInfo(
                "本项目基于Swagger开发的API文档，用户管理",//大标题
                "测试REST API，所有应用程序都可以通过JSON访问Object模型数据",//小标题
                "1.0",//版本
                "NO terms of service",//服务条款
                new Contact("xiyou", "hiiumaa.club:8088", "neuq_hcg@163.com"),//作者
                "项目github地址",//链接显示文字
                "https://github.com/HouChenggong/springboot_shiroMD5"//网站链接
        );
        return apiInfo;
    }


    private ApiInfo xitongApiInfo() {
//        apiInfo是接口文档的基本说明信息，包括 标题 、描述、服务网址、联系人、版本等信息；
//        主要用户项目说明
//        系统管理模块的说明
        ApiInfo apiInfo = new ApiInfo(
                "本项目基于Swagger开发的API文档,系统管理",//大标题
                "测试REST API，所有应用程序都可以通过JSON访问Object模型数据",//小标题
                "1.0",//版本
                "NO terms of service",//服务条款
                new Contact("xiyou", "hiiumaa.club:8088", "neuq_hcg@163.com"),//作者
                "项目github地址",//链接显示文字
                "https://github.com/HouChenggong/springboot_shiroMD5"//网站链接
        );
        return apiInfo;
    }

    private ApiInfo quanInfo() {
//        apiInfo是接口文档的基本说明信息，包括 标题 、描述、服务网址、联系人、版本等信息；
//        主要用户项目说明
//        系统管理模块的说明
        ApiInfo apiInfo = new ApiInfo(
                "本项目基于Swagger开发的API文档,全部项目",//大标题
                "测试REST API，所有应用程序都可以通过JSON访问Object模型数据",//小标题
                "1.0",//版本
                "NO terms of service",//服务条款
                new Contact("xiyou", "hiiumaa.club:8088", "neuq_hcg@163.com"),//作者
                "项目github地址",//链接显示文字
                "https://github.com/HouChenggong/springboot_shiroMD5"//网站链接
        );
        return apiInfo;
    }
}
