
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @authors Pierre Dibo, Mathieu Doisneau
 */
public class Graphe {

    private final Sommet[] noeuds;
    private final int nb_noeuds, nb_aretes, nb_boucles;
    private int nb_doublons, min_noeud_max_degree;

    public Graphe(int n, int m, int b, int[][] g) {
        this.noeuds = new Sommet[n];
        this.nb_noeuds = n;
        this.nb_aretes = m;
        this.nb_boucles = b;
        this.nb_doublons = 0;
        this.min_noeud_max_degree = 0;
        remplirGraphe(g);
    }

    public final void remplirGraphe(int[][] g) {
        for (int i = 0; i < this.nb_noeuds; i++) {
            this.noeuds[i] = new Sommet(i, g[i][0]);
        }
    }

    public final int max(final int a, final int b) {
        if (a > b) {
            return a;
        }
        return b;
    }

    public final int min(final int a, final int b) {
        if (a > b) {
            return b;
        }
        return a;
    }

    public boolean addVoisin(int o1, int o2) {
        if (o1 == o2) {
            return false;
        }
        Sommet s1 = this.noeuds[o1];
        if (s1.addVoisin(o2)) {
            Sommet s2 = this.noeuds[o2];
            s2.addVoisin(o1);

            if (s1.getDegree() < s2.getDegree()) {
                if (this.noeuds[min_noeud_max_degree].getDegree() < s2.getDegree()) {
                    min_noeud_max_degree = o2;
                } else {
                    if (this.noeuds[min_noeud_max_degree].getDegree() == s2.getDegree()) {
                        min_noeud_max_degree = this.min(min_noeud_max_degree, s2.id);
                    }
                }
            } else {
                if (s1.getDegree() == s2.getDegree()) {
                    int id = this.min(o1, o2);
                    if (this.noeuds[min_noeud_max_degree].getDegree() < this.noeuds[id].getDegree()) {
                        min_noeud_max_degree = o2;
                    } else {
                        if (this.noeuds[min_noeud_max_degree].getDegree() == this.noeuds[id].getDegree()) {
                            min_noeud_max_degree = this.min(min_noeud_max_degree, id);
                        }
                    }
                } else {
                    if (this.noeuds[min_noeud_max_degree].getDegree() < s1.getDegree()) {
                        min_noeud_max_degree = o1;
                    } else {
                        if (this.noeuds[min_noeud_max_degree].getDegree() == s1.getDegree()) {
                            if (min_noeud_max_degree > o1) {
                                min_noeud_max_degree = o1;
                            }
                        }
                    }
                }
            }
            return true;
        }
        this.nb_doublons++;
        return false;
    }

    public void afficheDistributionDegree() {
        DistributionDegree result;

        result = new DistributionDegree();
        Stream<Sommet> stream = Arrays.stream(this.noeuds);
        result.putAll(stream.collect(Collectors.groupingBy(s -> s.getDegree(), Collectors.counting())));
        System.out.println(result.toString());
    }

    public Sommet getSommetDegreeMax() {
        Sommet s = null;
        for (int i = 0; i < this.nb_noeuds; i++) {
            if (s == null) {
                s = this.noeuds[i];
                continue;
            }
            Sommet tmp = this.noeuds[i];

            if (s.getDegree() < tmp.getDegree()) {
                s = tmp;
            } else {
                if (s.getDegree() == tmp.getDegree() && s.id < tmp.id) {
                    s = tmp;
                }
            }
        }
        return s;
    }

    public void display() {
        if (this.nb_boucles > 0) {
            System.out.println(this.nb_boucles + " boucles ont été ignorées");
        }
        if (this.nb_doublons > 0) {
            System.out.println(this.nb_doublons + " doublons ont ete supprimes");
        }
        System.out.println("Nombre de sommets : " + this.nb_noeuds);
        System.out.println("Nombre d'aretes : " + (this.nb_aretes - (this.nb_boucles + this.nb_doublons)));
        System.out.println("Sommet de degré max (de numéro minimal) : " + this.noeuds[min_noeud_max_degree]);
        System.out.println("Sa liste d'adjacence (ligne suivante) :");
        this.noeuds[min_noeud_max_degree].affichageVoisinnage();
        System.out.println("Ditribution des degrés :");
        this.afficheDistributionDegree();
    }
}
