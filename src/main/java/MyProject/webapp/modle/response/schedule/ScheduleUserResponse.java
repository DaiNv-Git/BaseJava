package MyProject.webapp.modle.response.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ScheduleUserResponse {
    @JsonProperty(value = "scheduleId")
    private Long id;
    @JsonProperty(value = "name")
    private String title;
    @JsonProperty(value = "start")
    private String startDate;
    @JsonProperty(value = "end")
    private String endDate;
    @JsonProperty(value = "color")
    private String color;
    @JsonProperty(value = "timed")
    private boolean timed;
    @JsonProperty(value = "isCanEdit")
    private boolean isCanEdit;
}
