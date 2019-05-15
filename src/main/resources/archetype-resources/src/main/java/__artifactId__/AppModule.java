package ${package}.${artifactId};

import com.google.inject.Binder;
import ${package}.${artifactId}.authentication.Authentication;
import com.typesafe.config.Config;
import org.jooby.Env;
import org.jooby.Jooby;
import org.jooby.scope.RequestScoped;

import javax.inject.Provider;

public class AppModule implements Jooby.Module {
    @Override
    public void configure(Env env, Config conf, Binder binder) throws Throwable {
        binder.bind(Authentication.class).toProvider((Provider<Authentication>) () -> null).in(RequestScoped.class);
    }
}
