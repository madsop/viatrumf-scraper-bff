package no.madsopheim.viatrumf.scraper.bff;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

@ApplicationScoped
public class FirestoreConnector {

    @Inject
    NettbutikkRepository nettbutikkRepository;

    public boolean isReady() {
        return nettbutikkRepository.isReady();
    }

    public List<String> finnAlleNettbutikkar() {
        return stream(nettbutikkRepository.listDocuments().spliterator(), true)
                .map(DocumentReference::getPath)
                .map(nettbutikkRepository::removeCollectionName)
                .collect(Collectors.toList());
    }

    public List<Nettbutikk> query(String nettbutikknamn) {
        return stream(nettbutikkRepository.document(nettbutikknamn).listCollections().spliterator(), false)
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
