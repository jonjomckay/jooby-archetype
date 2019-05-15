package ${package}.${artifactId};

import ${package}.${artifactId}.authentication.AuthenticationHandler;
import ${package}.${artifactId}.jdbi.Slf4jLogger;
import ${package}.${artifactId}.jooby.ProblemHandler;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.jooby.Jooby;
import org.jooby.RequestLogger;
import org.jooby.flyway.Flywaydb;
import org.jooby.frontend.Yarn;
import org.jooby.handlers.AssetHandler;
import org.jooby.jdbc.Jdbc;
import org.jooby.jdbi.Jdbi3;
import org.jooby.json.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zalando.problem.ProblemModule;

public class App extends Jooby {
    private final static Logger LOGGER = LoggerFactory.getLogger(App.class);

    {
        use(new Jackson()
                .module(new ProblemModule())
                .doWith(mapper -> mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)));
        use(new Jdbc());
        use(new Jdbi3()
                .transactionPerRequest()
                .doWith((jdbi, config) -> jdbi.setSqlLogger(new Slf4jLogger())));
        use(new Flywaydb());
        use("*", new RequestLogger()
                .latency());

        use(new AppModule());

        on("dev", () -> {
            use(new Yarn("v11.10.0", "v1.13.0")
                    .onStart(yarn -> yarn.execute("start"))
            );
        });

        err(new ProblemHandler());

        assets("/", "index.html");
        assets("/static/**");

        // Authentication
        before((req, rsp) -> require(AuthenticationHandler.class)
                .handle(req, rsp));

        use("GET", "*", new AssetHandler("index.html"));
    }

    public static void main(final String[] args) {
        run(App::new, args);
    }
}
