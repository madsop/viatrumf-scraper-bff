package no.madsopheim.viatrumf.scraper.bff.api;

import no.madsopheim.viatrumf.scraper.bff.Nettbutikk;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
