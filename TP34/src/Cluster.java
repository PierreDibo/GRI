
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author dibop
 */
public class Cluster {

    public final List<Integer> t;
    public double modu;
    
    public Cluster(List<Integer> c) {
        this.t = c;
    }

    public static Cluster merge(Cluster c1, Cluster c2) {
        HashSet<Integer> c = new HashSet<>();
        c.addAll(c1.t);
        c.addAll(c2.t);
        
        return new Cluster(new ArrayList<>(c));
    }
}
