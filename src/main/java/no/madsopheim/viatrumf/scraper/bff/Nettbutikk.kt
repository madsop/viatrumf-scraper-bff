package no.madsopheim.viatrumf.scraper.bff

import jakarta.xml.bind.annotation.XmlRootElement
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        this.timestamp = timestamp?.let { LocalDateTime.parse(it, datePattern) }
        this.kategori = kategori
    }

    companion object {
        private val datePattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
    }
}
