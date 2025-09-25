package com.hazalyarimdunya.auth_jwt_app.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration // Spring uygulaması ayağa kalkarken burada tanımlanan bean’leri yükler.
//public class SecurityConfig {
//
//    //Spring Security, uygulamaya gelen request’leri bu filter chain üzerinden geçirir.
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {//HttpSecurity, hangi endpointlere nasıl güvenlik uygulanacağını tanımlar.
//        http.csrf(csrf -> csrf.disable()) //CSRF (Cross-Site Request Forgery) saldırılarına karşı koruma mekanizmasıdır.(Siteler Arası İstek Sahteciliği).JWT tabanlı auth yapılacağı için CSRF token kapatilir.
//                .authorizeHttpRequests(auth->auth
//                        .requestMatchers("/auth/**").permitAll() // /auth/ ile başlayan tüm endpointler authentication olmadan erişilebilir.
//                        .requestMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
//                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                        .anyRequest().authenticated()//Diğer tüm endpointler için kimlik doğrulaması gerekir.Yani Kullanıcı JWT token olmadan buralara erişemez.
//
//                );
//        return http.build();
//    }
//}

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/user/**").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable())
                .sessionManagement(session -> session.disable());

        return http.build();
    }
}

