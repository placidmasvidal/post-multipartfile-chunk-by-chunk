package com.backapifilter.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("back-api-filter").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.backapifilter.v1.controller")).build();
	}

	private ApiInfo apiInfo() {

		return new ApiInfoBuilder().title("back-api-filter")
				.description("Testing send file by partitioning").version("1.0").build();
	}
}
