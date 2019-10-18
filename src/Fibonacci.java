// Did a quick google search on this subject, and of course this was the first page to show up:
// https://www.geeksforgeeks.org/program-for-nth-fibonacci-number/ Might as well shamelessly copy what's applicable...

import java.util.Arrays;

public class Fibonacci {
    // results cache for fibrecurDP version
    public static long[] FibResultsCache;

    static long fibRecur(long n)
    {
        if (n <= 1)
            return n;
        return fibRecur(n-1) + fibRecur(n-2);
    }

    public long fibRecurDP(long n, long m) {
        //wipe results cache
        FibResultsCache = null;
        //increment size +1 to avoid out of bounds
        m++;
        // assign length to cache
        FibResultsCache = new long[(int)m];
        // fill with null (-1) values for each call
        for(int i = 0; i < m; i++)
            FibResultsCache[i] = -1;

        // call & return result from main recursive function
        return fibRecurDPFunc(n);
    }

    static long fibRecurDPFunc(long n) {
        long result = n;
        if (n <= 1) {
            return result;
            // check and see if result for current number is in cache (not -1)
        } else if (FibResultsCache[(int)n] > -1) {
            result = FibResultsCache[(int)n];
            return result;
        } else {
       result = fibRecurDPFunc(n-1) + fibRecurDPFunc(n-2);
       // put result into cache
       FibResultsCache[(int)n] = result;
       return result;
     }

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
