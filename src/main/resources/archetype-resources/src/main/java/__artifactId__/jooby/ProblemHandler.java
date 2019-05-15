package ${package}.${artifactId}.jooby;

import org.jooby.Err;
import org.jooby.MediaType;
import org.jooby.Request;
import org.jooby.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zalando.problem.Problem;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

public class ProblemHandler implements Err.Handler {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProblemHandler.class);

    @Override
    public void handle(Request req, Response rsp, Err ex) throws Throwable {
        var cause = ex.getCause() == null
                ? ex
                : ex.getCause();

        LOGGER.error(cause.getMessage(), cause);

        ThrowableProblem problem;

        if (cause instanceof ThrowableProblem) {
            problem = (ThrowableProblem) cause;

            if (problem.getStatus() == null) {
                rsp.status(ex.statusCode());
            } else {
                rsp.status(problem.getStatus().getStatusCode());
            }
        } else {
            problem = Problem.builder()
                    .withDetail(cause.getMessage())
                    .withStatus(new StatusType() {
                        @Override
                        public int getStatusCode() {
                            return ex.statusCode();
                        }

                        @Override
                        public String getReasonPhrase() {
                            return ex.getMessage();
                        }
                    })
                    .build();
        }

        rsp.type(MediaType.json)
                .send(problem);
    }
}
