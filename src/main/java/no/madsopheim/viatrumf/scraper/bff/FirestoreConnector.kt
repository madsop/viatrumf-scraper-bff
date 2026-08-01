package no.madsopheim.viatrumf.scraper.bff

import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.Firestore
import jakarta.annotation.PostConstruct
import jakarta.enterprise.context.ApplicationScoped
import java.time.format.DateTimeFormatter

@ApplicationScoped
class FirestoreConnector(val firestore: Firestore) {

    private val collections: MutableMap<CollectionName, CollectionReference> = mutableMapOf()

    @PostConstruct
    fun setup() {
        CollectionName.entries.forEach {
            collections[it] = firestore.collection(it.mappenamn)
        }
    }

    fun finnAlleNettbutikkar(collection: CollectionName): List<String> = collections[collection]!!.listDocuments()
        .map { it.path }
        .map { it.removePrefix("${collection.mappenamn}/") }

    fun query(collection: CollectionName, nettbutikknamn: String): List<Nettbutikk> =
        collections[collection]!!.document(nettbutikknamn).listCollections()
        .asSequence()
        .map { it.get() }
        .map { it.get() }
        .flatMap { it.documents }
        .map { it.getData() }
        .map { Nettbutikk(it) }
        .toList()

}

val datePattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")

enum class CollectionName(val mappenamn: String) {
    TRUMF_NETTHANDEL("viatrumf-scraper2"),
    SAS_ONLINE_SHOPPING("sasOnlineShopping")
}