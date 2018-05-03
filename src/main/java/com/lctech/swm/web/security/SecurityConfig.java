	package com.lctech.swm.web.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
  
    @Autowired
	UserDetailsServiceImpl userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/frontend", "/login", "/logout", "/oauth/**").permitAll()
                .antMatchers("/admin/account/**").access("hasRole('ROLE_MNU_ACCOUNT')")
                .antMatchers("/admin/company/**").access("hasRole('ROLE_MNU_COMPANY')")
                .antMatchers("/admin/agent/**").access("hasRole('ROLE_MNU_ENTERPRISE')")
                .antMatchers("/admin/users/**").access("hasRole('ROLE_MNU_INDIVIDUAL')")
                .antMatchers("/admin/device/**").access("hasRole('ROLE_MNU_DEVICE')")
                .antMatchers("/admin/authorization/**").access("hasRole('ROLE_MNU_AUTHORIZATION')")
                .antMatchers("/admin/contract/**").access("hasRole('ROLE_MNU_CONTRACT')")
                .antMatchers("/admin/report/**").access("hasRole('ROLE_MNU_REPORT')")
                .antMatchers("/admin/setting/**").access("hasRole('ROLE_MNU_SETTING')")
            .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/admin")
                .failureUrl("/login-error")
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
            .and()
                .exceptionHandling()
                .accessDeniedPage("/403");
    }

}