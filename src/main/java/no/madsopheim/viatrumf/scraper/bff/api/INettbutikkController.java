package no.madsopheim.viatrumf.scraper.bff.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import no.madsopheim.viatrumf.scraper.bff.Nettbutikk;

import java.util.List;

public interface INettbutikkController {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    List<String> getAlleNettbutikkar();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(("{nettbutikk}"))
    List<Nettbutikk> hentInfoForNettbutikk(@PathParam("nettbutikk") String nettbutikk);
}
