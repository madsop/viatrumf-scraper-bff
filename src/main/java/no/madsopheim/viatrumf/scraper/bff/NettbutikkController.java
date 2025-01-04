package no.madsopheim.viatrumf.scraper.bff;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import no.madsopheim.viatrumf.scraper.bff.api.INettbutikkController;

import java.util.List;

@Path("/nettbutikkar")
@Singleton
public class NettbutikkController implements INettbutikkController {

    @Inject
    FirestoreConnector firestoreConnector;

    @Override
    public List<String> getAlleNettbutikkar() {
        return firestoreConnector.finnAlleNettbutikkar();
    }

    @Override
    public List<Nettbutikk> hentInfoForNettbutikk(@PathParam("nettbutikk") String nettbutikk) {
        return firestoreConnector.query(nettbutikk);
    }
}
