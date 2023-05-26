package MyProject.webapp.modle.response;

import MyProject.webapp.modle.entity.ShiftEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShiftResponse {
    @JsonProperty(value = "id")
    private Long id;
    @JsonProperty(value = "name")
    private String name;

    public ShiftResponse(ShiftEntity item) {
        this.id=item.getId();
        this.name=item.getName();
    }
}
