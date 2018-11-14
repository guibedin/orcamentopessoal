package br.com.guibedin.orcamentopessoal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.guibedin.orcamentopessoal.filters.TokenAuthenticationFilter;
import br.com.guibedin.orcamentopessoal.helpers.RestAuthenticationEntryPoint;
import br.com.guibedin.orcamentopessoal.helpers.TokenHelper;
import br.com.guibedin.orcamentopessoal.resources.UsuarioRepository;

//import br.com.guibedin.orcamentopessoal.filters.TokenAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MyConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	TokenHelper tokenHelper;	
	@Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Bean	
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins("http://localhost:3000", "https://localhost:3000", "http://guibedin.github.io", "https://guibedin.github.io")
					.allowedMethods("*");
			 }
		};
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
	        .authorizeRequests()
	        .antMatchers("/usuario/login/").permitAll()
	        .antMatchers("/usuario/cadastrar/").permitAll()
			.antMatchers(HttpMethod.POST, "/contas/**").authenticated()
			.antMatchers(HttpMethod.GET, "/usuario/**").authenticated()
	        .and()
	        .addFilterBefore(new TokenAuthenticationFilter(tokenHelper, usuarioRepository), BasicAuthenticationFilter.class);
		
        http.csrf().disable();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	    	.ignoring()
	    	.antMatchers(HttpMethod.POST, "/usuario/**");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}
