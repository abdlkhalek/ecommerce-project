package com.alimi.ecommerce.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;


@Configuration
public class SecurityConfiguration {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//
//        // protect endpoint /api/orders only accessible to authenticated users
//        http.authorizeRequests(configurer ->
//                            configurer
//                                    .antMatchers("/api/orders/**")
//                                    .authenticated())
//                .oauth2ResourceServer()// configures oauth2resource server support
//                .jwt(); // enables jwt-encoded bearer token support
//
//        // add support for cors filters
//        http.cors();
//
//        // add content negotiation strategy to send back a friendly response based on okta response
//        http.setSharedObject(ContentNegotiationStrategy.class,
//                              new HeaderContentNegotiationStrategy());
//
//        // force a non-empty response body for 401's to make the response more friendly
//        Okta.configureResourceServer401ResponseBody(http);
//
//        return http.build();
//    }


    // for spring v3 and okta v3
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //protect endpoint /api/orders
        http.authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/api/orders/**")
                                .authenticated()
                                .anyRequest().permitAll())
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()));

        // + CORS filters
        http.cors(Customizer.withDefaults());

        // + content negotiation strategy
        http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());

        // + non-empty response body for 401 (more friendly)
        Okta.configureResourceServer401ResponseBody(http);

        // we are not using Cookies for session tracking >> disable CSRF
        http.csrf(AbstractHttpConfigurer::disable);

        // disable CSRF since we are not using cookies for session tracking
        http.csrf().disable();

        return http.build();
    }

}
