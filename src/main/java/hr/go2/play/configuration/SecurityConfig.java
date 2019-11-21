package hr.go2.play.configuration;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import hr.go2.play.impl.UserDetailsService;
import hr.go2.play.jwt.JwtConfigurer;
import hr.go2.play.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    UserDetailsService userDetailsService = userDetails();
	    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.httpBasic().disable().csrf().disable().sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
	            .antMatchers("/api/user/login").permitAll().antMatchers("/api/user/register").permitAll()
	            .antMatchers("/api/user/logout").hasAuthority("ADMIN").anyRequest().authenticated().and().csrf()
	            .disable().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint()).and()
	            .apply(new JwtConfigurer(jwtTokenProvider));
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCyrptPasswordEncoder = new BCryptPasswordEncoder();
		return bCyrptPasswordEncoder;
	}
	
	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationEntryPoint unauthorizedEntryPoint() {
	    return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
	            "Unauthorized");
	}

	@Bean
	public UserDetailsService userDetails() {
	    return new UserDetailsService();
	}
}
