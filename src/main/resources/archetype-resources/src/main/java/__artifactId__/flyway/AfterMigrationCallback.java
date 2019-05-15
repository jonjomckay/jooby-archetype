package ${package}.${artifactId}.flyway;

import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class AfterMigrationCallback implements Callback {
    private final static Logger LOGGER = LoggerFactory.getLogger(AfterMigrationCallback.class);

    @Override
    public boolean supports(Event event, Context context) {
        if (event.getId().equals("afterMigrate")) {
            return true;
        }

        return false;
    }

    @Override
    public boolean canHandleInTransaction(Event event, Context context) {
        return true;
    }

    @Override
    public void handle(Event event, Context context) {
        LOGGER.info("Inserting seed data");

        var connection = context.getConnection();

        try {
            Statement statement = connection.createStatement();

            // Insert some seed data here

            statement.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
