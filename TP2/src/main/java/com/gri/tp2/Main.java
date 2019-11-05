package com.gri.tp2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Arrays;
import java.util.Iterator;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.util.SupplierUtil;

/**
 *
 * @author dibop
 */
public class Main {

    public static final int CORRECTEUR_BET = 2;

    public static int compteur(Graphe G, String filename) {
        int compteur = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filename)))) {
            while (reader.readLine() != null) {
                compteur++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Erreur entree/sortie sur" + filename, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return compteur;
    }

    public static int matrice(Graphe g, String filename, int[][] lus) {
        int l = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filename)))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) // end of file
                {
                    break;
                }
                if (line.charAt(0) == '#') {
                    // System.out.println(line); // affichage de la ligne de commentaire
                    continue;
                }
                int a = 0;
                for (int pos = 0; pos < line.length(); pos++) {
                    char c = line.charAt(pos);
                    if (c == ' ' || c == '\t') {
                        if (a != 0) {
                            lus[l][0] = a;
                        }
                        a = 0;
                        continue;
                    }
                    if (c < '0' || c > '9') {
                        System.out.println("Erreur format ligne " + l + "c = " + c + " valeur " + (int) c);
                        System.exit(1);
                    }
                    a = 10 * a + c - '0';
                }
                lus[l][1] = a;
                if (g.n < 1 + lus[l][0]) // au passage calcul du numéro de sommet max
                {
                    g.n = 1 + lus[l][0];
                }
                if (g.n < 1 + lus[l][1]) // au passage calcul du numéro de sommet max
                {
                    g.n = 1 + lus[l][1];
                }
                l++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Erreur entree/sortie sur" + filename, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    public static void boucles(Graphe g, int[][] lus, int l) {
        int nbloop = 0;
        g.V = new Sommet[g.n];
        for (int i = 0; i < g.n; i++) {
            g.V[i] = new Sommet(i);
        }

        for (int i = 0; i < l; i++) {
            int x, y; // juste pour la lisibilité
            x = lus[i][0];
            y = lus[i][1];
            if (x == y) { // nous ignorons les boucles
                nbloop++;
                continue;
            }

            (g.V[x].degre)++; // arete x--y augmente le degre de x 
            (g.V[y].degre)++; // ...et celui de y 
        }
        if (nbloop > 0) {
            System.out.println(nbloop + " boucles ont été ignorées");
        }
    }

    public static void tableauxAdjacence(Graphe g, int[][] lus, int l) {
        for (int i = 0; i < g.n; i++) {
            if (g.V[i].degre > 0) {
                g.V[i].adj = new int[g.V[i].degre];
            }
            g.V[i].degre = 0; // on remet le degre a zero car degre pointe la première place libre où insérer un élément pour la troisième passe
        }

        for (int i = 0; i < l; i++) {
            int x, y; // juste pour la lisibilité
            x = lus[i][0];
            y = lus[i][1];
            if (x == y) {
                continue;
            }
            g.V[x].adj[g.V[x].degre++] = y;
            g.V[y].adj[g.V[y].degre++] = x;
        }
    }

    public static void deboublonage(Graphe g) {
        int nbdoubl = 0;
        g.dmax = 0;
        g.somdmax = 0;
        for (int i = 0; i < g.n; i++) {
            if (g.V[i].degre > 0) {
                Arrays.sort(g.V[i].adj); 		    // on commence par trier la liste d'adjacance.
                for (int j = g.V[i].degre - 2; j >= 0; j--) {
                    if (g.V[i].adj[j] == g.V[i].adj[j + 1]) {    // du coup les doublons deviennent consécutifs 
                        // oh oh un doublon
                        nbdoubl++;
                        // on echange le doublon avec le dernier element que l'on supprime
                        // boucle de droite a gauche pour eviter de deplacer un autre doublon
                        g.V[i].adj[j + 1] = g.V[i].adj[g.V[i].degre - 1];
                        g.V[i].degre--;
                    }
                }
            }
            // on calcule le degré max maintenant, et le nombre d'arêtes
            if (g.dmax < g.V[i].degre) {
                g.dmax = g.V[i].degre;
                g.somdmax = i; // on sait qui atteint le degré max
            }
            g.m += g.V[i].degre;
        }

        // on a compté chaque arête deux fois et chaqyue doublon aussi
        g.m /= 2;
        nbdoubl /= 2;

        if (nbdoubl > 0) {
            System.out.println(nbdoubl + " doublons ont ete supprimes");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage : time mvn exec:java -Dexec.mainClass=mvn exec:java -Dexec.mainClass=com.gri.tp2.Main -Dexec.args=\"filename [nodes i...n]\"");
            return;
        }
        String filename = args[0];

        Graphe g = new Graphe();
        int compteur = compteur(g, filename);
        int[][] lus = new int[compteur][2];
        int l = matrice(g, filename, lus);

        boucles(g, lus, l);
        tableauxAdjacence(g, lus, l);
        deboublonage(g);

        DefaultUndirectedGraph graphe = new DefaultUndirectedGraph(SupplierUtil.createIntegerSupplier(), SupplierUtil.createIntegerSupplier(), true);
        for (Sommet s : g.V) {
            graphe.addVertex(s.num);
        }

        for (Sommet s : g.V) {
            if (s.adj != null) {
                for (int i = 0; i < s.adj.length; i++) {
                    graphe.addEdge(s.num, s.adj[i]);
                }
            }
        }

        System.out.println("Nombre de sommets : " + graphe.vertexSet().size());
        System.out.println("Nombre d'aretes : " + graphe.edgeSet().size());
        BetweennessCentrality centrality = new BetweennessCentrality(graphe, true);

        Iterator<Integer> iter = graphe.vertexSet().iterator();
        if (args.length > 1) {
            for (int i = 1; i < args.length; i++) {
                int node = Integer.parseInt(args[i]);
                if (node > graphe.vertexSet().size()) {
                    System.out.println(node + " : Numéro du sommet invalide");
                } else {
                    System.out.println(node + " : " + centrality.getVertexScore(node) * CORRECTEUR_BET);
                }
            }
        } else {
            while (iter.hasNext()) {
                Integer i = iter.next();
                System.out.println(i + " : " + centrality.getVertexScore(i) * CORRECTEUR_BET);
            }
        }
        System.out.println("Mémoire allouée : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + " octets");
    }

}
