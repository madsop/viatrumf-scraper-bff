package no.madsopheim.viatrumf.scraper.bff

import java.time.LocalDateTime

data class Nettbutikk(
    val namn: String,
    val href: String,
    val popularitet: String,
    val verdi: String,
    val timestamp: LocalDateTime,
    val kategori: String?
)