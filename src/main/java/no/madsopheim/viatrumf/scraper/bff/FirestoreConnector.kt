package no.madsopheim.viatrumf.scraper.bff

import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.Firestore
import jakarta.annotation.PostConstruct
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

@ApplicationScoped
class FirestoreConnector(val firestore: Firestore) {

    private var collection: CollectionReference? = null

    @Inject
    @ConfigProperty(name = "collectionName")
    var collectionName: String? = null

    private var pattern: Pattern? = null

    @PostConstruct
    fun setup() {
        collection = firestore.collection(collectionName!!)
        pattern = Pattern.compile("$collectionName/")
    }

    fun finnAlleNettbutikkar(): List<String> = collection!!.listDocuments()
        .map { it.path }
        .map { pattern!!.matcher(it).replaceFirst("") }

    fun query(nettbutikknamn: String): List<Nettbutikk> =
        collection!!.document(nettbutikknamn).listCollections()
        .asSequence()
        .map { it.get() }
        .map { it.get() }
        .flatMap { it.documents }
        .map { it.getData() }
        .map { Nettbutikk(it) }
        .toList()

}

val datePattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")