package ro.anud.globalcooldown.action;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionOnPawn {

    private Integer depth;
    private String type;
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionOnPawn)) return false;

        ActionOnPawn that = (ActionOnPawn) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
