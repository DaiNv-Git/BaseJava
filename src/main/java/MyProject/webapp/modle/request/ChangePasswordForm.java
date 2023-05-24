package MyProject.webapp.modle.request;

import MyProject.webapp.utils.Messageutils;
import MyProject.webapp.validate.constraint.NewPasswordMatch;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordForm {
    @NotBlank(message = Messageutils.CANNOT_BLANK)
    @ApiModelProperty(value = "abd31071", example = "abd123", required = true)
    @JsonProperty(value = "old-password")
    private String oldPassword;

    @NotBlank(message = Messageutils.CANNOT_BLANK)
    @ApiModelProperty(value = "abd31071", example = "abd123", required = true)
    @JsonProperty(value = "new-password")
    @NewPasswordMatch
    private String newPassword;

    @NotBlank(message = Messageutils.CANNOT_BLANK)
    @ApiModelProperty(value = "abd31071", example = "abd123", required = true)
    @JsonProperty(value = "repeat-password")
    private String repeatPassword;
}
