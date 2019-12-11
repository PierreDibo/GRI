

/**
 * @author Pierre Dibo
 * @author Aillerie Anthony
 */
public final class Paire implements Comparable<Paire> {

    public final Cluster a;
    public final Cluster b;
    public final Cluster m;
    public double Q;

    //private static final double CARRE = 2.0;

    public Paire(Cluster c1, Cluster c2) {
        this.a = c1;
        this.b = c2;
        this.m = Cluster.merge(this.a, this.b);
        //this.Q = merge();
    }

    /*private double merge() {
        double mab = (Partition.m(this.m)) / TP34.g.m;
        return mab - ((Math.pow(this.a.degrees + this.b.degrees, CARRE)
                + Math.pow(this.a.degrees, CARRE)
                + Math.pow(this.b.degrees, CARRE)) / (4 * Math.pow(TP34.g.m, CARRE)));
    }*/

    @Override
    public String toString() {
        return "(" + a + ", " + b + ", " + this.Q + ")";
    }

    @Override
    public int compareTo(Paire o) {
        return Double.compare(Q, o.Q);
    }
}
