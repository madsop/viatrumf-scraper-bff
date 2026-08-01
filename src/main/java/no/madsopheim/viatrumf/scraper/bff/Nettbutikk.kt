package no.madsopheim.viatrumf.scraper.bff

import java.time.LocalDateTime

// TODO: Denne er tilpassa strukturen i trumf netthandel
// Vil ikkje funke for SAS online shopping utan endringar
data class Nettbutikk(
    val namn: String,
    val href: String,
    val popularitet: String,
    val verdi: String,
    val timestamp: LocalDateTime,
    val kategori: String?
) {
    constructor(stringObjectMap: Map<String, Any>) : this(
        namn = stringObjectMap["namn"].toString(),
        href = stringObjectMap["href"].toString(),
        popularitet = stringObjectMap["popularitet"].toString(),
        verdi = stringObjectMap["verdi"].toString(),
        timestamp = LocalDateTime.parse(stringObjectMap["timestamp"].toString(), datePattern),
        kategori = stringObjectMap["kategori"]?.toString()?.takeIf { it.isNotEmpty() }
    )
}