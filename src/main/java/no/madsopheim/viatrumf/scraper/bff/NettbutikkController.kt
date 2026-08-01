package no.madsopheim.viatrumf.scraper.bff

import jakarta.inject.Singleton
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/nettbutikkar")
@Singleton
class NettbutikkController(val firestoreConnector: FirestoreConnector) {

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    @GET
    fun alleNettbutikkar(): List<String> = firestoreConnector.finnAlleNettbutikkar()

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{nettbutikk}")
    fun hentInfoForNettbutikk(@PathParam("nettbutikk") nettbutikk: String): List<Nettbutikk> = firestoreConnector.query(nettbutikk)
}
