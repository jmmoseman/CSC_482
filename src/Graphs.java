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

    String greedy(double[][] matrix) {

        double cost = 0;
        double edgeCost;

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

    private double[][] distance;
    private double minTourCost = Double.POSITIVE_INFINITY;

    private List<Integer> tour = new ArrayList<>();
    private boolean ranSolver = false;

    public void DP(double[][] distance) {

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

   // This does not work properly for the circle tests due to the way it stores costs. It is hashable for that.
   // So there will not be identical values, so those edges are not stored, and you get an incomplete tour.
   // Either that, or the way the costs are generated doesn't round to be exactly the same. So sometimes I noticed
   // even though it got the order mostly right, it was doing things like 1->3->2->4->5->6 etc...
    // It also fails for the random cost graphs. Only a complete cost matrix ("randEuc") works for this, unless the code is modified obviously.

    // For better results, perhaps include the home node in the "costEdgeList", then "normalize" final tour list.
    // Just leaving it like this for now. It's currently slower and lower quality than the normal greedy... It does partially work though! :(
    // I don't think this part of computer science is really my game. Although it kind of IS computer science...

    // Thinking about this some more. I didn't follow my original thoughts properly. I didn't take as many nodes from the
    // Cost list pairs as I should have. It should have had pairs for the best edges for all NODES, not connections...
    // It should look like (1,2, 4,7, 3,6, 2,5, 5,3, 6,0, 0,4) etc.... Actually, going on another thought.
    // You take the first node from the first pair found on this list (1), and run the search again excluding
    // this node. If the lowest cost connection on the graph is 1,2, then you want to find the lowest cost for 2 to
    // another node next. But when you search excluding 1, you want to save the costs of anything that comes before 2 on this
    // next search. IE: 3,6, 5,0, 2,7. Save 3,6, 5,0. etc. Do another search finding lowest cost for 7, save
    // anything before it (excluding those, 3 & 5 here, for future searches too), repeat until done. Another option, is to run a standard greedy search after you
    // have found the lowest cost connection, excluding those starting nodes (1,2). "Smart Greedy" or something.

    // A lot of possibilities for this stuff. I'll end now...

    String greedyExhaust(double[][] matrix) {

        double cost = 0;
        double edgeCost = Integer.MAX_VALUE;

        String message = " Tour Success. With A Total Cost Of: ";

        int currNode = 0;

        int n = matrix.length;

        boolean[] visited = new boolean[n];

        int[] finalRoute = new int[n+1];

        Integer[] tmpEdges;
        double tmpCost;

        TreeMap<Double,Integer[]> costEdgeList = new TreeMap<>();


        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n; j++) {

                if (i == 0) {
                    if (matrix[i][j] != 0) {
                        if (matrix[i][j] < edgeCost) {
                            currNode = j;
                            edgeCost = matrix[i][j];
                        }
                    }
                } else {
                    if (j != 0 && i != j && !visited[j] && !visited[i]) {
                        tmpEdges = new Integer[2];
                        tmpEdges[0] = i;
                        tmpEdges[1] = j;
                        tmpCost = matrix[i][j];
                        costEdgeList.put(tmpCost,tmpEdges);
                    }
                }

            }

            if (i == 0) {
                finalRoute[1] = currNode;
                visited[currNode] = true;
            }

        }

        Integer[] value;
        HashSet<Integer> compare = new HashSet<>();
        compare.add(finalRoute[1]);

        int k = 3;

        for (Map.Entry<Double, Integer[]> entry : costEdgeList.entrySet()) {

            if (k == 3) {
                value = entry.getValue();
                finalRoute[2] = value[0];
                finalRoute[3] = value[1];
                compare.add(value[0]);
                compare.add(value[1]);
                k++;
            }

            value = entry.getValue();

            if (!compare.contains(value[0]) && !compare.contains(value[1])) {
                finalRoute[k] = value[0];
                k++;
                finalRoute[k] = value[1];
                k++;
                compare.add(value[0]);
                compare.add(value[1]);
            }

            if (compare.size() >= n-2) {
                break;
            }

        }

        // On random costs it sometimes fails, then just puts the last few unsearched nodes in below...
        for(int l = 1; l < n; l++) {
            if (!compare.contains(l)) {
                finalRoute[k] = l;
                k++;
            }
        }

        for (int m = 0; m < n; m++) {
            if (matrix[finalRoute[m]][finalRoute[m+1]] == 0) {
                message = " Greed Is A Failure! Incomplete Tour." +
                        " Connection Failed at: (" + finalRoute[m] + "->" + finalRoute[m+1] +
                         ") Cost So Far: ";
                break;
            }
            cost += matrix[finalRoute[m]][finalRoute[m+1]];
        }


        // Original notes below:

        // Search through all costs, store in list sorted from least to most. Ignore identical connections (3->8 would be ignored if 8->3 is added).
        // Store costs with edges associated with them. (array of 2d arrays? Cost being the sortable key for the edges. Or some adt...).
        // Search through and put in order of best (start at beginning of array/list), until you have a list that = N (original costmatrix size) items.
        // Ignore identical nodes etc. (8->3 is added, 4->8 would be ignored since 8 is already in list, etc.).
        // Save total cost of this. Or when finished with step below, do a final run through the graph to confirm etc.
        // Ended up doing just this. Below was done automatically by the ADT used, and the logic I implemented.

        // Sort to make sure it's in order (element 1 is 7->4, element 2 is 4->6 etc.).
        // Return the added cost and assembled list (duplicates deleted) with the last element's cost going back to 0 added.


        return Arrays.toString(finalRoute) + message + cost;
    }


}
