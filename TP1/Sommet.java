
/**
 *
 * @authors Pierre Dibo, Mathieu Doisneau
 */
public class Sommet {

    public final int id;
    private int degree;
    private final int[] voisinnage;

    public Sommet(int i, int n) {
        this.id = i;
        this.degree = 0;
        this.voisinnage = new int[n];
    }

    public boolean search(int item) {
        int n = this.degree;

        if (this.voisinnage[n] == item) {
            return true;
        }
        int backup = this.voisinnage[n];
        this.voisinnage[n] = item;

        for (int i = 0;; i++) {
            if (this.voisinnage[i] == item) {
                this.voisinnage[n] = backup;
                return i < n;
            }
        }
    }

    public boolean addVoisin(int o) {
        if (this.degree > 0) {
            if (!this.search(o)) {
                this.voisinnage[this.degree++] = o;
                return true;
            }
            return false;
        }
        this.voisinnage[this.degree++] = o;
        return true;

    }

    public int getDegree() {
        return degree;
    }

    public void affichageVoisinnage() {
        for (int i = 0; i < this.degree; i++) {
            if (this.voisinnage[i] != -1) {
                System.out.print(this.voisinnage[i] + " ");
            }
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "" + id;
    }

}
