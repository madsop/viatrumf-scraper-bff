package no.madsopheim.viatrumf.scraper.bff;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.regex.Pattern;

@ApplicationScoped
public class NettbutikkRepository {
    private CollectionReference collection;

    @Inject
    @ConfigProperty(name = "collectionName")
    String collectionName;

    @Inject
    Firestore firestore;

    private Pattern pattern;

    @PostConstruct
    public void setup() {
        collection = firestore.collection(collectionName);
        pattern = Pattern.compile(collectionName + "/");
    }

    public Iterable<DocumentReference> listDocuments() {
        return collection.listDocuments();
    }

    public DocumentReference document(String nettbutikknamn) {
        return collection.document(nettbutikknamn);
    }

    public boolean isReady() {
        return collection != null;
    }

    public String removeCollectionName(String path) {
        return pattern.matcher(path).replaceFirst("");
    }
}
