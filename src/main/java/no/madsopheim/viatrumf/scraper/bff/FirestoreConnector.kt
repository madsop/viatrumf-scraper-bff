package no.madsopheim.viatrumf.scraper.bff

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.QuerySnapshot
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class FirestoreConnector(val nettbutikkRepository: NettbutikkRepository) {

    val isReady: Boolean
        get() = nettbutikkRepository.isReady

    fun finnAlleNettbutikkar(): List<String> = nettbutikkRepository.listDocuments()
        .map { it.path }
        .map { nettbutikkRepository.removeCollectionName(it) }

    fun query(nettbutikknamn: String): List<Nettbutikk> =
        nettbutikkRepository.document(nettbutikknamn).listCollections()
        .asSequence()
        .map { it.get() }
        .map { future -> this.wrappingGet(future) }
        .map { it.documents }
        .flatMap { it }
        .map { it.getData() }
        .map { joinData(it) }
        .toList()

    fun wrappingGet(future: ApiFuture<QuerySnapshot?>): QuerySnapshot = try {
        future.get()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }!!

    private fun joinData(stringObjectMap: Map<String, Any>) = Nettbutikk(
        stringObjectMap["namn"] as String,
        stringObjectMap["href"] as String,
        stringObjectMap["popularitet"] as String,
        stringObjectMap["verdi"] as String,
        stringObjectMap["timestamp"] as String,
        stringObjectMap["kategori"] as String
    )
}
