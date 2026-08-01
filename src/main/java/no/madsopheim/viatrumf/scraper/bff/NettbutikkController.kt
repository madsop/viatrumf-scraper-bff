package no.madsopheim.viatrumf.scraper.bff

import jakarta.inject.Singleton
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import no.madsopheim.viatrumf.scraper.bff.api.INettbutikkController

@Path("/nettbutikkar")
@Singleton
class NettbutikkController(val firestoreConnector: FirestoreConnector) : INettbutikkController {

    override fun hentInfoForNettbutikk(@PathParam("nettbutikk") nettbutikk: String): List<Nettbutikk> =
        firestoreConnector.query(nettbutikk)

    override fun alleNettbutikkar(): List<String> = firestoreConnector.finnAlleNettbutikkar()
}
