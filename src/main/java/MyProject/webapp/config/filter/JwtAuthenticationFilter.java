package MyProject.webapp.config.filter;

import MyProject.webapp.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtToken token;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //kiem tra duong dan dang nhap
        try {
            // Lấy jwt từ request
            String authenticationHeader = getJwtFromRequest(request);
            if (!Strings.isNullOrEmpty(authenticationHeader)) {
                if (StringUtils.hasText(authenticationHeader) && token.validateToken(authenticationHeader)) {
                    // Lấy userLogin từ chuỗi jwt
                    String userLogin = token.getUserNameFromJWT(authenticationHeader);
                    // Lấy thông tin người dùng qua userLogin
                    UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userLogin);
                    if (userDetails != null) {
                        // Nếu người dùng hợp lệ, set thông tin cho Seturity Context
                        UsernamePasswordAuthenticationToken
                                usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            } else {
                filterChain.doFilter(request, response);
                return;
            }
        } catch (Exception e) {
            log.error("Error logging in : {} " + e.getMessage());
            response.setHeader("api/login", HttpStatus.BAD_REQUEST.name());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType(APPLICATION_JSON_VALUE);
            Map<String, String> error = new HashMap<>();
            error.put("message", "user login or password is not correct");
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        // Check if Authorization header contains jwt information
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}
