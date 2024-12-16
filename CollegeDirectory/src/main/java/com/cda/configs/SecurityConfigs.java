package com.cda.configs;

import com.cda.utils.Message;
import com.cda.utils.MessageType;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigs {

    @Autowired
    private CustomSuccessHandler customSuccessHandler;
    @Autowired
    private CustomeUserDetailService userDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(authorize -> {
            authorize
            .requestMatchers("/faculty/**").hasAnyAuthority("FACULTY_MEMBER","ADMINISTRATOR").
                    requestMatchers("/students/**").hasAnyAuthority("STUDENT","ADMINISTRATOR").
                    requestMatchers("/admin/**").hasAuthority("ADMINISTRATOR").
                    requestMatchers("/user/**").permitAll()
            .anyRequest().permitAll();
        });


        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/user/login").permitAll();
                    formLogin.loginProcessingUrl("/authenticate");
                    formLogin.successHandler(customSuccessHandler);
                    formLogin.usernameParameter("username");
                    formLogin.passwordParameter("password");
                    formLogin.failureForwardUrl("/user/login");
                    formLogin.failureHandler(
                            (request, response, authentication) -> {
                                HttpSession session = request.getSession(false);
                                if (session != null) {
                                    Message message = Message.builder()
                                            .type(MessageType.red)
                                            .content("Invalid Login Credentials")
                                            .build();
                                    session.setAttribute("message", mapper().map(message, Message.class));
                                }
                                response.sendRedirect("/user/login?error=true");
                            }
                    );
                }
        );

        // Redirect unauthorized users to the access-denied page
        httpSecurity.exceptionHandling(exception -> {
            exception.accessDeniedPage("/access-denied"); // Redirect to custom warning page
        });

        // Disable CSRF for simplicity in development
        httpSecurity.csrf(AbstractHttpConfigurer::disable);


//        httpSecurity.logout(logoutForm -> {
//            logoutForm.logoutUrl("/logout");
//            logoutForm.logoutSuccessUrl("/login?logout=true");
//        });

        return httpSecurity.build();

    }
}
