package com.sparta.testmyself.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                // image 폴더를 login 없이 허용
                .antMatchers("/images/**").permitAll()
                // css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
                // 그 외 모든 요청은 인증과정 필요
                .antMatchers("/user/**").permitAll()
                // 회원 관리 URL 전부를 login 없이 허용
                .antMatchers("/h2-console/**").permitAll()
                // h2 콘솔 허용
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .failureUrl("/user/login/error")
                .defaultSuccessUrl("/")	//로그인 완료되었을 때 이동할 페이지
                .permitAll()	//허용
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .permitAll();	//로그아웃도 허용
    }
}