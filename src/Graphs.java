import java.util.Arrays;

public class Graphs {

    // This one was 100% implemented myself. I guess it works...

    String BF(int[][] matrix) {

        int cost = 150;
        int tmpCost = 0;

        // With Help From Psuedo code: https://en.wikipedia.org/wiki/Heap%27s_algorithm

        int n = matrix.length-1;
        int[] tmpRoute = new int[n+2];
        int[] route = new int [n+2];

        int[] indices = new int[n];
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            indices[i] = 0;
            a[i] = i+1;
        }


        // Initial loop through the list (1 2 3 ... etc.)
        tmpCost = matrix[0][a[0]];
        // Handle home position by itself, then iterate through the rest.

        if (matrix[0][a[0]] != 0) {
            for (int j = 0; j < n-1; j++) {
                    tmpCost += matrix[a[j]][a[j+1]];
                    tmpRoute[j+1] = a[j];

                    if (matrix[a[j]][a[j+1]] == 0) {
                        tmpCost = 0;
                        tmpRoute = new int[n+2];
                        break;
                    }
                }
            }

        // If it doesn't "break;" then compare last in verticies list with home node for final cost.
        if (tmpCost != 0) {
            if (matrix[0][a[n - 1]] != 0) {
                tmpCost += matrix[0][a[n - 1]];
                tmpRoute[n] = a[n-1];
            } else {
                tmpCost = 0;
                tmpRoute = new int[n+2];
            }
        }

        if (tmpCost < cost && tmpCost > 0) {
            cost = tmpCost;
            route = tmpRoute;
        }

        int i = 0;

        // After the initial list (1 2 3 4...) Create a permutation, then run the cost search again for each.

        while (i < n) {
            if (indices[i] < i) {
                if (i % 2 == 0) {
                    int tmp = a[0];
                    a[0] = a[i];
                    a[i] = tmp;
                } else {
                    int tmp = a[indices[i]];
                    a[indices[i]] = a[i];
                    a[i] = tmp;
                }

                tmpCost = matrix[0][a[0]];
                tmpRoute = new int[n+2];

                if (matrix[0][a[0]] != 0) {
                    for (int j = 0; j < n-1; j++) {
                            tmpCost += matrix[a[j]][a[j+1]];
                            tmpRoute[j+1] = a[j];

                            if (matrix[a[j]][a[j+1]] == 0) {
                                tmpCost = 0;
                                tmpRoute = new int[n+2];
                                // break off the search through this path if one connection isn't there.
                                break;
                            }
                    }
                }

                if (tmpCost != 0) {
                    if (matrix[0][a[n - 1]] != 0) {
                        tmpCost += matrix[0][a[n - 1]];
                        tmpRoute[n] = a[n-1];
                    } else {
                        tmpCost = 0;
                        tmpRoute = new int[n+2];
                    }
                }

                if (tmpCost < cost && tmpCost > 0) {
                    cost = tmpCost;
                    route = tmpRoute;
                }

                indices[i]++;
                i = 0;

            } else {
                indices[i] = 0;
                i += 1;
            }
        }


        if (cost == 150) {
            return "No Complete Tour Found";
        } else {
            return Arrays.toString(route) + " With A Total Cost Of: " + cost;
        }
    }

    String greedy(int[][] matrix) {

        int cost = 0;
        int edgeCost;

        String message = " Tour Success. With A Total Cost Of: ";

        boolean inc = false;

        int tmpVisit = 0;
        int currNode = 0;

        int n = matrix.length;
        int[] route = new int [n+1];

        boolean[] visited = new boolean[n];


        // Simply iterate through the whole graph, finding lowest cost per connection then saving it.
        // Next iteration just compares all unvisited nodes with the previous lowest cost node.

        for (int i = 0; i < n-1; i++) {
            edgeCost = 100;
            for (int j = 0; j < n; j++) {

                if (matrix[tmpVisit][j] != 0) {
                    if (matrix[tmpVisit][j] < edgeCost && !visited[j]) {
                        currNode = j;
                        edgeCost = matrix[tmpVisit][j];
                    }
                }

            }

            if (i == 0) {
                visited[i] = true;
            }


            // There could be some logic for walking back up through the graph, and searching again.
            // But I'll just leave it like this. Realistically, this probably isn't the WORST thing to do.
            // Especially when this algorithm is best used on massive graphs.

            if (edgeCost == 100) {
                inc = true;
                message = " Greed Is A Failure! Incomplete Tour. Cost So Far: ";
                break;
            }

            tmpVisit = currNode;
            visited[tmpVisit] = true;


            cost += edgeCost;
            route[i+1] = tmpVisit;

            if (i == n-2) {
                if (matrix[tmpVisit][0] == 0) {
                    // If there's no final connection, print special message.
                    message = " Greed Is A Failure! No Final Path Home. Cost So Far: ";
                    inc = true;
                    route[i+2] = -1;
                } else {
                    cost += matrix[tmpVisit][0];
                }
            }

        }


        if (inc) {
            return Arrays.toString(route) + message + cost;
        } else {
            return Arrays.toString(route) + message + cost;
        }
    }


}
