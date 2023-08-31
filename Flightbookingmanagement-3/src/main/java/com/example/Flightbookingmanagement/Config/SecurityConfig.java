package com.example.Flightbookingmanagement.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable();
    	http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/user/**").hasRole("USER")
        .antMatchers("/**").permitAll()
        	.antMatchers("/registe/**").hasAuthority("USER")
            .antMatchers("/api/passengers/**").hasAnyAuthority("USER", "ADMIN")
            .antMatchers("/api/bookings/**").hasAuthority("USER")
            .antMatchers("/api/flights/**").hasAuthority("USER")
            .antMatchers("/api/**").hasAuthority("ADMIN")
            .anyRequest().authenticated()
            .and().formLogin()
            .and()
            .logout()
            .permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/403")
        	.and()
        	.httpBasic();
    }

	


}