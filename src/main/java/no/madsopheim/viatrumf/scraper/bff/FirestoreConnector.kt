package no.madsopheim.viatrumf.scraper.bff

import jakarta.enterprise.context.ApplicationScoped
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
}
