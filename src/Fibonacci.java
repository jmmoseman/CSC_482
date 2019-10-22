// Did a quick google search on this subject, and of course this was the first page to show up:
// https://www.geeksforgeeks.org/program-for-nth-fibonacci-number/ Might as well shamelessly copy what's applicable...

// OH GOD THE GNUPLOT OUTPUTS FOR 500 trial / 5000 N EXPERIMENTS GAVE ME FLASHBACKS TO THIS STUFF: https://tinyurl.com/y6n2dllf
// Sorry...

public class Fibonacci {
    // results cache for fibrecurDP version
    public static long[] FibResultsCache;

    static long fibRecur(long n)
    {
        if (n <= 1)
            return n;
        return fibRecur(n-1) + fibRecur(n-2);
    }
    // DP recursive version
    public long fibRecurDP(int n, int m) {
        //wipe results cache
        //FibResultsCache = null;
        //increment size +1 to avoid out of bounds
        //m++;
        // assign length to cache. Initialized to 0 automatically.
        FibResultsCache = new long[m+1];

        // call & return result from main recursive function
        return fibRecurDPFunc(n);
    }

    static long fibRecurDPFunc(int n) {
        if (n <= 1) {
            return n;
            // check and see if result for current number is in cache (not 0)
        } else if (FibResultsCache[n] > 0) {
            return FibResultsCache[n];
        } else {
       //long result = fibRecurDPFunc(n-1) + fibRecurDPFunc(n-2);
       // put result into cache
       return FibResultsCache[n] = fibRecurDPFunc(n-1) + fibRecurDPFunc(n-2);
       //return result;
     }

    }

    /* function that returns nth Fibonacci number. Sophisticated matrix solution found on the "geeks" site */

    static long fibMatrix(long n)
    {
        long F[][] = new long[][]{{1,1},{1,0}};
        if (n == 0)
            return 0;
        power(F, n-1);

        return F[0][0];
    }

    static void multiply(long F[][], long M[][])
    {
        long x =  F[0][0]*M[0][0] + F[0][1]*M[1][0];
        long y =  F[0][0]*M[0][1] + F[0][1]*M[1][1];
        long z =  F[1][0]*M[0][0] + F[1][1]*M[1][0];
        long w =  F[1][0]*M[0][1] + F[1][1]*M[1][1];

        F[0][0] = x;
        F[0][1] = y;
        F[1][0] = z;
        F[1][1] = w;
    }

    /* Optimized version of power() in method 4 */
    static void power(long F[][], long n)
    {
        if( n == 0 || n == 1)
            return;
        long M[][] = new long[][]{{1,1},{1,0}};

        power(F, n/2);
        multiply(F, F);

        if (n%2 != 0)
            multiply(F, M);
    }


    static long fibLoop(int n) {
        /* Declare an array to store Fibonacci numbers. */
        long f[] = new long[n+2]; // 1 extra to handle case, n = 0
        int i;

        /* 0th and 1st number of the series are 0 and 1*/
        f[0] = 0;
        f[1] = 1;

        for (i = 2; i <= n; i++)
        {
       /* Add the previous 2 numbers in the series
         and store it */
            f[i] = f[i-1] + f[i-2];
        }

        return f[n];
    }
}
