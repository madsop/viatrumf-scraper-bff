package no.madsopheim.viatrumf.scraper.bff;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.*;

@ApplicationScoped
public class FirestoreConnector {

    private Firestore db;
    private CollectionReference collection;

    @Inject
    @ConfigProperty(name = "collectionName")
    String collectionName;

    @PostConstruct
    public void setup() throws ExecutionException, InterruptedException {
        String projectId = "viatrumf-scraper-271913";
        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setProjectId(projectId)
                        .build();
        db = firestoreOptions.getService();
        collection = db.collection(collectionName);
    }

    public List<String> finnAlleNettbutikkar() {
        return stream(collection.listDocuments().spliterator(), false)
                .map(DocumentReference::getPath)
                .map(path -> path.replace(collectionName + "/", ""))
                .collect(Collectors.toList());
    }

    public List<Nettbutikk> query(String nettbutikknamn) throws ExecutionException, InterruptedException {
        return stream(collection.document(nettbutikknamn).listCollections().spliterator(), false)
                .map(Query::get)
                .map(this::wrappingGet)
                .map(QuerySnapshot::getDocuments)
                .flatMap(Collection::stream)
                .map(QueryDocumentSnapshot::getData)
                .map(this::joinData)
                .collect(Collectors.toList());
    }

    QuerySnapshot wrappingGet(ApiFuture<QuerySnapshot> future) {
        try {
            return future.get();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Nettbutikk joinData(Map<String, Object> stringObjectMap) {
        return new Nettbutikk(
                (String) stringObjectMap.get("namn"),
                (String) stringObjectMap.get("href"),
                (String) stringObjectMap.get("popularitet"),
                (String) stringObjectMap.get("verdi"),
                (String) stringObjectMap.get("timestamp"),
                (String) stringObjectMap.get("kategori")
        );
    }
}
