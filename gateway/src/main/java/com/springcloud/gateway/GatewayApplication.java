package com.springcloud.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import com.springcloud.gateway.config.UriConfiguration;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableConfigurationProperties(UriConfiguration.class)
public class GatewayApplication {

	@Autowired
	UriConfiguration uriConfiguration;
	
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
	
	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		String httpUri = uriConfiguration.getHttpbin();
	    return builder.routes()
	    		.route(p -> p
	    	            .path("/get")
	    	            .filters(f -> f.addRequestHeader("Hello", "World"))
	    	            .uri(httpUri))
//	    		.route(p -> p
//	    	            .host("*.hystrix.com")
//	    	            .filters(f -> f.hystrix(config -> config.setName("mycmd")))
//	    	            .uri("http://httpbin.org:80"))
	    		.route(p -> p
	    	            .host("*.hystrix.com")
	    	            .filters(f -> f.hystrix(config -> config
	    	                .setName("mycmd")
	    	                .setFallbackUri("forward:/fallback")))
	    	            .uri(httpUri))
	    		.build();
	}
	
//	@Bean
//	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//		return builder.routes()
//			.route("path_route", r -> r.path("/get")
//				.uri("http://httpbin.org"))
//			.route("host_route", r -> r.host("*.myhost.org")
//				.uri("http://httpbin.org"))
//			.route("rewrite_route", r -> r.host("*.rewrite.org")
//				.filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/${segment}"))
//				.uri("http://httpbin.org"))
//			.route("hystrix_route", r -> r.host("*.hystrix.org")
//				.filters(f -> f.hystrix(c -> c.setName("slowcmd")))
//				.uri("http://httpbin.org"))
//			.route("hystrix_fallback_route", r -> r.host("*.hystrixfallback.org")
//				.filters(f -> f.hystrix(c -> c.setName("slowcmd").setFallbackUri("forward:/hystrixfallback")))
//				.uri("http://httpbin.org"))
//			.route("limit_route", r -> r
//				.host("*.limited.org").and().path("/anything/**")
//				.filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
//				.uri("http://httpbin.org"))
//			.build();
//	}
//	
//	@Bean
//	RedisRateLimiter redisRateLimiter() {
//		return new RedisRateLimiter(1, 2);
//	}
}
