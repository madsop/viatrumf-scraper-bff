package no.madsopheim.viatrumf.scraper.bff

import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import jakarta.annotation.PostConstruct
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

@ApplicationScoped
class FirestoreConnector(val firestore: Firestore) {

    fun finnAlleNettbutikkar(): List<String> = listDocuments()
        .map { it.path }
        .map { removeCollectionName(it) }

    fun query(nettbutikknamn: String): List<Nettbutikk> =
        document(nettbutikknamn).listCollections()
        .asSequence()
        .map { it.get() }
        .map { it.get() }
        .flatMap { it.documents }
        .map { it.getData() }
        .map { joinData(it) }
        .toList()

    private fun joinData(stringObjectMap: Map<String, Any>) = Nettbutikk(
        stringObjectMap["namn"] as String,
        stringObjectMap["href"] as String,
        stringObjectMap["popularitet"] as String,
        stringObjectMap["verdi"] as String,
        (stringObjectMap["timestamp"] as String).let { LocalDateTime.parse(it, datePattern) },
        stringObjectMap["kategori"] as String?
    )

    private val datePattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
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

    fun listDocuments(): Iterable<DocumentReference> = collection!!.listDocuments()

    fun document(nettbutikknamn: String): DocumentReference = collection!!.document(nettbutikknamn)

    val isReady: Boolean
        get() = collection != null

    fun removeCollectionName(path: String): String = pattern!!.matcher(path).replaceFirst("")
}
