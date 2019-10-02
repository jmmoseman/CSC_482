import java.util.Arrays;
import java.util.HashSet;

public class ThreeSum {
    public static int count(long[] a) {  // Count triples that sum to 0.
        int N = a.length;
        int cnt = 0;
        for (int i = 0; i < N; i++)
            for (int j = i + 1; j < N; j++)
                for (int k = j + 1; k < N; k++)
                    if (a[i] + a[j] + a[k] == 0)
                        cnt++;
        return cnt;
    }

    public static int countFast(long[] a)  // Count triples that sum to 0. Uses Binary Search ("rank") To Speed Up.
    {
        Arrays.sort(a);
        int N = a.length;
        int cnt = 0;
        for (int i = 0; i < N; i++)
            for (int j = i+1; j < N; j++)
                if (rank(-a[i]-a[j], a) > j)
                    cnt++;
        return cnt;
    }

    public static int countFastest(long arr[], int n) {
        {
            //   boolean found = false;
            int count = 0;

            for (int i = 0; i < n - 1; i++)
            {
                // Find all pairs with sum equals to
                // "-arr[i]"
                HashSet<Long> s = new HashSet<Long>();
                for (int j = i + 1; j < n; j++)
                {
                    long x = -(arr[i] + arr[j]);
                    if (s.contains(x))
                    {
                        //System.out.printf("%d %d %d\n", x, arr[i], arr[j]);
                        //found = true;
                        count++;
                    }
                    else
                    {
                        s.add(arr[j]);

                    }
                }
            }

          /*  if (count == 0)
            {
                System.out.printf(" No Triplet Found\n");
            } */
            return count;
        }

    }
    public static int rank(long key, long[] a)
    {  return rank(key, a, 0, a.length - 1);  }
    public static int rank(long key, long[] a, int lo, int hi)
    {  // Index of key in a[], if present, is not smaller than lo
        //                                  and not larger than hi.
        if (lo > hi) return -1;
        int mid = lo + (hi - lo) / 2;
        if      (key < a[mid]) return rank(key, a, lo, mid - 1);
        else if (key > a[mid]) return rank(key, a, mid + 1, hi);
        else                   return mid;
    }

}