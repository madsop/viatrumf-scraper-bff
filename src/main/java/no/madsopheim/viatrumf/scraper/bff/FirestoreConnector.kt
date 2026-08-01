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
import java.lang.Exception
import java.lang.RuntimeException

@ApplicationScoped
class FirestoreConnector {
    @Inject
    var nettbutikkRepository: NettbutikkRepository? = null

    val isReady: Boolean
        get() = nettbutikkRepository!!.isReady

    fun finnAlleNettbutikkar(): MutableList<String?> {
        return StreamSupport.stream<DocumentReference?>(nettbutikkRepository!!.listDocuments().spliterator(), true)
            .map<String?> { obj: DocumentReference? -> obj!!.getPath() }
            .map<String?> { path: String? -> nettbutikkRepository!!.removeCollectionName(path!!) }
            .collect(Collectors.toList())
    }

    fun query(nettbutikknamn: String): MutableList<Nettbutikk?> {
        return StreamSupport.stream<CollectionReference?>(
            nettbutikkRepository!!.document(nettbutikknamn).listCollections().spliterator(), false
        )
            .map<ApiFuture<QuerySnapshot?>?> { obj: CollectionReference? -> obj!!.get() }
            .map<QuerySnapshot?> { future: ApiFuture<QuerySnapshot?>? -> this.wrappingGet(future!!) }
            .map<MutableList<QueryDocumentSnapshot?>?> { obj: QuerySnapshot? -> obj!!.getDocuments() }
            .flatMap<QueryDocumentSnapshot?> { obj: MutableList<QueryDocumentSnapshot?>? -> obj!!.stream() }
            .map<MutableMap<String?, Any?>?> { obj: QueryDocumentSnapshot? -> obj!!.getData() }
            .map<Nettbutikk?> { stringObjectMap: MutableMap<String?, Any?>? -> this.joinData(stringObjectMap!!) }
            .collect(Collectors.toList())
    }

    fun wrappingGet(future: ApiFuture<QuerySnapshot?>): QuerySnapshot? {
        try {
            return future.get()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun joinData(stringObjectMap: MutableMap<String?, Any?>): Nettbutikk {
        return Nettbutikk(
            stringObjectMap.get("namn") as String?,
            stringObjectMap.get("href") as String?,
            stringObjectMap.get("popularitet") as String?,
            stringObjectMap.get("verdi") as String?,
            stringObjectMap.get("timestamp") as String?,
            stringObjectMap.get("kategori") as String?
        )
    }
}
