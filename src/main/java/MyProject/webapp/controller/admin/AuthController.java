package MyProject.webapp.controller.admin;

import MyProject.webapp.jwt.AuthTokenFilter;
import MyProject.webapp.jwt.JwtUtils;
import MyProject.webapp.jwt.UserDetailsImpl;
import MyProject.webapp.modle.request.AuthLogin;
import MyProject.webapp.modle.response.BaseResponse;
import MyProject.webapp.response.AuthResponse;
import MyProject.webapp.utils.Messageutils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
//    private final UserService userService;
    private final AuthTokenFilter authTokenFilter;

    public AuthController(AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils, AuthTokenFilter authTokenFilter) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.authTokenFilter = authTokenFilter;
    }

    @PostMapping("/")
    public ResponseEntity<BaseResponse<Object>> user(@Valid @RequestBody AuthLogin loginRequest) {
        try {
            // Authenticate from username and password.
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            // Set authentication information to Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtUtils.generateJwtToken(userDetails);

//            String refreshToken = jwtUtils.generateRefreshToken(userDetails);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(HttpStatus.OK.value(), Messageutils.SUCCESSFULLY, new AuthResponse(token, null)));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
