import java.util.Random;

public class QuickSortSmart {
    /* https://www.geeksforgeeks.org/quicksort-using-random-pivoting/ Modified to work & some tweaks to make it "smart" */

    // public static int N = 5;
    // public static long[] arr = new long[N];

    // This Function helps in calculating random
    // numbers between low(inclusive) and high(inclusive)
    int partition_r(long arr[], int low,int high)
    {

        Random rand= new Random();
        int pivot = rand.nextInt(high-low) + low;

        //I guess this is... smart?
        if (pivot == high) {
            pivot -= (high/2);
        }

        if (pivot == low) {
            pivot += (high/2);
        }

        long temp1=arr[pivot];
        arr[pivot]=arr[high];
        arr[high]=temp1;

        return partition(arr, low, high);
    }

    /* This function takes last element as pivot,
    places the pivot element at its correct
    position in sorted array, and places all
    smaller (smaller than pivot) to left of
    pivot and all greater elements to right
    of pivot */

    int partition(long arr[], int low, int high)
    {
        // pivot is chosen randomly
        long pivot = arr[high];


        int i = (low-1); // index of smaller element
        for (int j = low; j < high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j] <= pivot)
            {
                i++;

                // swap arr[i] and arr[j]
                long temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        long temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;

        return i+1;
    }


    /* The main function that implements QuickSort()
    arr[] --> Array to be sorted,
    low --> Starting index,
    high --> Ending index */
    void sort(long arr[], int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is
            now at right place */
            int pi = partition_r(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            // I guess this is... smart, too?
            if ((pi-1) > low) {
                sort(arr, low, pi - 1);
            }
            if ((pi+1 > high)) {
                sort(arr, pi + 1, high);
            }
        }
    }
}
