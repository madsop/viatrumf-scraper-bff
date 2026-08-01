package no.madsopheim.viatrumf.scraper.bff

import java.time.LocalDateTime

data class Nettbutikk(
    val namn: String,
    val href: String,
    val verdi: String,
    val timestamp: LocalDateTime,
    val kategori: String?,
    val type: Type?
) {
    constructor(stringObjectMap: Map<String, Any>) : this(
        namn = stringObjectMap["namn"].toString(),
        href = stringObjectMap["href"].toString(),
        verdi = stringObjectMap["verdi"]?.toString()?.takeIf { it.isNotEmpty() } ?: stringObjectMap["points"].toString(),
        timestamp = LocalDateTime.parse(stringObjectMap["timestamp"].toString(), datePattern),
        kategori = stringObjectMap["kategori"]?.toString()?.takeIf { it.isNotEmpty() },
        type = stringObjectMap["currency"]?.toString()?.takeIf { it.isNotEmpty() }?.let { Type.valueOf(it.uppercase()) }
    )
}

enum class Type {
    PROSENT, NOK
}