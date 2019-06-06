import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "swagger")
public class Swagger2 {
  private static final String BASE_PACKAGE = "com.ralph";
  @Value("${swagger.enable}")
  private boolean enableSwagger;

  @Bean
  public Docket helloDocket() {
    return new Docket(DocumentationType.SWAGGER_2)
        // 用于分组功能
        .groupName("hello")
        // 注册整体api信息
        .apiInfo(apiInfo())
        // swagger功能是否启用
        .enable(enableSwagger).select()
        // 指定扫描的包
        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
        // 设置此组只匹配hello/**的请求
        .paths(PathSelectors.ant("/hello/**")).build();
  }

  @Bean
  public Docket testDocket() {
    return new Docket(DocumentationType.SWAGGER_2).groupName("test").apiInfo(apiInfo()).enable(enableSwagger).select()
        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE)).paths(PathSelectors.ant("/test/**"))
        // .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Spring Boot中使用Swagger2构建RESTful APIs").description("test")
        .contact(new Contact("yunruo", "", "")).version("1.0.0").build();
  }
}
