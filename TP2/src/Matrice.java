
import java.util.Arrays;

/**
 *
 * @author Pierre Dibo
 */
public final class Matrice {

    public int[][] t;

    public Matrice(int n, int m) {
        this.t = new int[n][m];
        this.fill();
    }

    public void fill() {
        for (int[] i : t) {
            Arrays.fill(i, Integer.MAX_VALUE);
        }

        for (int i = 0; i < t.length; i++) {
            Arrays.fill(t[i], Integer.MAX_VALUE);
            for (int j = 0; j < t[i].length; j++) {
                if (i == j) {
                    t[i][j] = 0;
                    break;
                }
            }
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (int[] i : t) {
            s += Arrays.toString(i) + "\n";
        }

        return s;
    }

}
