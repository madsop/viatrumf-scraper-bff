package no.madsopheim.viatrumf.scraper.bff;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@XmlRootElement
public class Nettbutikk {

    private String namn;
    private String href;
    private String popularitet;
    private String verdi;
    private LocalDateTime timestamp;
    private String kategori;

    private static final DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

    public Nettbutikk() {
    }

    public Nettbutikk(String namn, String href, String popularitet, String verdi, String timestamp, String kategori) {
        this.namn = namn;
        this.href = href;
        this.popularitet = popularitet;
        this.verdi = verdi;
        this.timestamp = Optional.ofNullable(timestamp).map(t -> LocalDateTime.parse(t, datePattern)).orElse(null);
        this.kategori = kategori;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getPopularitet() {
        return popularitet;
    }

    public void setPopularitet(String popularitet) {
        this.popularitet = popularitet;
    }

    public String getVerdi() {
        return verdi;
    }

    public void setVerdi(String verdi) {
        this.verdi = verdi;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
