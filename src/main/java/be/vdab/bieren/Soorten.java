package be.vdab.bieren;

public class Soorten {
    private final long id;
    private final String naam;

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Soorten(long id, String naam) {
        this.id = id;
        this.naam = naam;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Soorten{");
        sb.append("id=").append(id);
        sb.append(", naam='").append(naam).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
