package ${package}.${artifactId}.authentication;

import org.jooby.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class AuthenticationHandler implements Route.Before {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationHandler.class);

    @Override
    public void handle(Request request, Response response) {
        boolean isPublic = false;

        // Check if this is a "public" endpoint
        if (request.path().equals("/api/health")) {
            isPublic = true;
        }

        var header = request.header("Authorization");
        if (header.isSet() == false || header.value().length() < 6) {
            respondUnauthorized(isPublic, response);
            return;
        }

        var token = header.value().substring(7);
        if (token.isEmpty()) {
            respondUnauthorized(isPublic, response);
            return;
        }

        // Insert authentication handling logic here
    }

    private static void respondUnauthorized(boolean isPublic, Response response) {
        if (isPublic) {
            return;
        }

        response.status(Status.UNAUTHORIZED)
                .end();
    }
}
