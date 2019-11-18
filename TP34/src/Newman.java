
import java.util.Arrays;

/**
 *
 * @author dibop
 */
public class Newman {

    public static void modularite(Graphe g, Partition p) {
        double rslt = 0.0;
        //Arrays.stream(c.t).map(p -> Double.sum(rslt, new Partition(p).Q()));
        /*for (int[] t : c.t) {
            double p = new Partition(t).Q();
            //System.out.println(p);
            rslt += p;
        }*/
        System.out.println(p.Q());
    }

    public static double calc(Graphe g, int[] t) {
        double rslt = 0.0;
        for (int i = 0; i < t.length; i = i + 2) {
            if (i + 1 < t.length) {
                rslt += g.distance[t[i]][t[i + 1]] - (g.V[t[i]].degre * g.V[t[i + 1]].degre / (2 * g.m));
            } else {
                rslt += g.distance[t[i]][t[i]] - (g.V[t[i]].degre * g.V[t[i]].degre / (2 * g.m));
            }
        }
        /*for (int i = 0; i < t.length; i++) {
            rslt += Math.pow(g.V[t[i]].degre, 2.0);
        }*/
        return rslt; // (4 * Math.pow(g.m, 2.0));
    }

}
