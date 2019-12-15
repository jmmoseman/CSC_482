import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.*;

public class Graphs {

    // With Help From Psuedo code: https://en.wikipedia.org/wiki/Heap%27s_algorithm

    String BF(int[][] matrix) {

        int cost = Integer.MAX_VALUE; // Probalby won't ever get that big on a bf, lol...
        int tmpCost = 0;

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

        // If it doesn't "break;" then compare last in vertices list with home node for final cost.
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


        if (cost == Integer.MAX_VALUE) {
            return "No Complete Tour Found";
        } else {
            return Arrays.toString(route) + " With A Total Cost Of: " + cost;
        }
    }

    String greedy(int[][] matrix) {

        int cost = 0;
        int edgeCost;

        String message = " Tour Success. With A Total Cost Of: ";

        int tmpVisit = 0;
        int currNode = 0;

        int n = matrix.length;
        int[] route = new int [n+1];

        boolean[] visited = new boolean[n];


        // Simply iterate through the whole graph, finding the lowest cost node and saving it.
        // Next iteration just compares all unvisited nodes with the previous lowest cost node.

        for (int i = 0; i < n-1; i++) {
            edgeCost = Integer.MAX_VALUE; // to handle enormous euclidean things... probably paranoid with this.
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
            // Especially when this algorithm is probably best used on massive graphs. And on a complete
            // graph, like the euclidean tests, it will always find a route (and the best one for the circles).

            if (edgeCost == Integer.MAX_VALUE) {
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
                    route[i+2] = -1;
                } else {
                    cost += matrix[tmpVisit][0];
                }
            }

        }


        return Arrays.toString(route) + message + cost;
    }

    String DP(int[][] matrix) {

        int cost = Integer.MAX_VALUE;
        int tmpCost = 0;

        int n = matrix.length-1;
        int[] tmpRoute = new int[n+2];
        int[] route = new int [n+2];

        int[] indices = new int[n];
        int[] a = new int[n];

        HashSet<Integer> h = new HashSet<>();

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

        // If it doesn't "break;" then compare last in vertices list with home node for final cost.
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
        int lst = 0;
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

                // Works with this code's logic for permutations.
                // Every time the last number in the list is changed, it
                // changes the "lst" variable to it, and adds it to a hashlist,
                // which then is compared every permutation to see if it has been
                // done yet. Obviously if the number is in the list, then every possibility has
                // happened for that number being first. So, this skips 1/2 of the iterations for the
                // graph traversal loop below. A good 30%+ speedup it looks like!

                if(a[n-1] != lst) {
                    lst = a[n - 1];
                    h.add(lst);
                }

                if(h.contains(a[0])) {
                    indices[i]++;
                    i = 0;
                    continue;
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


        if (cost == Integer.MAX_VALUE) {
            return "No Complete Tour Found";
        } else {
            return Arrays.toString(route) + " With A Total Cost Of: " + cost;
        }
    }


}
