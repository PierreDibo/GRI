
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Pierre Dibo
 * @author Aillerie Anthony
 */
public class Partition {

    public Cluster[] c;
    private static final double CARRE = 2.0;

    private double modularite(Cluster c) {
        return c.modu = ((m(c) / CARRE) / TP34.g.m) - (Math.pow(c.degrees, CARRE) / ((CARRE + CARRE) * Math.pow(TP34.g.m, CARRE)));
    }

    public double Q() {
        double rslt = 0.0;
        for (Cluster c1 : c) {
            rslt += modularite(c1);
        }
        return rslt;
    }

    public double Q(List<Cluster> cm) {
        return cm.stream().map(c1 -> modularite(c1)).reduce(0.0, Double::sum);
    }

    public double Q(Cluster[] cm) {
        return Arrays.stream(cm).map(c1 -> modularite(c1)).reduce(0.0, Double::sum);
    }

    public static double m(Cluster c) {
        Set<Integer> ct = new HashSet<>(c.t);
        final ArrayList<Integer> rslt = new ArrayList<>();
        c.t.stream().forEach(i -> {
            Set<Integer> set = new HashSet<>(Arrays.stream(TP34.g.V[i].adj).boxed().collect(Collectors.toList()));
            set.retainAll(ct);
            rslt.add(set.size());
        });
        return rslt.stream().reduce(0, Integer::sum);
    }

    public static double m(Paire p) {
        Set<Integer> ct = new HashSet<>(p.a.t);
        final ArrayList<Integer> rslt = new ArrayList<>();
        p.a.t.stream().forEach(i -> {
            Set<Integer> set = new HashSet<>(Arrays.stream(TP34.g.V[i].adj).boxed().collect(Collectors.toList()));
            set.retainAll(ct);
            rslt.add(set.size());
        });
        return rslt.stream().reduce(0, Integer::sum);
    }

    public void paire() {
        PriorityQueue<Paire> queue = new PriorityQueue<>(new Comparator<Paire>() {
            @Override
            public int compare(Paire o1, Paire o2) {
                return Double.compare(o2.Q, o1.Q);
            }
        });

        double part = Q();

        for (int i = 0; i < c.length; i++) {
            for (int j = i + 1; j < c.length; j++) {
                //List<Cluster> l = Arrays.stream(c).collect(Collectors.toList());
                Paire p = new Paire(c[i], c[j]);
                /*l.remove(p.a);
                l.remove(p.b);
                l.add(p.m);*/
                if (!queue.contains(p)) {
                    Cluster[] ct = Stream.concat(Arrays.stream(c).filter(cl -> !cl.equals(p.a) && !cl.equals(p.b)),
                            Stream.of(p.m))
                            .toArray(Cluster[]::new);
                    p.Q = Q(ct) - part;
                    queue.add(p);
                }
            }
        }

        printIncrementPaire(queue.peek());
    }

    public void printIncrementPaire(Paire p) {
        printList(p.a.t);
        printList(p.b.t);
        System.out.println("incrément de modularité " + p.Q);
    }

    private void printList(List<Integer> l) {
        l.stream().forEach(i -> System.out.print(i + " "));
        System.out.println();
    }
}
