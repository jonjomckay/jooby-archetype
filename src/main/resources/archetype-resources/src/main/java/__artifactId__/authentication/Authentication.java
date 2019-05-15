package ${package}.${artifactId}.authentication;

import java.util.UUID;

public class Authentication {
    private UUID id;

    public Authentication() {
    }

    public Authentication(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
