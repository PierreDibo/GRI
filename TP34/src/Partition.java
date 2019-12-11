
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
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

    public List<Cluster> c = new ArrayList<>();
    private static final double CARRE = 2.0;

    public static double modularite(Cluster c, int i) {
        c.index = i;
        return c.modu = ((c.m = (m(c) / CARRE) / TP34.g.m)) - (Math.pow(c.degrees, CARRE) / ((CARRE + CARRE) * Math.pow(TP34.g.m, CARRE)));
    }

    public static double modularite(Cluster c) {
        return c.modu = ((c.m = (m(c) / CARRE) / TP34.g.m)) - (Math.pow(c.degrees, CARRE) / ((CARRE + CARRE) * Math.pow(TP34.g.m, CARRE)));
    }

    public double Q() {
        double rslt = 0.0;
        for (int i = 0; i < c.size(); i++) {
            rslt += modularite(c.get(i), i);
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
        PriorityQueue<Paire> queue = new PriorityQueue<>((Paire o1, Paire o2) -> Double.compare(o2.Q, o1.Q));
        double part = Q();
        for (int i = 0; i < c.size(); i++) {
            for (int j = i + 1; j < c.size(); j++) {
                Paire p = new Paire(c.get(i), c.get(j));
                Paire inQueu;

                inQueu = queue.peek();

                if (inQueu == null) {
                    queue.add(p);
                } else {
                    if (p.Q > inQueu.Q) {
                        queue.poll();
                        queue.add(p);
                    }
                }
            }
        }

        printIncrementPaire(queue.peek(), part);
    }

    public void printIncrementPaire(Paire p, double part) {
        printList(p.a.t);
        printList(p.b.t);
        Cluster[] ct = Stream.concat(c.stream().filter(cl -> !cl.equals(p.a) && !cl.equals(p.b)),
                Stream.of(p.m))
                .toArray(Cluster[]::new);
        System.out.println("incrément de modularité " + (Q(ct) - part));
    }

    private void printList(List<Integer> l) {
        l.stream().forEach(i -> System.out.print(i + " "));
        System.out.println();
    }

    public void louvain(String f) {
        double part = Q();
        PriorityQueue<Paire> queue = new PriorityQueue<>((Paire o1, Paire o2) -> Double.compare(o2.Q, o1.Q));
        /*List<Cluster> clusters = Arrays.asList(c);

        Iterator<Cluster> iter = clusters.iterator();
        while (iter.hasNext()) {
            Paire p = meilleurM(clusters, iter.next());
            queue.add(p);
        }*/
        int size = c.size();
        for (int i = 0; i < size; i++) {
            Paire p, inQueu;

            p = transition();
            inQueu = queue.peek();
            c.remove(p.b);
            c.add(p.m);
            
            if (inQueu == null) {
                queue.add(p);
            } else {
                if (p.Q > inQueu.Q) {
                    queue.poll();
                    queue.add(p);
                }
            }
        }

        //System.out.println(queue);
        printIncrementPaire(queue.peek(), part);
        //System.out.println("Meilleure modularité : " + Q(c));
    }

    public Paire transition() {
        PriorityQueue<Paire> queue = new PriorityQueue<>((Paire o1, Paire o2) -> Double.compare(o2.Q, o1.Q));
        Iterator<Cluster> iter = c.iterator();
        while (iter.hasNext()) {
            Paire p, inQueu;

            p = meilleurM(c, iter.next());
            inQueu = queue.peek();

            if (inQueu == null) {
                queue.add(p);
            } else {
                if (p.Q > inQueu.Q) {
                    queue.poll();
                    queue.add(p);
                }
            }
        }
        return queue.poll();
    }

    public Paire meilleurM(List<Cluster> clusters, Cluster cluster) {
        PriorityQueue<Paire> queue = new PriorityQueue<>((Paire o1, Paire o2) -> Double.compare(o2.Q, o1.Q));
        for (int i = 0; i < clusters.size(); i++) {
            Paire p, inQueu;

            p = new Paire(cluster, clusters.get(i));
            inQueu = queue.peek();

            if (inQueu == null) {
                queue.add(p);
            } else {
                if (p.Q > inQueu.Q) {
                    queue.poll();
                    queue.add(p);
                }
            }
        }
        return queue.poll();
    }
}
