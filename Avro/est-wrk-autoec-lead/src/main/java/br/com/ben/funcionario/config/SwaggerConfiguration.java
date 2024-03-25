package br.com.mac.funcionario.config;

import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;


import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

  @Value("${info.app.name:Api Documentation}")
  private String apiName;

  @Value("${info.app.description:Api Documentation}")
  private String apiDescription;

  @Value("${info.build.version}")
  private String version;

  @Value("${k8s.pathSwagger}")
  private String pathSwagger;


  @Bean
  public Docket swaggerSpringfoxDocket() {

    return new Docket(SWAGGER_2)
        .apiInfo(apiInfo())
        .genericModelSubstitutes(ResponseEntity.class)
        .forCodeGeneration(true)
        .genericModelSubstitutes(ResponseEntity.class)
        .directModelSubstitute(LocalDate.class, String.class)
        .directModelSubstitute(LocalDateTime.class, String.class)
        .useDefaultResponseMessages(false)
        .select()
        .apis(RequestHandlerSelectors.basePackage("br.com.mac.funcionario.resource"))
        .paths(any())
        .build()
        .pathMapping(pathSwagger);
  }

  public ApiInfo apiInfo() {
    return new ApiInfoBuilder().title(apiName).description(apiDescription).version(version).build();
  }

}

