package com.Rail.TrainInquiry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@PropertySource("ExceptionMessages.properties")
public class TrainInquiryApplication implements WebMvcConfigurer
{
	
	
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) 
	{
		UrlPathHelper urlHelper=new UrlPathHelper();
		urlHelper.setRemoveSemicolonContent(false);
		configurer.setUrlPathHelper(urlHelper);
		
	}

	public static void main(String[] args) {
		SpringApplication.run(TrainInquiryApplication.class, args);
	}

}
