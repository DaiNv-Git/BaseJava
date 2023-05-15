package MyProject.webapp.modle.request;

import MyProject.webapp.utils.Messageutils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AuthLogin {
    @NotBlank(message = Messageutils.EMAIL_NOT_BLANK)
    @Email
    @JsonProperty(value = "email")
    private String email;
    @NotBlank(message = Messageutils.PASSWORD_NOT_BLANK)
    @JsonProperty(value = "password")
    private String password;
}
