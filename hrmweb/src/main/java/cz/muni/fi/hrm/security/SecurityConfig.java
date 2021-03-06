package cz.muni.fi.hrm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

/**
 * security configuration for application, using spring security
 */
@Configuration
@Order(1)
@EnableWebSecurity
@ComponentScan("cz.muni.fi.hrm.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().
                withUser("admin").password("xxadmin329").roles("ADMIN")
                .and()
                .withUser("admin2").password("iAmAdmin2").roles("ADMIN");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/scripts/**", "/bower_components/**", "/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .headers().frameOptions().sameOrigin()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/admin", "/admin/*", "/html/auth/**", "/file/generateFileDbData").hasRole("ADMIN")
                .antMatchers("/", "/login", "/login*","/computation/**",
                        "/refCurve/*","/file/*", "/html/**", "/user", "/currentUser").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }
}