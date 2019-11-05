
/**
 *
 * @author dibop
 */
public class Graphe {

    public int n, m, dmax, somdmax;
    public Sommet[] V;

    public void makeGraphe(int[][] graphe) {
        for (int i = 0; i < graphe.length; i++) {
            for (int j = 0; j < graphe[i].length; j++) {
                if (i == j) {
                    graphe[i][j] = 0;
                } else {
                    for(int k = 0; k < V[i].adj.length; k++) {
                        
                    }
                }
            }
        }

        for (int i = 0; i < V.length; i++) {
            if (V[i].degre == 0) {
                continue;
            }
            for (int j = 0; j < V[i].degre; j++) {
                if (i == j) {
                    graphe[i][j] = 0;
                }
                V[i].adj[j] = -1;
            }
            
        }
        /*n = 5
        [(0, 3, [1, 2, 4]), ...]
        
        i = 0 : 0
        
        j = 0 : 1
        
        [0, 1, 0, 0, 0]
        
        k, 0...n
        k = 0 : if k != j, INF else 1 : [INF, 1, INF, INF, INF]
        
        j = 1: 2
        
        [INF, 1, INF, INF, INF]
        
        
        k, 0...n
        k = 0 : if k != j, INF else 1 : [INF, 1, INF, INF, INF]
        i = 1: 1*/
        
        
                
           
        
    }

    public void floydWarshall(Matrice m) {
        int dist[][] = new int[n][n];
        int i, j, k;

        /* Initialize the solution matrix same as input graph matrix. 
           Or we can say the initial values of shortest distances 
           are based on shortest paths considering no intermediate 
           vertex. */
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                dist[i][j] = m.t[i][j];
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
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
        printSolution(dist);
    }

    public void printSolution(int dist[][]) {
        System.out.println("The following matrix shows the shortest "
                + "distances between every pair of vertices");
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (dist[i][j] == Integer.MAX_VALUE) {
                    System.out.print("INF ");
                } else {
                    System.out.print(dist[i][j] + "   ");
                }
            }
            System.out.println();
        }
    }

    public Sommet pred(Sommet s, Sommet t) {
        return null;
    }

    public int npcc(Sommet s, Sommet t, int val) {
        if (dist(s, t) == 1) {
            return 1;
        } else {
            return npcc(s, pred(s, t), val++);
        }
    }

    public int dist(Sommet s, Sommet t) {
        return 0;
    }

}
