package no.madsopheim.viatrumf.scraper.bff.infrastruktur

import no.madsopheim.viatrumf.scraper.bff.FirestoreConnector
import org.eclipse.microprofile.health.HealthCheck
import org.eclipse.microprofile.health.HealthCheckResponse
import org.eclipse.microprofile.health.Readiness

@Readiness
class ReadinessProbe(val connector: FirestoreConnector) : HealthCheck {
    override fun call(): HealthCheckResponse = if (connector.isReady)
        HealthCheckResponse.up("Viatrumf-scraper-bff readiness")
    else
        HealthCheckResponse.down("Viatrumf-scraper-bff readiness")
}
