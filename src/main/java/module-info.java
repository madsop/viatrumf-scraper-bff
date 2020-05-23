module viatrumf.scraper.bff {
    exports no.madsopheim.viatrumf.scraper.bff.api;

    requires java.compiler;
    requires java.ws.rs;
    requires java.xml.bind;
    requires com.google.api.apicommon;
    requires google.cloud.firestore;
    requires google.cloud.core;
    requires jakarta.enterprise.cdi.api;
    requires jakarta.inject.api;
    requires microprofile.config.api;
    requires java.annotation;
}