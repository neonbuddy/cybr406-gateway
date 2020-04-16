package com.cybr406.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	@Value("${account.url}")
	String accountUrl;

	@Value("${post.url}")
	String postUrl;

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	//RoutLocatorBuilder is a builder Style API, define routes, send traffic based on conditions, you can alter requests

	//This bean holds all of the routes that we want to serve; application will use these to manage traffic
	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("example", r -> r
						.path("/")			// Predicate that you want to match
						.uri("http://example.com"))		// Destination you want request to route to if predicate matches
				.route("account", r -> r
						.path("/headers", "/signup", "/check-user", "/profiles/**")	// Send these urls to account
						.uri(accountUrl))	// These urls will change based on enviornment
				.route("post", r -> r
						.path("/posts/**", "/comments/**")					//Send these urls to post
						.uri(postUrl))
				.build();
	}

}
