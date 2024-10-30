package com.example.SWP391_KOIFARMSHOP_BE.config;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

@Component
public class Filter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    HandlerExceptionResolver resolver;

    // đinhj nghĩa cho thằng filter nhưng thằng yêu cầu tới cái đường dẫn này cho phép truy cập
   private final List<String> AUTH_PERMISSION = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/api/authen/login",
            "/api/authen/Register",
            "/api/role/post",
            "/api/authen/forgot",
            "/api/authen/reset",
            "/api/product/getall",
            "/api/productcombo/getall",
            "/api/carePackages",
            "/api/productcombo/get/{id}",
            "/api/product/get/{id}"
    );

    // có cho phép truy cập hay ko
    public boolean checkPublicAPI(String uri){
        //uri: /api/login
        // nếu gặp những cái api trong list ở trên  => cho phép truy cập luôn => true
        //check coi có match với cái patch ko
        AntPathMatcher patchMatch = new AntPathMatcher();
        //nếu mà ko thì checktoken => false
        return  AUTH_PERMISSION.stream().anyMatch(pattern -> patchMatch.match(pattern, uri));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // kiểm tra trước khi cho phép truy cập vào controller
        boolean isPublicAPI = checkPublicAPI(request.getRequestURI());

        if (isPublicAPI) {
            filterChain.doFilter(request, response);
        } else {
            // lấy token
            String token = getToken(request);
            if (token == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Empty token");
//              return;
            }
            Account account;
            // => có token
            //check xem token có đúng hay không => lấy thông tin account từ token
            try {
                account = tokenService.getAccountByToken(token);
                String roles = tokenService.getRoleByToken(token);

                System.out.println(request.getRequestURI());
                System.out.println(roles);

                if(request.getRequestURI().contains("/api/question/update-status") && !roles.contains("Admin")&& !roles.contains("Staff")) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not permission");
                    return;
                }
                if(request.getRequestURI().contains("/api/question/all") && !roles.contains("Admin")&& !roles.contains("Staff")) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not permission");
                    return;
                }
                if(request.getRequestURI().contains("/api/feedback/all") && !roles.contains("Admin")&& !roles.contains("Staff")) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not permission");
                    return;
                }
                if(request.getRequestURI().contains("/api/report") && !roles.contains("Admin")) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not permission");
                    return;
                }
                if(request.getRequestURI().contains("/api/authen/account") && !roles.contains("Admin")&& !roles.contains("Staff")) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not permission");
                    return;
                }
                if(request.getRequestURI().contains("/api/product/getall") && !roles.contains("Admin")&& !roles.contains("Staff")) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not permission");
                    return;
                }
                if(request.getRequestURI().contains("/api/productcombo/getall") && !roles.contains("Admin")&& !roles.contains("Staff")) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not permission");
                    return;
                }
                if(request.getRequestURI().contains("/api/consignments") && !roles.contains("Admin")&& !roles.contains("Staff")) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not permission");
                    return;
                }
                if(request.getRequestURI().contains("/api/orders") && !roles.contains("Admin")&& !roles.contains("Staff")) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not permission");
                    return;
                }
                if(request.getRequestURI().contains("/api/transactions/all") && !roles.contains("Admin")&& !roles.contains("Staff")) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not permission");
                    return;
                }




            } catch (ExpiredJwtException e) {
                // response token hêt hạn
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
                return;
            } catch (MalformedJwtException malformedJwtException) {
                // respone token sai
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }

            //=> token chuẩn
            // cho phép truy cập
            // lưu lại thông tin cuar account
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account, token, account.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        }
    }
        public String getToken (HttpServletRequest request){
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                // Trả về null hoặc có thể ném một ngoại lệ nếu cần thiết
                return null;
            }
            return authHeader.substring(7);
        }
    }
