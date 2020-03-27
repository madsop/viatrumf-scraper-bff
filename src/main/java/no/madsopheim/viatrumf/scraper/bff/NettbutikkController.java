package no.madsopheim.viatrumf.scraper.bff;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import io.quarkus.deployment.annotations.Produce;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Path("/nettbutikkar")
@Singleton
public class NettbutikkController {

    @Inject
    FirestoreConnector firestoreConnector;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAlleNettbutikkar() {
        return firestoreConnector.finnAlleNettbutikkar();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(("{nettbutikk}"))
    public List<Nettbutikk> hentInfoForNettbutikk(@PathParam("nettbutikk") String nettbutikk) throws ExecutionException, InterruptedException {
        return firestoreConnector.query(nettbutikk);
    }
}
