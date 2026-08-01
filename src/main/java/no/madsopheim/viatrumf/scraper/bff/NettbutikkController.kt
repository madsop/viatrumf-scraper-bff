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
import jakarta.inject.Inject
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam

@Path("/nettbutikkar")
@Singleton
class NettbutikkController : INettbutikkController {
    @Inject
    var firestoreConnector: FirestoreConnector? = null

    override fun getAlleNettbutikkar(): MutableList<String?>? {
        return firestoreConnector!!.finnAlleNettbutikkar()
    }

    override fun hentInfoForNettbutikk(@PathParam("nettbutikk") nettbutikk: String?): MutableList<Nettbutikk?>? {
        return firestoreConnector!!.query(nettbutikk)
    }
}
