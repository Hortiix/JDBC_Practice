package be.vdab.bieren;

import java.util.Objects;

public class Brouwers {
    private final long id;
    private final String naam,adres,gemeente;
    private final int postcode;
    private final int omzet;

    public Brouwers(long id, String naam,String adres, int postcode, String gemeente, int omzet) {
        this.id = id;
        this.naam = naam;
        this.adres = adres;
        this.postcode = postcode;
        this.gemeente = gemeente;
        this.omzet = omzet;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("naam='").append(naam).append('{').append('\'');
        sb.append("id=").append(id);
        sb.append(", adres=").append(adres);
        sb.append(", postcode=").append(postcode);
        sb.append(", gemeente='").append(gemeente).append('\'');
        sb.append(", omzet=").append(omzet);
        sb.append('}');
        return sb.toString();
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public int getPostcode() {
        return postcode;
    }

    public String getGemeente() {
        return gemeente;
    }

    public int getOmzet() {
        return omzet;
    }

    public String getAdres() {
        return adres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brouwers brouwers = (Brouwers) o;
        return id == brouwers.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
