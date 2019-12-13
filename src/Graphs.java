import java.util.Arrays;

public class Graphs {

    // This one was 100% implemented myself. I guess it works... SLOOOW of course...
    // Can't seem to get much past n = 15 or so. 4,6,8,10,12 go pretty fast, oddly. Anything beyhond that (15 or more)
    // takes hours, I originally wanted to try up to 40. That'd probably take weeks! Not entirely sure I understand
    // this behavior (why up to 10 it's fast...). But I'm fairly sure this procedure actually works properly.

    String BF(double[][] matrix) {

        double cost = 1000;
        double tmpCost = 0.0;
        StringBuilder tmpRoute = new StringBuilder();
        StringBuilder route  = new StringBuilder();

        // With Help From Psuedo code: https://en.wikipedia.org/wiki/Heap%27s_algorithm

        int n = matrix.length-1;
        int[] indeces = new int[n];
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            indeces[i] = 0;
            a[i] = i+1;
        }


        // Initial loop through the list (1 2 3 ... etc.)
        tmpCost = matrix[0][a[0]];
        tmpRoute = new StringBuilder(0 + ", ");

        // Handle home position by itself, then iterate through the rest.

        if (matrix[0][a[0]] != 0.0) {
            for (int j = 0; j < n-1; j++) {
                    tmpCost += matrix[a[j]][a[j+1]];
                    tmpRoute.append(a[j]).append(", ");

                    if (matrix[a[j]][a[j+1]] == 0.0) {
                        tmpCost = 0.0;
                        tmpRoute = new StringBuilder();
                        break;
                    }
                }
            }

        // If it doesn't "break;" then compare last in verticies list with home node for final cost.
        if (tmpCost != 0.0) {
            if (matrix[0][a[n - 1]] != 0) {
                tmpCost += matrix[0][a[n - 1]];
                tmpRoute.append(a[n - 1]).append(", ").append("0 )");
            } else {
                tmpCost = 0.0;
                tmpRoute = new StringBuilder();
            }
        }

        if (tmpCost < cost && tmpCost > 0.0) {
            cost = tmpCost;
            route = tmpRoute;
        }

        int i = 0;

        // After the initial list (1 2 3 4...) Create a permutation, then run the cost search again for each.

        while (i < n) {
            if (indeces[i] < i) {
                if (i % 2 == 0) {
                    int tmp = a[0];
                    a[0] = a[i];
                    a[i] = tmp;
                } else {
                    int tmp = a[indeces[i]];
                    a[indeces[i]] = a[i];
                    a[i] = tmp;
                }

                tmpCost = matrix[0][a[0]];
                tmpRoute = new StringBuilder(0 + ", ");

                if (matrix[0][a[0]] != 0.0) {
                    for (int j = 0; j < n-1; j++) {
                            tmpCost += matrix[a[j]][a[j+1]];
                            tmpRoute.append(a[j]).append(", ");

                            if (matrix[a[j]][a[j+1]] == 0.0) {
                                tmpCost = 0.0;
                                tmpRoute = new StringBuilder();
                                // break off the search through this path if one connection isn't there.
                                break;
                            }
                    }
                }

                if (tmpCost != 0.0) {
                    if (matrix[0][a[n - 1]] != 0) {
                        tmpCost += matrix[0][a[n - 1]];
                        tmpRoute.append(a[n - 1]).append(", ").append("0 )");
                    } else {
                        tmpCost = 0.0;
                        tmpRoute = new StringBuilder();
                    }
                }

                if (tmpCost < cost && tmpCost > 0.0) {
                    cost = tmpCost;
                    route = tmpRoute;
                }

                indeces[i]++;
                i = 0;

            } else {
                indeces[i] = 0;
                i += 1;
            }
        }


    return "( " + route.toString() + " With A Total Cost Of: " + cost;
    }

    // Dijkstra time! https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
    // I remember struggling to implement this myself in CSC 300, I'll just copy this whole cloth this time
    // And not relive that again, lol. I understood the logic nearly perfectly, I just honestly ain't some super algo coding wiz...

    // Just have to modify it to store the steps. If there's no connections, do I want it to consider for that?
    // Or just have the algorithm work assuming the graph is full (all nodes connected to each other, like the testing "circle" matrix)
    // ... hmm. Makes no difference to some of the testing. Even on the BF though, on some random matrices with lower
    // node counts, there was no tour/walk (w/e you technically call it) at all through the graph...

    // A utility function to find the vertex with minimum distance value,
    // from the set of vertices not yet included in shortest path tree

    static final double V = 9;
    int minDistance(double dist[], Boolean sptSet[])
    {
        // Initialize min value
        double min = Integer.MAX_VALUE;
        int min_index = -1;

        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    // A utility function to print the constructed distance array
    void printSolution(double dist[])
    {
        System.out.println("Vertex \t\t Distance from Source");
        for (int i = 0; i < V; i++)
            System.out.println(i + " \t\t " + dist[i]);
    }

    // Function that implements Dijkstra's single source shortest path
    // algorithm for a graph represented using adjacency matrix
    // representation
    void dijkstra(double graph[][], int src)
    {
        double dist[] = new double[(int)V]; // The output array. dist[i] will hold
        // the shortest distance from src to i

        // sptSet[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        Boolean sptSet[] = new Boolean[(int)V];

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        // Distance of source vertex from itself is always 0
        dist[src] = 0;

        // Find shortest path for all vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to src in first
            // iteration.
            int u = minDistance(dist, sptSet);

            // Mark the picked vertex as processed
            sptSet[u] = true;

            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < V; v++)

                // Update dist[v] only if is not in sptSet, there is an
                // edge from u to v, and total weight of path from src to
                // v through u is smaller than current value of dist[v]
                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
                    dist[v] = dist[u] + graph[u][v];
        }

        // print the constructed distance array
        printSolution(dist);
    }


}
