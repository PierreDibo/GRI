
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * @author Pierre Dibo
 * @author Aillerie Anthony
 */
public class Partition {

    public List<Cluster> c = new ArrayList<>();
    private static final double CARRE = 2.0;
    public double modu;

    class PartitionModularite {

        public ArrayList<Cluster> clusters = new ArrayList<>();
        public double modularite;

        public PartitionModularite(List<Cluster> cls, double mod) {
            clusters.addAll(cls);
            modularite = mod;
        }

        public String toString2() {
            String s = "[";
            s = clusters.stream().map((i) -> i.toString2() + ", ").reduce(s, String::concat);
            s = s.substring(0, s.length() - 2).concat("]");
            return s;
        }

        @Override
        public String toString() {
            return clusters.toString();
        }

    }

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
        return modu = rslt;
    }

    public double Q(List<Cluster> cm) {
        return cm.stream().map(c1 -> modularite(c1)).reduce(0.0, Double::sum);
    }

    public static double m(Cluster cluster) {
        Set<Integer> ct = new HashSet<>(cluster.t);
        final ArrayList<Integer> rslt = new ArrayList<>();
        cluster.t.stream().forEach(i -> {
            Set<Integer> set = new HashSet<>(Arrays.stream(TP34.g.V[i].adj).boxed().collect(Collectors.toList()));
            set.retainAll(ct);
            rslt.add(set.size());
        });
        return rslt.stream().reduce(0, Integer::sum);
    }

    public void paire() {
        double part = Q();
        ArrayList<Cluster> cl = new ArrayList<>(c);
        Paire p = transition(part);
        cl.remove(p.a);
        cl.remove(p.b);
        cl.add(p.m);
        PartitionModularite pm = new PartitionModularite(cl, p.Q);
        if (pm.modularite > part) {
            System.out.println("incrément de modularité :" + (pm.modularite - part));
            //System.out.println(pm.toString2());
        } else {
            System.out.println("Aucune fusion avec une meilleur modularité");
            //System.out.println(c);
            //System.out.println(part);
        }
    }

    public void louvain(String filename) {
        PriorityQueue<PartitionModularite> queue = new PriorityQueue<>((PartitionModularite o1, PartitionModularite o2) -> Double.compare(o2.modularite, o1.modularite));
        int size = c.size();
        for (int i = 0; i < size; i++) {
            Paire p;
            double part;

            part = Q();
            p = transition(part);

            if (p == null) {
                break;
            }

            c.remove(p.a);
            c.remove(p.b);
            c.add(p.m);

            if (p.Q > part) {
                queue.add(new PartitionModularite(c, Q(c)));
            }
        }
        PartitionModularite pm = queue.peek();
        write(filename, pm.clusters);
        System.out.println("Meilleure modularité : " + pm.modularite);
    }

    public boolean write(String path, List<Cluster> cls) {
        try {
            File file = Paths.get(path).toFile();
            if (!file.exists()) {
                file.createNewFile();
            }
            try ( BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
                for (Cluster cl : cls) {
                    out.write(cl.toString());
                    out.newLine();
                    out.flush();
                }
            }
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Partition.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /*private double merge(Paire p) {
        double mab = (p.a.m + p.b.m) / TP34.g.m;
        return mab - ((Math.pow(p.a.degrees + p.b.degrees, CARRE)
                + Math.pow(p.a.degrees, CARRE)
                + Math.pow(p.b.degrees, CARRE)) / (4 * Math.pow(TP34.g.m, CARRE)));
    }*/

    public void printQueue(PriorityQueue<Paire> queue) {
        Iterator<Paire> iter = queue.iterator();
        while (iter.hasNext()) {
            Paire p = iter.next();
            System.out.println(p.m);
            System.out.println(p.Q + "\n\n");
        }
    }

    public Paire transition(double q) {
        PriorityQueue<Paire> queue = new PriorityQueue<>((Paire o1, Paire o2) -> Double.compare(o1.Q, o2.Q));
        for (int i = 0; i < c.size(); i++) {
            for (int j = i + 1; j < c.size(); j++) {
                ArrayList<Cluster> cl = new ArrayList<>(c);
                Paire p = new Paire(c.get(i), c.get(j));
                Paire inQueu;

                cl.remove(p.a);
                cl.remove(p.b);
                cl.add(p.m);

                inQueu = queue.peek();

                if (inQueu == null) {
                    //p.Q = merge(p);
                    p.Q = Q(cl);
                    queue.offer(p);
                } else {
                    //p.Q = merge(p);
                    p.Q = Q(cl);
                    if (p.Q > inQueu.Q) {
                        queue.poll();
                        queue.offer(p);
                    }
                }
            }
        }
        Iterator<Paire> iter = queue.iterator();
        while (iter.hasNext() && queue.size() != 1) {
            iter.next();
            iter.remove();

        }
        return queue.poll();
    }

}
