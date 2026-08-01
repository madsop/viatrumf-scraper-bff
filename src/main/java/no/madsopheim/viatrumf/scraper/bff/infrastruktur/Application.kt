package no.madsopheim.viatrumf.scraper.bff.infrastruktur

import jakarta.ws.rs.core.Application
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
import org.eclipse.microprofile.openapi.annotations.info.Info

@OpenAPIDefinition(info = Info(title = "Viatrumf-scraper-bff", version = "1.0"))
class Application : Application()
