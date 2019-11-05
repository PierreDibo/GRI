
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @authors Pierre Dibo, Mathieu Doisneau
 */
public class TP1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[][] graphe;
        Graphe g;
        String file_name;
        int nb_noeuds = 0, nb_aretes = 0, nb_boucles = 0, min_noeud = 0;

        if (args.length == 0) {
            System.err.println("Usage : java TP1 nomchier.txt");
            return;
        }

        file_name = args[0];
        try {
            try ( BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
                for (String line; (line = reader.readLine()) != null;) {
                    if (line.charAt(0) == '#') {
                        continue;
                    }
                    String[] s = line.split("\\s+");
                    nb_noeuds = Math.max(nb_noeuds, Math.max(Integer.parseInt(s[0]), Integer.parseInt(s[1])));
                    nb_aretes++;
                }
            } catch (IOException ex) {
                System.err.println("Erreur entree/sortie sur " + file_name);
                return;
            }

            graphe = new int[++nb_noeuds][1];
            for (int i = 0; i < nb_noeuds; i++) {
                graphe[i][0] = 0;
            }

            try ( BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
                for (String line; (line = reader.readLine()) != null;) {
                    if (line.charAt(0) == '#') {
                        continue;
                    }
                    String[] s = line.split("\\s+");
                    int x = Integer.parseInt(s[0]);
                    int y = Integer.parseInt(s[1]);

                    if (x == y) {
                        nb_boucles++;
                        continue;
                    }
                    graphe[x][0]++;
                    graphe[y][0]++;
                }
            } catch (IOException ex) {
                Logger.getLogger(TP1.class.getName()).log(Level.SEVERE, null, ex);
            }

            g = new Graphe(nb_noeuds, nb_aretes, nb_boucles, graphe);

            try ( BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
                for (String line; (line = reader.readLine()) != null;) {
                    if (line.charAt(0) == '#') {
                        continue;
                    }
                    String[] s = line.split("\\s+");
                    g.addVoisin(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
                }
            } catch (IOException ex) {
                Logger.getLogger(TP1.class.getName()).log(Level.SEVERE, null, ex);
            }

            g.display();
            System.out.println("Mémoire allouée : "
                    + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
                    + " octets\n");
        } catch (NumberFormatException ex) {
            System.err.println("Erreur format ligne " + ex.getStackTrace()[0].getLineNumber());
        }
    }

}
