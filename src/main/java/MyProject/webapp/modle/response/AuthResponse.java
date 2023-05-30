package MyProject.webapp.modle.response;

import MyProject.webapp.jwt.UserDetailsImpl;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    @JsonProperty(value = "token")
    private String token;
    @JsonProperty(value = "refreshToken")
    private String refreshToken;
    @JsonProperty(value = "userId")
    private Long userId;
    @JsonProperty(value = "userName")
    private String userName;
    @JsonProperty(value = "isAdmin")
    private boolean isAdmin;

    public AuthResponse(String token, String refreshToken, UserDetailsImpl userDetails) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.userId = userDetails.getId();
        this.userName = userDetails.getName();
        this.isAdmin = getRoleUserLogin(userDetails.getAuthorities());
    }

    private boolean getRoleUserLogin(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }
}
