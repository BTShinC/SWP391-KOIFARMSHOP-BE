package com.example.SWP391_KOIFARMSHOP_BE.config;

import com.example.SWP391_KOIFARMSHOP_BE.service.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {


    @Lazy
    private final AuthenticationService authenticationService;

    public SecurityConfig(@Lazy AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // Mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Cấu hình AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    // Cấu hình chuỗi bảo mật
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception {

//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(withDefaults()) // Sử dụng cấu hình CORS nếu cần
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/api/login", "/api/Register").permitAll() // Các API không yêu cầu xác thực
//                        .requestMatchers("/api/account").hasAnyRole("Customer", "Admin")
//                        .anyRequest().authenticated() // Các API khác yêu cầu xác thực
//                )
//                .addFilterBefore(new Filter(), UsernamePasswordAuthenticationFilter.class) // Thêm filter xác thực JWT
//                .build();
        return http
                .csrf(AbstractHttpConfigurer::disable) // Tắt CSRF (để đơn giản hóa cho môi trường phát triển)
                .authorizeHttpRequests(req -> req
                        .anyRequest().permitAll() // Cho phép tất cả các yêu cầu mà không cần xác thực
                )
                .build();

//        return http
//                .csrf(AbstractHttpConfigurer::disable) // Tắt CSRF
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/api/login", "/api/Register").permitAll() // Các API không yêu cầu xác thực
//                        .requestMatchers("/api/account/*").hasAnyRole("Customer", "Admin") // Chỉ cho phép Customer và Admin truy cập vào /api/account
//                        .requestMatchers("/api/admin").hasRole("Admin") // Chỉ cho phép Admin truy cập vào /api/admin
//                        .requestMatchers("/api/staff").hasAnyRole("Staff", "Admin") // Cho phép Staff và Admin truy cập vào /api/staff
//                        .anyRequest().permitAll() // Các API khác yêu cầu xác thực
//                )
//                .addFilterBefore(new Filter(), UsernamePasswordAuthenticationFilter.class) // Thêm filter xác thực JWT
//                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://103.90.227.69")); // Địa chỉ frontend của bạn
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Các phương thức bạn cho phép
        configuration.setAllowCredentials(true); // Cho phép cookie nếu cần
        configuration.setAllowedHeaders(List.of("*")); // Hoặc chỉ định các tiêu đề cụ thể

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Áp dụng cấu hình cho tất cả các endpoint
        return source;
    }





//    @Lazy
//    private final AuthenticationService authenticationService;
//
//    public SecurityConfig(@Lazy AuthenticationService authenticationService) {
//        this.authenticationService = authenticationService;
//    }
//
//    // Mã hóa mật khẩu
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    // Cấu hình AuthenticationManager
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
//        return configuration.getAuthenticationManager();
//    }
//
//    // Cấu hình chuỗi bảo mật
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)  // Tắt CSRF vì bạn đang phát triển
//                .authorizeHttpRequests(req -> req
//                        .anyRequest().permitAll()  // Cho phép tất cả các yêu cầu mà không cần xác thực
//                )
//                .build();
//        }

}
