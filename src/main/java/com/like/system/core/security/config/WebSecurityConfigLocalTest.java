package com.like.system.core.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
//@EnableWebSecurity
@Profile("localtest")
public class WebSecurityConfigLocalTest<S extends Session> {

	@Autowired
	private FindByIndexNameSessionRepository<S> sessionRepository;
	
	@Bean
	public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
			       .userDetailsService(userDetailService)
			       .passwordEncoder(bCryptPasswordEncoder)
			       .and()
			       .build();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()							
		.cors().configurationSource(corsConfigurationSource()).and()
		.headers().frameOptions().disable().and()	// h2-console 테스트를 위한 설정
		//.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
		.sessionManagement((s) -> s.maximumSessions(1).sessionRegistry(sessionRegistry()))/*.sessionCreationPolicy(SessionCreationPolicy.NEVER).and()*/
	    .authorizeHttpRequests()
		//.authorizeRequests()			
		.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
			.requestMatchers("/api/system/user/login").permitAll()			// 로그인 api
			.requestMatchers("/h2-console/**").permitAll()
			.requestMatchers("/oauth/user").permitAll()								
			.requestMatchers("/oauth2/authorization/**").permitAll()				
			.requestMatchers("/ex").permitAll()
			
			//.antMatchers("/common/menuhierarchy/**").permitAll()
			//.antMatchers("/grw/**").permitAll()//hasRole("USER")							
			.anyRequest().authenticated().and()		// 인증된 요청만 허용
			//.anyRequest().permitAll().and()				// 모든 요청 허용(테스트용도)
		// 모든 연결을 HTTPS로 강제 전환
		//.requiresChannel().anyRequest().requiresSecure().and()			
		.logout()
			.logoutUrl("/common/user/logout")
			//.logoutSuccessHandler(this::logoutSuccessHandler)
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.permitAll();		
		//http.portMapper().http(8080).mapsTo(8443);
	//http.addFilterBefore(myAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	
		return http.build();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
       CorsConfiguration configuration = new CorsConfiguration();       

       configuration.addAllowedOrigin("http://localhost:4200");
       //configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));       
              
       configuration.addAllowedMethod("*");
       //configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));       
                    
       // Request Header에 Http default 이외에 정해진 것만 허용한다.
       /*
       configuration.setAllowedHeaders(Arrays.asList("Origin", "Accept", "X-Requested-With", "Content-Type", 
    		   										 "remember-me", "x-auth-token", "Authorization", "x-xsrf-token", "XSRF-TOKEN","X-Access-Token", 
    		   										 "Access-Control-Allow-Origin","Access-Control-Request-Method","Access-Control-Request-Headers"));
       */
       configuration.setAllowedHeaders(Arrays.asList("*"));
                           
       // browser에서 Access-Control-Allow-Credentials: true가 없으면 거절한다. 즉, xmlhttprequest header에 쿠키가 있어야 한다.
       configuration.setAllowCredentials(true);
       
       // preflight가 전송된 후 60분 이후 만료된다.
       configuration.setMaxAge(3600L);
       
       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       source.registerCorsConfiguration("/**", configuration);
       
       return source;
	}
	
	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SpringSessionBackedSessionRegistry<S> sessionRegistry() {
		return new SpringSessionBackedSessionRegistry<>(this.sessionRepository);
	}
	
	@Bean                                                        
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/static/**");
	}
}