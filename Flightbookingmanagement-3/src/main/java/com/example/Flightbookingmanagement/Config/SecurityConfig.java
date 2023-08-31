package com.example.Flightbookingmanagement.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.swagger.models.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable();
        http.authorizeRequests().antMatchers("/register").permitAll().and().authorizeRequests()
        	.antMatchers("/users/**").hasAuthority("ADMIN")
            .antMatchers("/api/passengers/**").hasAnyAuthority("USER", "ADMIN")
            .antMatchers("/api/bookings/**").hasAuthority("USER")
            .antMatchers("/api/flights/**").hasAuthority("USER")
            .antMatchers("/api/**").hasAuthority("ADMIN")
            .anyRequest().authenticated()
            .and().formLogin().permitAll()
            .and().exceptionHandling().accessDeniedPage("/403")
        	.and().httpBasic();
    }


}