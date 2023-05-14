package MyProject.webapp.modle.response;

import MyProject.webapp.modle.entity.ShiftEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShiftResponse {
    private Long id;
    private String name;

    public ShiftResponse(ShiftEntity item) {
        this.id=item.getId();
        this.name=item.getName();
    }
}
