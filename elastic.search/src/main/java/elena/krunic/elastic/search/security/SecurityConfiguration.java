package elena.krunic.elastic.search.security;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import elena.krunic.elastic.search.security.auth.RestAuthenticationEntryPoint;
import elena.krunic.elastic.search.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint; 
	
	@Autowired
	private void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
	    return super.userDetailsService();
	}
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
	    AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
	    authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
	    return authenticationTokenFilter;
	 }
	  
	
	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
	      http.csrf().disable().cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	      		.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
	      		.and().authorizeRequests().antMatchers("/api/users/login").permitAll();
				
	      http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	  }
}
