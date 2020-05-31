package no.madsopheim.viatrumf.scraper.bff;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.inject.Inject;

@Readiness
public class ReadinessProbe implements HealthCheck {

    @Inject
    FirestoreConnector connector;

    @Override
    public HealthCheckResponse call() {
        String name = "Viatrumf-scraper-bff readiness";
        return connector.isReady()
                ? HealthCheckResponse.up(name)
                : HealthCheckResponse.down(name);
    }
}
