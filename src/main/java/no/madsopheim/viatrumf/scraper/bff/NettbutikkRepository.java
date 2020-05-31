package no.madsopheim.viatrumf.scraper.bff;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FirestoreOptions;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.opentracing.Traced;

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
    @ConfigProperty(name = "projectId")
    String projectId;

    private Pattern pattern;

    @Traced
    @PostConstruct
    public void setup() {
        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance()
                .toBuilder()
                .setProjectId(projectId)
                .build();
        collection = firestoreOptions.getService().collection(collectionName);
        pattern = Pattern.compile(collectionName + "/");
    }

    @Traced
    public Iterable<DocumentReference> listDocuments() {
        return collection.listDocuments();
    }

    @Traced
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
