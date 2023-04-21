package bank.project.app;

import bank.project.dao.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BankSecurity {
    @Autowired
    RoleService service;
    AuthenticationManager authenticationManager;

    @Autowired
    LoginFailureHandler failureHandler;

    @Autowired
    LoginSuccessHandler successHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeRequests((requests)->{
            requests.anyRequest().permitAll();
        });
        httpSecurity.formLogin().loginPage("/web/log").usernameParameter("username").failureHandler(failureHandler).successHandler(successHandler).permitAll();
        httpSecurity.logout().permitAll();
        httpSecurity.csrf().disable();


        AuthenticationManagerBuilder builder=httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(service);
        authenticationManager=builder.build();
        httpSecurity.authenticationManager(authenticationManager);

        return httpSecurity.build();
    }
}

