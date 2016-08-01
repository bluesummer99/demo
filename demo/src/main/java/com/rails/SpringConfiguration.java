package com.rails;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
 * 说明：等同于SpringContext.xml作用
 * 1、使用SpringBootServletInitializer spring boot封装的入口
 * 2、使用WebMvcConfigurerAdapter spring mvc的入口，需要自己去封装
 */
//@EntityScan("com.rails")
//@ComponentScan("com.rails")
//@EnableAutoConfiguration
@Configuration
public class SpringConfiguration extends SpringBootServletInitializer{

	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringConfiguration.class);
    }
    
    @Bean(name = "captchaProducer")
	public Producer captchaProducer() {
		DefaultKaptcha  kaptcha =  new DefaultKaptcha();
		Properties p = new Properties();
		p.put(Constants.KAPTCHA_BORDER, "yes");
		p.put(Constants.KAPTCHA_BORDER_COLOR, "blue");
		p.put(Constants.KAPTCHA_IMAGE_HEIGHT, "50");
		p.put(Constants.KAPTCHA_IMAGE_WIDTH, "148");
		p.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "blue");
		p.put(Constants.KAPTCHA_NOISE_COLOR, "blue");
		p.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "5");
		Config	config = new Config(p);
		kaptcha.setConfig(config);
		return kaptcha;
	}
    
    @Bean
    public HttpMessageConverters customConverters() {
    	StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
    	List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
    	supportedMediaTypes.add(MediaType.valueOf("text/plain;charset=UTF-8"));
    	supportedMediaTypes.add(MediaType.valueOf("text/html;charset=UTF-8"));
    	stringHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
    	FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
    	List<MediaType> supportedMediaTypes2 = new ArrayList<MediaType>();
    	supportedMediaTypes2.add(MediaType.valueOf("text/plain;charset=UTF-8"));
    	supportedMediaTypes2.add(MediaType.valueOf("application/json"));
        return new HttpMessageConverters(stringHttpMessageConverter, fastJsonHttpMessageConverter);
    }
}
