
import java.util.Objects;

/**
 * @author Pierre Dibo
 * @author Aillerie Anthony
 */
public final class Paire implements Comparable<Paire> {

    public final Cluster a;
    public final Cluster b;
    public final Cluster m;
    public double Q;

    private static final double CARRE = 2.0;

    public Paire(Cluster c1, Cluster c2) {
        this.a = c1;
        this.b = c2;
        this.m = Cluster.merge(this.a, this.b);
        this.Q = 0.0;
    }

    public Paire(Cluster c1, Cluster c2, double q) {
        this.a = c1;
        this.b = c2;
        this.m = Cluster.merge(this.a, this.b);
        this.Q = q;
    }

    public double merge() {
        Cluster merged = Cluster.merge(this.a, this.b);
        double mab = (Partition.m(merged) - (Partition.m(this.a) + Partition.m(this.b))) / TP34.g.m;
        return (mab - (Math.pow(this.a.degrees + this.b.degrees, CARRE)
                + Math.pow(this.a.degrees, CARRE)
                + Math.pow(this.b.degrees, CARRE)) / (4 * Math.pow(TP34.g.m, CARRE)));
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
        final Paire other = (Paire) obj;
        if (!Objects.equals(this.a, other.a) && !Objects.equals(this.a, other.b)) {
            return false;
        }
        return !(!Objects.equals(this.b, other.b) && !Objects.equals(this.b, other.a));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.a);
        hash = 37 * hash + Objects.hashCode(this.b);
        return hash;
    }

    @Override
    public String toString() {
        return "(" + a + ", " + b + ", " + this.Q + ")";
    }

    @Override
    public int compareTo(Paire o) {
        return Double.compare(Q, o.Q);
    }


}
