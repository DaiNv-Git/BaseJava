package MyProject.webapp.modle.response.adminResponse;

import MyProject.webapp.modle.entity.UserEntity;
import MyProject.webapp.modle.entity.WorkingScheduleEntity;
import MyProject.webapp.modle.response.schedule.ScheduleUserResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleAdminResponse {
    @JsonProperty(value = "userId")
    private Long userId;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "image")
    private byte[] image;
    @JsonProperty(value = "monday")
    private List<SubScheduleResponse> monday = new ArrayList<>();
    @JsonProperty(value = "tuesday")
    private List<SubScheduleResponse> tuesday = new ArrayList<>();
    @JsonProperty(value = "wednesday")
    private List<SubScheduleResponse> wednesday = new ArrayList<>();
    @JsonProperty(value = "thursday")
    private List<SubScheduleResponse> thursday = new ArrayList<>();
    @JsonProperty(value = "friday")
    private List<SubScheduleResponse> friday = new ArrayList<>();
    @JsonProperty(value = "saturday")
    private List<SubScheduleResponse> saturday = new ArrayList<>();
    @JsonProperty(value = "sunday")
    private List<SubScheduleResponse> sunday = new ArrayList<>();

    public ScheduleAdminResponse(UserEntity user) {
        this.userId = user.getId();
        this.name = user.getFullName();
        this.image = user.getImage() ;
    }

    // whatDay:  giá trị từ 1 (Thứ Hai) đến 7 (Chủ Nhật)
    public void setScheduleData(int whatDay, WorkingScheduleEntity event) {
        switch (whatDay) {
            case 1:
                this.monday.add(new SubScheduleResponse(event));
                break;
            case 2:
                this.tuesday.add(new SubScheduleResponse(event));
                break;
            case 3:
                this.wednesday.add(new SubScheduleResponse(event));
                break;
            case 4:
                this.thursday.add(new SubScheduleResponse(event));
                break;
            case 5:
                this.friday.add(new SubScheduleResponse(event));
                break;
            case 6:
                this.saturday.add(new SubScheduleResponse(event));
                break;
            case 7:
                this.sunday.add(new SubScheduleResponse(event));
                break;
            default:
                break;
        }
    }

}
