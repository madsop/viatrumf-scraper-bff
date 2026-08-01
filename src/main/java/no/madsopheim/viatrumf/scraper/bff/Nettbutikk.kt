package no.madsopheim.viatrumf.scraper.bff

import no.madsopheim.viatrumf.scraper.bff.NettbutikkRepository.isReady
import no.madsopheim.viatrumf.scraper.bff.NettbutikkRepository.listDocuments
import no.madsopheim.viatrumf.scraper.bff.NettbutikkRepository.document
import jakarta.xml.bind.annotation.XmlRootElement
import no.madsopheim.viatrumf.scraper.bff.Nettbutikk
import jakarta.inject.Singleton
import no.madsopheim.viatrumf.scraper.bff.api.INettbutikkController
import no.madsopheim.viatrumf.scraper.bff.FirestoreConnector
import jakarta.ws.rs.GET
import jakarta.ws.rs.container.ContainerResponseFilter
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerResponseContext
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
import org.eclipse.microprofile.health.Readiness
import jakarta.enterprise.context.ApplicationScoped
import no.madsopheim.viatrumf.scraper.bff.NettbutikkRepository
import java.util.stream.StreamSupport
import com.google.cloud.firestore.DocumentReference
import java.util.stream.Collectors
import com.google.cloud.firestore.CollectionReference
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.QuerySnapshot
import com.google.cloud.firestore.QueryDocumentSnapshot
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Optional
import java.util.function.Function

@XmlRootElement
class Nettbutikk {
    var namn: String? = null
    var href: String? = null
    var popularitet: String? = null
    var verdi: String? = null
    var timestamp: LocalDateTime? = null
    var kategori: String? = null

    constructor()

    constructor(
        namn: String?,
        href: String?,
        popularitet: String?,
        verdi: String?,
        timestamp: String?,
        kategori: String?
    ) {
        this.namn = namn
        this.href = href
        this.popularitet = popularitet
        this.verdi = verdi
        this.timestamp = Optional.ofNullable<String?>(timestamp)
            .map<LocalDateTime>(Function { t: String? -> LocalDateTime.parse(t, datePattern) }).orElse(null)
        this.kategori = kategori
    }

    companion object {
        private val datePattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
    }
}
