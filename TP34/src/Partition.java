
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

    public double Q() {
        double rslt = 0.0;
        double rsltM = 0.0;
        double rsltV = 0.0;
        for (Cluster c1 : c) {
            for (int i = 0; i < c1.t.length; i++) {
            	rsltM += m(c1.t, i);
            	rsltV += calcV(c1.t[i]);
            }
            rslt += ((rsltM / 2) / TP34.g.m) - (Math.pow(rsltV, 2.0) / (4 * Math.pow(TP34.g.m, 2.0)));
            rsltM = 0.0;
            rsltV = 0.0;
        }

        return rslt;
    }

    private double calcV(int i) {
        double rslt = 0.0;
        rslt += TP34.g.V[i].degre;
        return rslt;
    }

    private double m(int[] ct, int k) {
        double rslt = 0.0;
        List<Integer> adj = Arrays.asList(TP34.g.hash.get(ct[k]));
        Set<Integer> intersect = new HashSet<Integer>(adj);
        List<Integer> currentCt  = Arrays.stream(ct).boxed().collect(Collectors.toList());
        intersect.retainAll(currentCt);
        rslt = intersect.size();
        
        return rslt;
    }
}
