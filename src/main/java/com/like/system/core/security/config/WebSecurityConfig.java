//package com.like.system.core.security.config;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.oauth2.client.endpoint.NimbusAuthorizationCodeTokenResponseClient;
//import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
//import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
//import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
//import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
//import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
//import org.springframework.security.web.csrf.CsrfFilter;
//import org.springframework.security.web.csrf.CsrfTokenRepository;
//import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.CorsUtils;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import com.like.system.core.oauth.CustomOAuth2UserService;
//import com.like.system.core.security.CustomCsrfFilter;
//import com.like.system.core.security.RestAuthenticationEntryPoint;
//import com.like.system.core.security.RestLoginFailureHandler;
//import com.like.system.core.security.RestLoginSuccessHandler;
//import com.like.system.user.service.SpringSecurityUserService;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Configuration
//@EnableWebSecurity
//@Profile("!localtest")
//public class WebSecurityConfig { //extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	SpringSecurityUserService userService;
//	
//	@Autowired
//	private CustomOAuth2UserService customOAuth2UserService;
//	
//	/*
//	 * ???????????? ?????? ????????? ?????? redirect(302)????????? ?????? 401 Status ??????
//	 */
//	@Autowired
//	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//		
//	/**
//	 * ????????? ????????? default target page??? redirect(302)?????? ?????? Http Status(200) ??????
//	 */
//	@Autowired
//	private RestLoginSuccessHandler authSuccessHandler;
//	
//	/**
//	 * ????????? ?????? ??? redirect(302)?????? ?????? Http Status(401) ??????
//	 */
//	@Autowired
//	private RestLoginFailureHandler authFailureHandler;
//	
//	private static final String[] CSRF_IGNORE = {"/api/system/user/login","/static/**"};
//	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/static/**");
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		
//		http.csrf()
//				.ignoringRequestMatchers(CSRF_IGNORE)				
//				.csrfTokenRepository(csrfTokenRepository()).and()				
//				.addFilterAfter(new CustomCsrfFilter(), CsrfFilter.class)				
//			.cors().configurationSource(corsConfigurationSource()).and()			
//			.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).and()			
//			.authorizeRequests()
//			.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()				
//				.antMatchers("/api/system/user/login").permitAll()			// ????????? API								
//				.antMatchers("/oauth/user").permitAll()
//				.antMatchers("/oauth2/authorization/**").permitAll()				
//				.antMatchers("/ex").permitAll()
//				
//				//.antMatchers("/common/menuhierarchy/**").permitAll()
//				//.antMatchers("/grw/**").permitAll()//hasRole("USER")							
//				.anyRequest().authenticated().and()		// ????????? ????????? ??????
//				//.anyRequest().permitAll().and()				// ?????? ?????? ??????(???????????????)
//			// ?????? ????????? HTTPS??? ?????? ??????
//			//.requiresChannel().anyRequest().requiresSecure().and()
//			
//			.formLogin()				
//				.loginProcessingUrl("/login")				
//				.usernameParameter("username")
//				.passwordParameter("password")
//				.successHandler(authSuccessHandler)
//				.failureHandler(authFailureHandler)
//				.permitAll().and()			
//			.logout()
//				.logoutUrl("/common/user/logout")
//				.logoutSuccessHandler(this::logoutSuccessHandler)
//				.invalidateHttpSession(true)
//				.deleteCookies("JSESSIONID")
//				.permitAll().and()
//			.oauth2Login()
//				.defaultSuccessUrl("/loginSuccess")							
//				.authorizationEndpoint()
//					.authorizationRequestRepository(authorizationRequestRepository()).and()
//				.tokenEndpoint()
//					.accessTokenResponseClient(accessTokenResponseClient()).and()
//                .userInfoEndpoint()
//                    .userService(customOAuth2UserService);
//			//http.portMapper().http(8080).mapsTo(8443);
//		//http.addFilterBefore(myAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);	
//		
//	}
//	
//	@Bean
//	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
//		return new NimbusAuthorizationCodeTokenResponseClient();
//	}
//
//	@Bean
//	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {		
//		return new HttpSessionOAuth2AuthorizationRequestRepository();
//	}
//
//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//       CorsConfiguration configuration = new CorsConfiguration();              
//       //configuration.setAllowedOrigins(Arrays.asList("http://localhost:8090","http://localhost:4200","http://kbm0417.gonetis.com:4200","http://kbm0417.gonetis.com"));
//       configuration.setAllowedOrigins(Arrays.asList("*"));
//       configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
//       //configuration.setAllowedMethods(Arrays.asList("*"));
//       
//       // Request Header??? Http default ????????? ????????? ?????? ????????????.
//       configuration.setAllowedHeaders(Arrays.asList("Origin", "Accept", "X-Requested-With", "Content-Type", "remember-me", "x-auth-token", "Authorization", "x-xsrf-token", "XSRF-TOKEN", "X-Access-Token","Access-Control-Request-Method","Access-Control-Request-Headers"));
//       //configuration.setAllowedHeaders(Arrays.asList("*"));
//       
//       //configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin","Access-Control-Allow-Credentials"));       
//       
//       // browser?????? Access-Control-Allow-Credentials: true??? ????????? ????????????. ???, xmlhttprequest header??? ????????? ????????? ??????.
//       configuration.setAllowCredentials(true);
//       
//       // preflight??? ????????? ??? 60??? ?????? ????????????.
//       configuration.setMaxAge(3600L);
//       
//       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//       source.registerCorsConfiguration("/**", configuration);
//       
//       return source;
//	}
//	
//	@Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//         return super.authenticationManagerBean();
//	}
//			
//	@Bean
//	public PasswordEncoder bCryptPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//				
//		auth.userDetailsService(userService).passwordEncoder(this.bCryptPasswordEncoder());
//		
//		log.info(auth.toString());
//	}
//	
//	private CsrfTokenRepository csrfTokenRepository() {
//		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//		repository.setHeaderName(CustomCsrfFilter.CSRF_COOKIE_NAME);
//		
//		return repository;
//	}
//
//	
//	/*
//	@Bean
//	public RequestBodyReaderAuthenticationFilter myAuthenticationFilter() throws Exception {
//		RequestBodyReaderAuthenticationFilter authenticationFilter = new RequestBodyReaderAuthenticationFilter();
//		authenticationFilter.setAuthenticationManager(this.authenticationManagerBean());		
//		// ????????? ?????? ?????? authenticationManagerBean??? ?????? ?????? ???
//		authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/common/user/login", "POST"));
//		// loginProcessingUrl??? ????????? ????????? ?????? ??? ????????? ????????? ?????? default ????????? ?????????
//		return authenticationFilter;
//	}
//	*/
//	
// 
//    private void logoutSuccessHandler(
//        HttpServletRequest request,
//        HttpServletResponse response,
//        Authentication authentication) throws IOException {
// 
//        response.setStatus(HttpStatus.OK.value());
//    }             
//}
