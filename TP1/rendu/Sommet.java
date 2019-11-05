
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

    public boolean addVoisin(int o) {
        for (int i = 0; i < this.degree; i++) {
            if (this.voisinnage[i] == o) {
                return false;
            }
        }
        this.voisinnage[this.degree++] = o;
        return true;
    }

    public int getDegree() {
        return degree;
    }

    public void affichageVoisinnage() {
        for (int i = 0; i < this.degree; i++) {
            System.out.print(this.voisinnage[i] + " ");
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "" + id;
    }

}
