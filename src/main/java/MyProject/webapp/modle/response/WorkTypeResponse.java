package MyProject.webapp.modle.response;

import MyProject.webapp.modle.entity.WorkTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkTypeResponse {
    private Long id;
    private String name;

    public WorkTypeResponse(WorkTypeEntity item) {
        this.id = item.getId();
        this.name = item.getName();
    }
}
