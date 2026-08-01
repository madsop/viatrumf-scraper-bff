package no.madsopheim.viatrumf.scraper.bff

import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import jakarta.annotation.PostConstruct
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.util.regex.Pattern

@ApplicationScoped
class NettbutikkRepository {
    private var collection: CollectionReference? = null

    @Inject
    @ConfigProperty(name = "collectionName")
    var collectionName: String? = null

    @Inject
    var firestore: Firestore? = null

    private var pattern: Pattern? = null

    @PostConstruct
    fun setup() {
        collection = firestore!!.collection(collectionName!!)
        pattern = Pattern.compile(collectionName + "/")
    }

    fun listDocuments(): Iterable<DocumentReference?> {
        return collection!!.listDocuments()
    }

    fun document(nettbutikknamn: String): DocumentReference {
        return collection!!.document(nettbutikknamn)
    }

    val isReady: Boolean
        get() = collection != null

    fun removeCollectionName(path: String): String? {
        return pattern!!.matcher(path).replaceFirst("")
    }
}
