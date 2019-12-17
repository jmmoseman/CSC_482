import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

        String message = " With A Total Cost Of: ";

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


    // This thing doesn't really work the way a proper DP algorithm should, even though some of the logic
    // is correct. It still does some compares for ALL permutations, it only skips for the main graph loop...
    // And to do less work, there is more work required... Calling it dumb for a reason, haha...

    String DP_Dumb(int[][] matrix) {

        int cost = Integer.MAX_VALUE;
        int tmpCost;

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
        int lst = a[n-1];


        int roundCost;
        int roundCost1;
        int[] firstRoute;
        int c;

        h.add(lst);

        roundCost = matrix[0][a[0]];
        firstRoute = new int[n+2];

        if (matrix[0][a[0]] != 0) {
            for (int j = 0; j < n-1; j++) {
                roundCost += matrix[a[j]][a[j+1]];
                firstRoute[j+1] = a[j];

                if (matrix[a[j]][a[j+1]] == 0) {
                    roundCost = 0;
                    firstRoute = new int[n+2];
                    lst = 0;
                    break;
                }
            }
            if (roundCost != 0) {
                roundCost += matrix[0][a[n - 1]];
                firstRoute[n] = a[n - 1];
            }
        } else {
            roundCost = 0;
            lst = 0;
        }


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


                if(a[n-1] != lst) {
                    lst = a[n - 1];
                    h.add(lst);

                    roundCost = matrix[0][a[0]];
                    firstRoute = new int[n+2];

                    if (matrix[0][a[0]] != 0) {
                        for (int j = 0; j < n-1; j++) {
                            roundCost += matrix[a[j]][a[j+1]];
                            firstRoute[j+1] = a[j];

                            if (matrix[a[j]][a[j+1]] == 0) {
                                roundCost = 0;
                                break;
                            }
                        }
                            if (roundCost != 0) {
                                roundCost += matrix[0][a[n - 1]];
                                firstRoute[n] = a[n - 1];
                            } else {
                                indices[i]++;
                                i = 0;
                                lst = 0;
                                continue;
                            }

                    } else {
                        indices[i]++;
                        i = 0;
                        lst = 0;
                        continue;
                    }
                }

                if(h.contains(a[0])) {
                    indices[i]++;
                    i = 0;
                    continue;
                } else if (matrix[0][a[n - 1]] == 0) {
                    indices[i]++;
                    i = 0;
                    continue;
                }


                    c = 0;

                    for (int l = n-1; l > 0; l--) {
                        if (firstRoute[l+1] == a[l]) {
                            c++;
                        } else {
                            break;
                        }
                    }

                   roundCost1 = roundCost;

                    tmpCost = matrix[0][a[0]];
                    roundCost1 -= matrix[firstRoute[0]][firstRoute[1]];

                    if (matrix[0][a[0]] != 0) {
                        for (int j = 0; j < (n - c); j++) {
                            roundCost1 -= matrix[firstRoute[j+1]][firstRoute[j + 2]];
                            tmpCost += matrix[a[j]][a[j + 1]];

                            if (matrix[a[j]][a[j + 1]] == 0) {
                                tmpCost = 0;
                                // break off the search through this path if one connection isn't there.
                                break;
                            }
                        }
                    }


                    if (tmpCost != 0) {
                        tmpCost += roundCost1;
                    }

                    if (tmpCost < cost && tmpCost > 0) {
                        cost = tmpCost;
                        route = new int[n + 2];
                        System.arraycopy(a,0, route,1, n);
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

    String greedyExaust(int[][] matrix) { // To do...

        // Search through all costs, store in list sorted from least to most.
        // Store costs with edges associated with them. (array of 2d arrays? Cost being the sortable key for the edges. Or some adt...).
        // Search through and put in order of best (start at beginning of array/list), until you have a list that = N (original matrix size) items.
        // Ignore identical nodes etc. (8->3 is added, 4->8 would be ignored since 8 is already in list, etc.).
        // Save total cost of this. Or when finished with step below, do a final
        // run through the graph to confirm etc.
        // Sort to make sure it's in order (element 1 is 7->4, element 2 is 4->6 etc.).
        // Return the added cost and assembled matrix with the last element's cost going back to 0 added.

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


}
