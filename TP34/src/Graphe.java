
import java.util.HashMap;


/**
 *
 * @author dibop
 */
public class Graphe {

    public static final int INF = 99999;
    public int n, m, dmax, somdmax;
    public Sommet[] V;
    int[][] distance;
    public HashMap<Integer, Integer[]> hash;

    public void makeGraphe(int[][] graphe) {
        for (int i = 0; i < graphe.length; i++) {

            for (int j = 0; j < graphe[i].length; j++) {
                if (i == j) {
                    graphe[i][j] = 0;
                } else {
                    graphe[i][j] = INF;
                }
            }

            if (V[i].adj != null) {
                for (int k = 0; k < V[i].adj.length; k++) {
                    graphe[i][V[i].adj[k]] = 1;
                }
            }
        }
    }
    
    public int[][] floydWarshall(int[][] m) {
        distance = new int[n][n];
        int i, j, k;

        /* Initialize the solution matrix same as input graph matrix. 
           Or we can say the initial values of shortest distances 
           are based on shortest paths considering no intermediate 
           vertex. */
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                distance[i][j] = m[i][j];
            }
        }

        /* Add all vertices one by one to the set of intermediate 
           vertices. 
          ---> Before start of an iteration, we have shortest 
               distances between all pairs of vertices such that 
               the shortest distances consider only the vertices in 
               set {0, 1, 2, .. k-1} as intermediate vertices. 
          ----> After the end of an iteration, vertex no. k is added 
                to the set of intermediate vertices and the set 
                becomes {0, 1, 2, .. k} */
        for (k = 0; k < n; k++) {
            // Pick all vertices as source one by one 
            for (i = 0; i < n; i++) {
                // Pick all vertices as destination for the 
                // above picked source 
                for (j = 0; j < n; j++) {
                    // If vertex k is on the shortest path from 
                    // i to j, then update the value of dist[i][j] 
                    if (distance[i][k] + distance[k][j] < distance[i][j]) {
                        distance[i][j] = distance[i][k] + distance[k][j];
                    }
                }
            }
        }

        return distance;
    }

    public void printSolution(int dist[][]) {
        System.out.println("The following matrix shows the shortest "
                + "distances between every pair of vertices");
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (dist[i][j] == INF) {
                    System.out.print("INF ");
                } else {
                    System.out.print(dist[i][j] + "   ");
                }
            }
            System.out.println();
        }
    }

}
