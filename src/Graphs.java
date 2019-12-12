import java.util.Arrays;

public class Graphs {

    public static double cost = 1000;
    public static double tmpCost = 0.0;
    public static String tmpRoute = "";
    public static String route = "";

    String BF(double[][] matrix) {

        // With Help From Psuedo code: https://en.wikipedia.org/wiki/Heap%27s_algorithm

        int n = matrix.length-1;
        int[] indeces = new int[n];
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            indeces[i] = 0;
            a[i] = i+1;
        }

//        System.out.println(Arrays.toString(a));

        tmpCost = matrix[0][a[0]];
        tmpRoute = 0 + ", ";

        if (matrix[0][a[0]] != 0.0) {
            for (int j = 0; j < n-1; j++) {
                    tmpCost += matrix[a[j]][a[j+1]];
                    tmpRoute += a[j] + ", ";

                    if (matrix[a[j]][a[j+1]] == 0.0) {
                        tmpCost = 0.0;
                        tmpRoute = "";
                        break;
                    }
                }
            }

        if (tmpCost != 0.0) {
            if (matrix[0][a[n - 1]] != 0) {
                tmpCost += matrix[0][a[n - 1]];
                tmpRoute += a[n - 1] + ", " + "0 )";
            } else {
                tmpCost = 0.0;
                tmpRoute = "";
            }
        }

        if (tmpCost < cost && tmpCost > 0.0) {
            cost = tmpCost;
            route = tmpRoute;
        }

        int i = 0;

        while (i < n) {
            if (indeces[i] < i) {
                if (i % 2 == 0) {
                    int tmp = a[0];
                    int tmp2 = a[i];
                    a[0] = tmp2;
                    a[i] = tmp;
                } else {
                    int tmp = a[indeces[i]];
                    int tmp2 = a[i];
                    a[indeces[i]] = tmp2;
                    a[i] = tmp;
                }
               //System.out.println(Arrays.toString(a));

                tmpCost = matrix[0][a[0]];
                tmpRoute = 0 + ", ";

                if (matrix[0][a[0]] != 0.0) {
                    for (int j = 0; j < n-1; j++) {
                            tmpCost += matrix[a[j]][a[j+1]];
                            tmpRoute += a[j] + ", ";

                            if (matrix[a[j]][a[j+1]] == 0.0) {
                                tmpCost = 0.0;
                                tmpRoute = "";
                                break;
                            }
                    }
                }

                if (tmpCost != 0.0) {
                    if (matrix[0][a[n - 1]] != 0) {
                        tmpCost += matrix[0][a[n - 1]];
                        tmpRoute += a[n - 1] + ", " + "0 )";
                    } else {
                        tmpCost = 0.0;
                        tmpRoute = "";
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


    return "( " + route + " ) With A Total Cost Of: " + cost;
    }

}
