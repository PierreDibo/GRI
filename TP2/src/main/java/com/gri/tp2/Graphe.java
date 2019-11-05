package com.gri.tp2;

import java.util.ArrayList;

/**
 *
 * @author dibop
 */
public class Graphe {

    public static final int INF = 99999;
    public int n, m, dmax, somdmax;
    public Sommet[] V;
    int[][] distance;

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

    public Sommet[] preds(Sommet s, Sommet t, int[] visite) {
        ArrayList<Tuple> ens = new ArrayList<>();
        Sommet[] rslt = new Sommet[distance[s.num][t.num]];
        int index = 0;
        if (V[s.num].adj != null) {
            for (int i = 0; i < V[s.num].adj.length; i++) {
                if (distance[t.num][V[s.num].adj[i]] + 1 == distance[s.num][t.num]) {

                    rslt[index++] = V[V[s.num].adj[i]];
                }
            }
        }

        return rslt;
    }

    private int np(Sommet s, Sommet t) {
        int distST = distance[s.num][t.num];
        int rslt = 0;
        switch (distST) {
            case 0:
                return rslt;
            case 1:
                return 1;
            case INF:
                return rslt;
            default:
                for (int i = 0; i < distance[s.num].length; i++) {
                    if (distance[s.num][i] + 1 == distance[s.num][t.num]) {
                        rslt += 1 + np(s, V[i]);
                    }
                }
                break;
        }

        return rslt;
    }

    private int npcc(Sommet s, Sommet t) {
        int rslt = 0;
        if (distance[s.num][t.num] == 1) {
            return 1;
        } else {
            for (int i = 0; i < distance[s.num].length; i++) {
                if (distance[s.num][i] + 1 == distance[s.num][t.num]) {
                    rslt += 1 + npcc(s, V[i]);
                }
            }
            return rslt;
        }
    }

    private int npccs(Sommet s, Sommet v, Sommet t) {
        return np(s, v) * np(v, t);
    }

    private int beetweeness(Sommet s, Sommet v, Sommet t) {
        if (distance[s.num][t.num] == INF) {
            return 0;
        } else {
            int den = npccs(s, v, t);
            int num = np(s, t);

            return num == 0 ? 0 : den / num;
        }

    }

    public double bet(Sommet v) {

        return 0.0;
    }

}
