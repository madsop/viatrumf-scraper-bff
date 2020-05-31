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
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

@ApplicationScoped
public class FirestoreConnector {

    private CollectionReference collection;

    @Inject
    @ConfigProperty(name = "collectionName")
    String collectionName;

    @Inject
    @ConfigProperty(name = "projectId")
    String projectId;

    @PostConstruct
    public void setup() {
        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance()
                    .toBuilder()
                    .setProjectId(projectId)
                    .build();
        collection = firestoreOptions.getService().collection(collectionName);
    }

    public boolean isReady() {
        return collection != null;
    }

    public List<String> finnAlleNettbutikkar() {
        return stream(collection.listDocuments().spliterator(), false)
                .map(DocumentReference::getPath)
                .map(path -> path.replace(collectionName + "/", ""))
                .collect(Collectors.toList());
    }

    public List<Nettbutikk> query(String nettbutikknamn) {
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
