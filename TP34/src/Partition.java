
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author dibop
 */
public class Partition {

    public Cluster[] c;

    private double modularite(Cluster c) {
        double rsltM = 0.0;
        double rsltV = 0.0;

        for (int i = 0; i < c.t.size(); i++) {
            rsltM += m(c.t, i);
            rsltV += calcV(TP34.g.V[i].degre);
        }
        return c.modu = ((rsltM / 2) / TP34.g.m) - (Math.pow(rsltV, 2.0) / (4 * Math.pow(TP34.g.m, 2.0)));
    }

    public double Q() {
        double rslt = 0.0;
        for (Cluster c1 : c) {
            rslt += modularite(c1);
        }
        return rslt;
    }

    private double calcV(int i) {
        double rslt = 0.0;
        rslt += TP34.g.V[i].degre;
        return rslt;
    }

    private double m(List<Integer> ct, int k) {
        Set<Integer> intersect = new HashSet<>(TP34.g.hash.get(ct.get(k)));
        intersect.retainAll(ct);

        return intersect.size();
    }

    public void paire() {
        Cluster m = null;
        Cluster first = null;
        Cluster second = null;
        double rslt = 0.0;
        for (int i = 0; i < c.length; i++) {
            for (int j = i + 1; j < c.length; j++) {
                Cluster merged = Cluster.merge(c[i], c[j]);
                if (m == null) {
                    m = merged;
                    first = c[i];
                    second = c[j];
                } else {
                    if ((rslt = modularite(m)) < modularite(merged)) {
                        m = merged;
                        first = c[i];
                        second = c[j];
                    }
                }
            }
        }
        if (m == null || first == null || second == null) {
            System.err.println("Should not be reached");
            return;
        }
        System.out.println(first.t);
        System.out.println(second.t);
        System.out.println(m.modu);
    }
}
