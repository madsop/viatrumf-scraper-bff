package no.madsopheim.viatrumf.scraper.bff

import jakarta.inject.Singleton
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType

@Path("/nettbutikkar")
@Singleton
class NettbutikkController(val firestoreConnector: FirestoreConnector) {

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    fun alleNettbutikkar(@QueryParam("collectionName") collectionName: CollectionName? = CollectionName.TRUMF_NETTHANDEL): List<String> = firestoreConnector.finnAlleNettbutikkar(collectionName ?: CollectionName.TRUMF_NETTHANDEL)

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{nettbutikk}")
    fun hentInfoForNettbutikk(@PathParam("nettbutikk") nettbutikk: String, @QueryParam("collectionName") collectionName: CollectionName? = CollectionName.TRUMF_NETTHANDEL): List<Nettbutikk> = firestoreConnector.query(
        collectionName ?: CollectionName.TRUMF_NETTHANDEL, nettbutikk)
}
