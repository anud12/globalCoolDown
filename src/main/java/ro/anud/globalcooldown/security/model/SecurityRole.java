package ro.anud.globalcooldown.security.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

public enum SecurityRole implements GrantedAuthority {
    USER("user");

    final private String authority;

    SecurityRole(final String authority) {
        this.authority = Objects.requireNonNull(authority, "authority must not be null");
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
