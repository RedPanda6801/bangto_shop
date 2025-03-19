package com.example.banto.Configs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    
	    configuration.setAllowedOrigins(List.of("http://localhost:3000")); // 특정 Origin 허용
	    configuration.setAllowedMethods(List.of("GET", "POST")); // 허용할 HTTP 메서드
	    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // 허용할 헤더
	    configuration.setAllowCredentials(true); // 쿠키 및 인증 정보 포함 허용

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration); // 특정 경로에만 적용

	    return source;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.httpBasic(AbstractHttpConfigurer::disable)
				.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeHttpRequests(
				request -> request
				// 관리자만 허용된 URL
				.requestMatchers(
						new AntPathRequestMatcher("/apply/modify"),
						new AntPathRequestMatcher("/apply/get-list/**"),
						new AntPathRequestMatcher("/apply/get-info/**"),
						new AntPathRequestMatcher("/manager/**"),
						new AntPathRequestMatcher("/pay/get-user-info/**"),
						new AntPathRequestMatcher("/pay/get-store-info/**"),
						new AntPathRequestMatcher("/seller/delete/**"),
						new AntPathRequestMatcher("/wallet/manager")
						).hasAuthority("ADMIN")
				// 판매자만 허용된 URL
				.requestMatchers(
						new AntPathRequestMatcher("/item/add-item"),
						new AntPathRequestMatcher("/item/modify"),
						new AntPathRequestMatcher("/item/option/modify"),
						new AntPathRequestMatcher("/pay/modify"),
						new AntPathRequestMatcher("/pay/get-my-store-info/**"),
						new AntPathRequestMatcher("/qna/store/get-list/**"),
						new AntPathRequestMatcher("/qna/add-answer"),
						new AntPathRequestMatcher("/store/**"),
						new AntPathRequestMatcher("/seller/get-info"),
						new AntPathRequestMatcher("/seller/delete-me"),
						new AntPathRequestMatcher("/group-item/delete"),
						new AntPathRequestMatcher("/group-item/modify"),
						new AntPathRequestMatcher("/group-item/add"),
						new AntPathRequestMatcher("/group-pay/store/get-list")
						).hasAuthority("SELLER")
				// 구매자만 허용된 URL
				.requestMatchers(
						new AntPathRequestMatcher("/apply")
						).hasAuthority("BUYER")
				// 관리자, 판매자 둘 다에게 허용된 URL
				.requestMatchers(
						new AntPathRequestMatcher("/group-buy/get-list")
						).hasAnyAuthority("ADMIN", "SELLER")
				// 관리자, 구매자 둘 다에게 허용된 URL
				.requestMatchers(
						new AntPathRequestMatcher("/qna/delete"),
						new AntPathRequestMatcher("/comment/delete")
						).hasAnyAuthority("ADMIN", "BUYER")
				// 판매자, 구매자 둘 다에게 허용된 URL
				.requestMatchers(
						new AntPathRequestMatcher("/apply/my-info"),
						new AntPathRequestMatcher("/cart/**"),
						new AntPathRequestMatcher("/favorite/**"),
						new AntPathRequestMatcher("/qna/my-list/**"),
						new AntPathRequestMatcher("/qna/add"),
						new AntPathRequestMatcher("/pay"),
						new AntPathRequestMatcher("/pay/get-info/**"),
						new AntPathRequestMatcher("/qna/get-detail"),
						new AntPathRequestMatcher("/user/modify"),
						new AntPathRequestMatcher("/user/delete-me"),
						new AntPathRequestMatcher("/wallet/my"),
						new AntPathRequestMatcher("/comment/write"),
						new AntPathRequestMatcher("/comment/get-my/**")
						).hasAnyAuthority("SELLER", "BUYER")
				// 관리자, 판매자, 구매자 셋 다에게 허용된 URL
				.requestMatchers(
						new AntPathRequestMatcher("/user/get-info")
						).hasAnyAuthority("ADMIN", "SELLER", "BUYER")
				// 모두에게 허용된 URL
				.requestMatchers(
						new AntPathRequestMatcher("/group-buy/current-event"),
						new AntPathRequestMatcher("/group-buy/item/current-list"),
						new AntPathRequestMatcher("/group-item/event/get-list"),
						new AntPathRequestMatcher("/item/get-all-list/**"),
						new AntPathRequestMatcher("/item/get-itemlist/**"),
						new AntPathRequestMatcher("/item/get-detail/**"),
						new AntPathRequestMatcher("/sign"),
						new AntPathRequestMatcher("/login"),
						new AntPathRequestMatcher("/user/get-sns-signed/**"),
						//new AntPathRequestMatcher("/user/get-info"),
						new AntPathRequestMatcher("/comment/item/**"),
						new AntPathRequestMatcher("/comment/get/**"),
						new AntPathRequestMatcher("/item/get-by-title/**"),
						new AntPathRequestMatcher("/item/get-by-store-name/**"),
						new AntPathRequestMatcher("/item/get-by-category/**"),
						new AntPathRequestMatcher("/item/get-filtered-list/**"),
						new AntPathRequestMatcher("/item/get-recommend-list"),
						new AntPathRequestMatcher("/qna/item/get-list/**")
						).permitAll()
				// 그 외 모든 요청 허용
				.anyRequest().permitAll()
				)
				.csrf(csrf -> csrf.disable())
				.build();
	}
}
