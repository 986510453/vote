package com.wechar.vote.configuration;

import com.wechar.vote.filter.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

	/**
	 * 将Swagger2 的swagger-ui.html加入资源路径下,否则Swagger2静态页面不能访问。要想使资源能够访问，可以有两种方法
	 * 一：需要配置类继承WebMvcConfigurationSupport 类，实现addResourceHandlers方法。
	 *      但是实现了WebMvcConfigurationSupport以后，Spring Boot的 WebMvc自动配置就会失效，具体表现比如访问不到
	 *      静态资源（js，css等）了，这是因为WebMvc的自动配置都在WebMvcAutoConfiguration类中，但是类中有这个注解
	 *      @ConditionalOnMissingBean({WebMvcConfigurationSupport.class})，意思是一旦在容器中检测到
	 *      WebMvcConfigurationSupport这个类，WebMvcAutoConfiguration类中的配置都不生效。
	 *      所以一旦我们自己写的配置类继承了WebMvcConfigurationSupport，相当于容器中已经有了WebMvcConfigurationSupport，
	 *      所有默认配置都不会生效，都得自己在配置文件中配置。
	 * 二：继承WebMvcConfigurer接口，这里采用此方法 网上有人说使用该方法会导致date编译等问题，可能在配置文件中得到解决，
	 *
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 静态资源映射
		// 如果没有启用 @EnableWebMvc，可以配置在 application.properties
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/static/");

		super.addResourceHandlers(registry);


	}

	/**
	 * 拦截器
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截

		registry.addInterceptor(new Interceptor()).addPathPatterns("/**").excludePathPatterns(
					"/index"
		);
		super.addInterceptors(registry);
	}
}
