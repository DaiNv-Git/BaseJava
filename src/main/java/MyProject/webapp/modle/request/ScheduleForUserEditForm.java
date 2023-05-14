package MyProject.webapp.modle.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
public class ScheduleForUserEditForm {
    @JsonProperty(value = "scheduleId")
    @NotNull
    private Long id;
    @JsonProperty(value = "title")
    @NotBlank(message = "Title can not empty")
    private String title;
    @NotNull(message = "Work Date can not empty")
    @JsonProperty(value = "workDate")
    private String workDate;
    @NotNull(message = "Start Time can not empty")
    @JsonProperty(value = "startTime")
    private String startTime;
    @NotNull(message = "End Time can not empty")
    @JsonProperty(value = "endTime")
    private String endTime;
    @JsonProperty(value = "description")
    private String description;
    @NotNull
    @JsonProperty(value = "shiftId")
    private Long shiftId;
    @NotNull
    @JsonProperty(value = "workTypeId")
    private Long workTypeId;
}
