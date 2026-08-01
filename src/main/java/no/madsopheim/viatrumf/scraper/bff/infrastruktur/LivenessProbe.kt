package no.madsopheim.viatrumf.scraper.bff.infrastruktur

import org.eclipse.microprofile.health.HealthCheck
import org.eclipse.microprofile.health.HealthCheckResponse
import org.eclipse.microprofile.health.Liveness

@Liveness
class LivenessProbe : HealthCheck {
    override fun call(): HealthCheckResponse = HealthCheckResponse.up("Viatrumf-scraper-bff liveness")
}
