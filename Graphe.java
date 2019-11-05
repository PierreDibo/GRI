import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


/**
 * @author Aillerie Anthony
 * @author dibop
 */
public class Graphe {

	public int n, m, dmax, somdmax;
	public Sommet[] V;
	public final static int INF = 99999;

	public void makeGraphe(int[][] graphe) {
		for (int i = 0; i < graphe.length; i++) {
			for (int j = 0; j < graphe[i].length; j++) {
				if (i == j) {
					continue;
				} else {
					graphe[i][j] = INF;
				}
			}
			if (V[i].adj != null) {
				for(int k = 0; k < V[i].adj.length; k++) {
					graphe[i][V[i].adj[k]] = 1;
				}
			}
		}
	}

	public void floydWarshall(int[][] m) {
		int dist[][] = new int[n][n];
		int i, j, k;
		int path[] = new int [n];

		/* Initialize the solution matrix same as input graph matrix. 
           Or we can say the initial values of shortest distances 
           are based on shortest paths considering no intermediate 
           vertex. */
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				dist[i][j] = m[i][j];
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
					} else if (dist[i][k] + dist[k][j] == dist[i][j]) {
						path[i] += 1;
					}
				}
			}
		}
		System.out.println("NB PATHS : " + Arrays.toString(path));
		printSolution(dist);
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

	void BFS(Sommet[] graphe, Sommet s, Sommet t, int dist[], int paths[]) { 
		Map<Integer, List<Integer>> list = new HashMap<>();
		boolean[] visited = new boolean[n]; 
		for (int i = 0; i < n; i++) 
			visited[i] = false; 
		dist[s.num] = 0; 
		paths[s.num] = 1;
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(s.num); 
		
		visited[s.num] = true; 
		while (!q.isEmpty()) { 
			int curr = q.peek(); 
			q.poll();
			if(curr == t.num) {
				break;
			}
			if (graphe[curr].adj != null) {
				for (int j = 0; j < graphe[curr].adj.length; j++) { 
					int adjacent = graphe[curr].adj[j];
					if (visited[adjacent] == false) { 
						q.add(adjacent); 
						visited[adjacent] = true; 
					}

					if (dist[adjacent] > dist[curr] + 1) { 	
						dist[adjacent] = dist[curr] + 1; 
						paths[adjacent] = paths[curr]; 
					} 

					else if (dist[adjacent] == dist[curr] + 1) {
						paths[adjacent] += paths[curr];
					}
				} 
			}
		} 
		list.entrySet().forEach(entry->{
		    System.out.println(entry.getKey() + " " + entry.getValue());  
		 });
	}
	/* TODO */
	public Map<Integer, List<Integer>> allPaths(Sommet s, int[] dist, int[] paths) {
		Map<Integer, List<Integer>> list = new HashMap<>();
		int count = 0;
		for(int i = 0; i < paths[paths.length - 1]; i++) {
			list.put(i, new ArrayList<Integer>());
			list.get(i).add(s.num);
		}
		for(int i = 0; i < dist.length - 1; i++) {
			if((dist[i] + 1) == dist[i + 1]) {
				System.out.println("I : " + dist[i] + " " + i);
				list.get(i).add(i+1);
				/*else if (dist[i] == dist[j]) {
					count++;
				}*/	
			}	
		}
		list.entrySet().forEach(entry->{
		    System.out.println(entry.getKey() + " " + entry.getValue());  
		 });
		return list;
	}

	public void findShortestPaths() { 
		int[] dist = new int[n];
		int[] paths = new int[n]; 
		for (int i = 0; i < n; i++) 
			dist[i] = INF;
		for (int i = 0; i < n; i++) 
			paths[i] = 0; 
		BFS(V, V[0], V[6], dist, paths);
		allPaths(V[0], dist,paths);
		System.out.println("Numbers of shortest Paths are: "); 
		System.out.println(Arrays.toString(paths));
		System.out.println(Arrays.toString(dist));
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
