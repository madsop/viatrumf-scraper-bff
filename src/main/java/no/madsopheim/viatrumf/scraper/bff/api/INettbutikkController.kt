package no.madsopheim.viatrumf.scraper.bff.api

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import no.madsopheim.viatrumf.scraper.bff.Nettbutikk

interface INettbutikkController {
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    @GET
    fun alleNettbutikkar(): List<String>

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(("{nettbutikk}"))
    fun hentInfoForNettbutikk(@PathParam("nettbutikk") nettbutikk: String): List<Nettbutikk>
}
