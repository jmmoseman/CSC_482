import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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


    // Copied this: https://github.com/williamfiset/Algorithms/blob/master/com/williamfiset/algorithms/graphtheory/TspDynamicProgrammingRecursive.java
    // Wanted to have a faster exact version... Want to work on my own crazy thing below this before deadline instead, haha...

    private int N;
    private int START_NODE;
    private int FINISHED_STATE;

    private int[][] distance;
    private double minTourCost = Double.POSITIVE_INFINITY;

    private List<Integer> tour = new ArrayList<>();
    private boolean ranSolver = false;

    public void DP(int[][] distance) {

        this.distance = distance;
        N = distance.length;
        START_NODE = 0;

        // Validate inputs.
        if (N <= 2) throw new IllegalStateException("TSP on 0, 1 or 2 nodes doesn't make sense.");
        if (N != distance[0].length)
            throw new IllegalArgumentException("Matrix must be square (N x N)");
        if (START_NODE < 0 || START_NODE >= N)
            throw new IllegalArgumentException("Starting node must be: 0 <= startNode < N");
        if (N > 32)
            throw new IllegalArgumentException(
                    "Matrix too large! A matrix that size for the DP TSP problem with a time complexity of"
                            + "O(n^2*2^n) requires way too much computation for any modern home computer to handle");

        // The finished state is when the finished state mask has all bits are set to
        // one (meaning all the nodes have been visited).
        FINISHED_STATE = (1 << N) - 1;
    }

    // Returns the optimal tour for the traveling salesman problem.
    public List<Integer> getTour() {
        if (!ranSolver) solve();
        return tour;
    }

    // Returns the minimal tour cost.
    public double getTourCost() {
        if (!ranSolver) solve();
        return minTourCost;
    }

    public void solve() {

        // Run the solver
        int state = 1 << START_NODE;
        Double[][] memo = new Double[N][1 << N];
        Integer[][] prev = new Integer[N][1 << N];
        minTourCost = tsp(START_NODE, state, memo, prev);

        // Regenerate path
        int index = START_NODE;
        while (true) {
            tour.add(index);
            Integer nextIndex = prev[index][state];
            if (nextIndex == null) break;
            int nextState = state | (1 << nextIndex);
            state = nextState;
            index = nextIndex;
        }
        tour.add(START_NODE);
        ranSolver = true;
    }

    private double tsp(int i, int state, Double[][] memo, Integer[][] prev) {

        // Done this tour. Return cost of going back to start node.
        if (state == FINISHED_STATE) return distance[i][START_NODE];

        // Return cached answer if already computed.
        if (memo[i][state] != null) return memo[i][state];

        double minCost = Double.POSITIVE_INFINITY;
        int index = -1;
        for (int next = 0; next < N; next++) {

            // Skip if the next node has already been visited.
            if ((state & (1 << next)) != 0) continue;

            int nextState = state | (1 << next);
            double newCost = distance[i][next] + tsp(next, nextState, memo, prev);
            if (newCost < minCost) {
                minCost = newCost;
                index = next;
            }
        }

        prev[i][state] = index;
        return memo[i][state] = minCost;
    }

    String greedyExhaust(int[][] matrix) { // To do... Below is the idea if I don't get around to it... Don't really know if this will work TBH...

        // First, determine 0's lowest cost connection. Save this then make that node "visited" for the next step.
        // Search through all costs, store in list sorted from least to most. Ignore identical connections (3->8 would be ignored if 8->3 is added).
        // Store costs with edges associated with them. (array of 2d arrays? Cost being the sortable key for the edges. Or some adt...).
        // Search through and put in order of best (start at beginning of array/list), until you have a list that = N (original costmatrix size) items.
        // Ignore identical nodes etc. (8->3 is added, 4->8 would be ignored since 8 is already in list, etc.).
        // Save total cost of this. Or when finished with step below, do a final run through the graph to confirm etc.
        // Sort to make sure it's in order (element 1 is 7->4, element 2 is 4->6 etc.).
        // Return the added cost and assembled list (duplicates deleted) with the last element's cost going back to 0 added.

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
