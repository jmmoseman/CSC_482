public class LCS {
    //Brute force below.
    static int LCSBF(String str1, String str2) {
        int lcsLen = 0;
        int k;
        int i1 = str1.length();
        int i2 = str2.length();

        for (int i = 0; i < i1; i++) {
            for (int j = 0; j < i2; j++) {
                for (k=0; (i+k) < i1 && (j+k) < i2; k++){
                    if (!str1.substring(i+k,i+k+1).equals(str2.substring(j+k,j+k+1))) break;
                }
                if (k > lcsLen) {
                    lcsLen = k;
                }
            }
        }

        return lcsLen;
    }

    static String LCSOPT(String str1, String str2) {
        int lcsLen = 0;
        int k;
        int start = 0;
        int i1 = str1.length();
        int i2 = str2.length();

        if (i1 == i2) {
            if (str1.equals(str2)) {
                lcsLen = i1;
            } else {
                    for (int i = 0; i < i1; i++) {
                        for (int j = 0; j < i2; j++) {
                            for (k = 0; (i + k) < i1 && (j + k) < i2; k++) {
                                if (!str1.substring(i + k, i + k + 1).equals(str2.substring(j + k, j + k + 1))) break;
                            }
                            if (k > lcsLen) {
                                // Couldn't quite get this logic right. It gives incorrect results sometimes.
                                i += k-1;
                                start = j;
                                lcsLen = k;
                            }
                        }
                    }
                   /* int sum = 0;
                   int max = 0;
                   int l = 0;
                   for (int i = 0; i < i1; i++) {
                       if (str1.substring(l+i,i+l+1).equals(str2.substring(i,i+1))) {
                           sum++;
                       } else {
                           if(i != i1-1) {
                               continue;
                           }
                       }

                       if (i == i1-1 && l != i1) {
                           l++;
                           i = 0;
                           sum = 0;
                           continue;
                       } else {
                           if (sum > max) {
                               max = sum;
                           }
                       }
                   }
                   lcsLen = max; Above some experiments that I sort of gave up on... */

                }
        } else {
            for (int i = 0; i < i1; i++) {
                for (int j = 0; j < i2; j++) {
                    for (k = 0; (i + k) < i1 && (j + k) < i2; k++) {
                        if (!str1.substring(i + k, i + k + 1).equals(str2.substring(j + k, j + k + 1))) break;
                    }
                    if (k > lcsLen) {
                        i += k-1;
                        start = j;
                        lcsLen = k;
                    }
                }
            }
        }
            return "Length Of LCS: " + String.valueOf(lcsLen) + ", LCS: " + str2.substring(start, lcsLen + start);
    }

    // Faster version: https://www.geeksforgeeks.org/longest-common-subsequence-dp-using-memoization/
    // It doesn't seem to add up right?

    // Returns length of LCS for X[0..m-1], Y[0..n-1] */
    // memoization applied in recursive solution

    static int lcsFast(String X, String Y, int m, int n, int dp[][]) {
        // base case
        if (m == 0 || n == 0) {
            return 0;
        }

        // if the same state has already been
        // computed
        if (dp[m - 1][n - 1] != -1) {
            return dp[m - 1][n - 1];
        }

        // if equal, then we store the value of the
        // function call
        if (X.charAt(m - 1) == Y.charAt(n - 1)) {

            // store it in arr to avoid further repetitive
            // work in future function calls
            dp[m - 1][n - 1] = 1 + lcsFast(X, Y, m - 1, n - 1, dp);

            return dp[m - 1][n - 1];
        } else {

            // store it in arr to avoid further repetitive
            // work in future function calls
            dp[m - 1][n - 1] = Math.max(lcsFast(X, Y, m, n - 1, dp),
                    lcsFast(X, Y, m - 1, n, dp));

            return dp[m - 1][n - 1];
        }
    }


}
