package com.koreait.facebook.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetails;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/css/**","/js/**","/img/**","error","favicon.ico","/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        //csrf는 사이트간의 요청 위조 -> .disable로 무력화시킴
        http.authorizeRequests()
                .antMatchers("/user/login","/user/join","/user/auth").permitAll()
                .anyRequest().authenticated();
//permitAll 누구가 들어갈 수 있다. 그 외의 주소 anyRequest는 권한 필요
        http.formLogin()
                .loginPage("/user/login")
                .usernameParameter("email")
                .passwordParameter("pw")
                .defaultSuccessUrl("/feed/home");

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/login")
                .invalidateHttpSession(true);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
    }
}
