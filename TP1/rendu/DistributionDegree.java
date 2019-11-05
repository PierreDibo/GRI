
import java.util.TreeMap;

/**
 *
 * @authors Pierre Dibo, Mathieu Doisneau
 */
public class DistributionDegree extends TreeMap<Integer, Long> {

    //private static final long serialVersionUID = -1884466897405388867L;

    @Override
    public String toString() {
        String s = "";

        for (int i = 0; i <= this.lastKey(); i++) {
            if (this.containsKey(i)) {
                s += i + " " + this.get(i) + "\n";
            } else {
                s += i + " " + 0 + "\n";
            }

        }
        return s;
    }
}
