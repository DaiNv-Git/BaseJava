package MyProject.webapp.modle.response;

import MyProject.webapp.modle.entity.WorkTypeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkTypeResponse {
    @JsonProperty(value = "id")
    private Long id;
    @JsonProperty(value = "name")
    private String name;

    public WorkTypeResponse(WorkTypeEntity item) {
        this.id = item.getId();
        this.name = item.getName();
    }
}
