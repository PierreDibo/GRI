
import java.util.Map;

/**
 *
 * @author dibop
 */
public class Partition {

    public Cluster[] c;

    public double Q() {
        double rslt = 0.0;
        for (Cluster c1 : c) {
            rslt += ((m(c1) / 2) / TP34.g.m) - (Math.pow(calcV(c1), 2.0) / (4 * Math.pow(TP34.g.m, 2.0)));
        }

        return rslt;
    }

    private double calcV(Cluster c) {
        double rslt = 0.0;
        for (int i = 0; i < c.t.length; i++) {
            rslt += TP34.g.V[c.t[i]].degre;
        }
        return rslt;
    }

    private double m(Cluster c) {
        double rslt = 0.0;
        for (int k : c.t) {
            Integer[] adj = TP34.g.hash.get(k);
            for (Integer adj1 : adj) {
                if (isIn(c.t, adj1)) {
                    rslt++;
                }
            }
        }
        return rslt;
    }

    private boolean isIn(int[] c, int i) {
        if (c == null) {
            return false;
        }

        for (int k : c) {
            if (k == i) {
                return true;
            }
        }
        return false;
    }
}
