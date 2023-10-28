package dio.myfirstwebapi.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dio.myfirstwebapi.tokenJWT.JWTFilter;

import org.h2.server.web.WebServlet;

@Configuration
@EnableWebSecurity
@ComponentScan
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	
	
	@Autowired
    private SecurityDatabaseService securityService;
	
	 @Autowired
	 public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		 
	        auth.userDetailsService(securityService).passwordEncoder(NoOpPasswordEncoder.getInstance());
	    }
	
	 /*
		 * Como estamos carregando os usuários do Banco 
		 * de dados com SecurityDataBaseService 
		 * não precisamos mais configurar 
		 * a criação e busca de dados na 
		 * classe WebSecurityConfig 
		 * 
		 * 
		 * @Override
             protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	         	auth.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}user123")
                .roles("USERS")
                .and()
                .withUser("admin")
                .password("{noop}master123")
                .roles("MANAGERS");
             }
		 * 
		 * */
	
	 @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication()
	                .withUser("user")
	                .password("{noop}user123")
	                .roles("USERS")
	                .and()
	                .withUser("admin")
	                .password("{noop}master123")
	                .roles("MANAGERS");
	    }
	
	 
	 private static final String[] SWAGGER_WHITELIST = {
	            "/v2/api-docs",
	            "/swagger-resources",
	            "/swagger-resources/**",
	            "/configuration/ui",
	            "/configuration/security",
	            "/swagger-ui.html",
	            "/webjars/**"
	    };
	
	/*
	 * 
	 * Essa configuração permite que
	 * não  usar preAuthorize nos métodos 
	 * em nosso controller , para 
	 * definir que usuários que tenha certa 
	 * ROLE possa cessar a rota .Já definimos aqui
	 * os usuários que tem acesso as rotas
	 * de acordo com sua role
	 * 
	 * */
	   @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.headers().frameOptions().disable();
	        http.cors().and().csrf().disable()
	                  .addFilterAfter(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
	                .authorizeRequests()
	                .antMatchers(SWAGGER_WHITELIST).permitAll()
	                .antMatchers("/h2-console/**").permitAll()
	                .antMatchers(HttpMethod.POST,"/login").permitAll()
	                .antMatchers(HttpMethod.POST,"/user/postar").permitAll()
	                .antMatchers(HttpMethod.GET,"/user").hasAnyRole("USERS","MANAGERS")
	                .antMatchers("/managers").hasAnyRole("MANAGERS")
	                .anyRequest().authenticated()
	                .and()
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    }
		
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		 @Bean //HABILITANDO ACESSAR O H2-DATABSE NA WEB
		    public ServletRegistrationBean h2servletRegistration(){
		        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
		        registrationBean.addUrlMappings("/h2-console/*");
		        return registrationBean;
		    }
		
	
}