package com.example.SWP391_KOIFARMSHOP_BE.config;

import com.example.SWP391_KOIFARMSHOP_BE.service.AuthenticationService;
import com.example.SWP391_KOIFARMSHOP_BE.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    Filter customFilter;

    // Cấu hình chuỗi bảo mật
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Tắt CSRF (để đơn giản hóa cho môi trường phát triển)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Thêm CORS
                .authorizeHttpRequests(req -> req
                        // .requestMatchers("/admin/**").hasRole("ADMIN")
                        // .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().permitAll() // Cho phép tất cả các yêu cầu mà không cần xác thực
                        // .anyRequest().authenticated()
                )
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://103.90.227.69","http://localhost:3000")); // Địa chỉ frontend của bạn
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Các phương thức bạn cho phép
        configuration.setAllowCredentials(true); // Cho phép cookie nếu cần
        configuration.setAllowedHeaders(List.of("*")); // Hoặc chỉ định các tiêu đề cụ thể
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Áp dụng cấu hình cho tất cả các endpoint
        return source;
    }


}
