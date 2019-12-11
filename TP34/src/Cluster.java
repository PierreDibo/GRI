
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * @author Pierre Dibo
 * @author Aillerie Anthony
 */
public final class Cluster {

    public final List<Integer> t;
    public double modu, degrees, m;
    public int index;
    public final HashMap<Integer, Cluster> voisins = new HashMap<>();

    public Cluster(int s) {
        (this.t = new ArrayList<>()).add(s);
        this.degrees = somDeg(this.t);
    }

    public Cluster(List<Integer> c) {
        this.t = c;
        this.degrees = somDeg(this.t);
    }

    public boolean exists(Cluster p2) {
        return this.voisins.keySet().stream().anyMatch(i -> p2.voisins.containsKey(i));
    }

    public int somDeg(List<Integer> l) {
        return l.stream()
                .map(i -> TP34.g.V[i].degre)
                .reduce(0, Integer::sum);
    }

    public static Cluster merge(Cluster c1, Cluster c2) {
        HashSet<Integer> c = new HashSet<>();
        c.addAll(c1.t);
        c.addAll(c2.t);

        return new Cluster(new ArrayList<>(c));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.t);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cluster other = (Cluster) obj;
        return Objects.equals(this.t, other.t);
    }

    @Override
    public String toString() {
        return t.toString();
    }

}
