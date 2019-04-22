package ro.anud.globalCooldown.trait;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "class")
public interface Trait {
}
