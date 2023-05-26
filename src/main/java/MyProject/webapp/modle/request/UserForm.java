package MyProject.webapp.modle.request;

import MyProject.webapp.utils.Messageutils;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(description = "User information")
public class UserForm {
    @NotBlank(message = Messageutils.CANNOT_BLANK)
    @JsonProperty(value = "fullName")
    @ApiModelProperty(value = "Nguyen Văn A", example = "Nguyen Văn A", required = true)
    private String fullName;

    @Pattern(regexp="[0-9]{10}", message= Messageutils.INVALID_TEL)
    @ApiModelProperty(value = "0969686868", example = "0969686868", required = true)
    @JsonProperty(value = "phoneNumber")
    private String tel;

    @NotBlank(message = Messageutils.EMAIL_NOT_BLANK)
    @JsonProperty(value = "email")
    @ApiModelProperty(value = "abcd@gmail.com", example = "abcd@gmail.com", required = true)
    private String email;

    @JsonProperty(value = "password")
    @NotBlank(message = Messageutils.CANNOT_BLANK)
    @ApiModelProperty(value = "abd31071", example = "abd123", required = true)
    private String password;

    @NotNull(message=Messageutils.CANNOT_BLANK)
    @ApiModelProperty(value = "Giới tính (0: Nam, 1: Nữ)", example = "0", required = true)
    @JsonProperty(value = "sex")
    private Integer sex;

    @NotBlank(message = Messageutils.CANNOT_BLANK)
    @JsonProperty(value = "birthDay")
    @ApiModelProperty(value = "1990-02-05", example = "1990-02-05", required = true)
    private String birthDay;

    @JsonProperty(value = "address")
    private String address;

    @NotNull(message=Messageutils.CANNOT_BLANK)
    @ApiModelProperty(value = "Role (1 : ROLE_USER, 2 : ROLE_ADMIN)", example = "1", required = true)
    @JsonProperty(value = "role")
    private Integer role;

    @JsonProperty(value = "image")
    private MultipartFile image;

    @JsonProperty(value = "description")
    private String description;
}

