package com.example.banto.Configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
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
						new AntPathRequestMatcher("/seller/delete-me")
						).hasAuthority("SELLER")
				// 구매자만 허용된 URL
				.requestMatchers(
						new AntPathRequestMatcher("/apply"),
						new AntPathRequestMatcher("/qna/get-list/**"),
						new AntPathRequestMatcher("/qna/add")
						).hasAuthority("BUYER")
				// 관리자, 판매자 둘 다에게 허용된 URL
				.requestMatchers(
						new AntPathRequestMatcher("/group-buy/get-list")
						).hasAnyAuthority("ADMIN", "SELLER")
				// 판매자, 구매자 둘 다에게 허용된 URL
				.requestMatchers(
						new AntPathRequestMatcher("/apply/my-info"),
						new AntPathRequestMatcher("/cart/**"),
						new AntPathRequestMatcher("/favorite/**"),
						new AntPathRequestMatcher("/pay"),
						new AntPathRequestMatcher("/pay/get-info/**"),
						new AntPathRequestMatcher("/qna/get-detail"),
						new AntPathRequestMatcher("/user/modify"),
						new AntPathRequestMatcher("/user/delete-me"),
						new AntPathRequestMatcher("/wallet/my")
						).hasAnyAuthority("SELLER", "BUYER")
				// 관리자, 판매자, 구매자 셋 다에게 허용된 URL
				.requestMatchers(
						new AntPathRequestMatcher("/user/get-info")
						).hasAnyAuthority("ADMIN", "SELLER", "BUYER")
				// 모두에게 허용된 URL
				.requestMatchers(
						new AntPathRequestMatcher("/group-buy/current-event"),
						new AntPathRequestMatcher("/group-buy/item/current-list"),
						new AntPathRequestMatcher("/item/get-all-list/**"),
						new AntPathRequestMatcher("/item/get-itemlist/**"),
						new AntPathRequestMatcher("/item/get-detail/**"),
						new AntPathRequestMatcher("/sign"),
						new AntPathRequestMatcher("/login")
						).permitAll()
				// 그 외 모든 요청 허용
				.anyRequest().permitAll()
				)
				.csrf(csrf -> csrf.disable())
				.build();
	}
}
