package MyProject.webapp.modle.response.schedule;
import MyProject.webapp.modle.entity.WorkingScheduleEntity;
import MyProject.webapp.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleUserDetailResponse {
    @JsonProperty(value = "scheduleId")
    private Long id;
    @JsonProperty(value = "title")
    private String title;
    @JsonProperty(value = "workDate")
    private String workDate;
    @JsonProperty(value = "startTime")
    private String startTime;
    @JsonProperty(value = "endTime")
    private String endTime;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "shiftId")
    private Long shiftId;
    @JsonProperty(value = "workTypeId")
    private Long workTypeId;

    public ScheduleUserDetailResponse(WorkingScheduleEntity workingScheduleEntity) {
        this.id = workingScheduleEntity.getId();
        this.title = workingScheduleEntity.getWorkTitle();
        this.workDate = DateUtils.parseLocalDateToString(workingScheduleEntity.getWorkDate());
        this.startTime = DateUtils.parseLocalTimeToString(workingScheduleEntity.getStartTime());
        this.endTime = DateUtils.parseLocalTimeToString(workingScheduleEntity.getEndTime());
        this.description = workingScheduleEntity.getDescription();
        this.shiftId = workingScheduleEntity.getShift()==null ? null : workingScheduleEntity.getShift().getId();
        this.workTypeId = workingScheduleEntity.getWorkType()==null ? null : workingScheduleEntity.getWorkType().getId();
    }
}
