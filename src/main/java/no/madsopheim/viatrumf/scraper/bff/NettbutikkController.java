package no.madsopheim.viatrumf.scraper.bff;

import no.madsopheim.viatrumf.scraper.bff.api.INettbutikkController;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
