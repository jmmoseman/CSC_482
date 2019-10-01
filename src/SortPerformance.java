import java.lang.management.*;
import java.util.Arrays;

public class SortPerformance {
    static long MAXVALUE = 2000000000;
    static long MINVALUE = -2000000000;
    static long numberOfTrials = 20;
    static int MINSIZE = 1000;
    static int MAXSIZE =  10000;
    static int SIZEINC = 1000;
    static int numssize = 10000000;

    public static void main(String[] args) {

        long[] testList = createRandomIntegerList(10);
        long findVal = 0;
        double pos = 0;
        long totalTime = 0;

        long[] nums = new long[numssize];
        for (int i = 0; i < nums.length; i++)
            nums[i] = i;

        //System.out.println(Arrays.toString(nums));
      /*  long pos = 0;
        System.out.println("Checking correctness of bubble sort function...");
        System.out.println(Arrays.toString(testList));
        bubbleSortNumberList(testList);
        System.out.println(Arrays.toString(testList));
        System.out.println("-----------------------------------------------"); */

        System.out.println("InputSize AvgTime TotalTimeForAllTrials \n");

        /* for each size of input we want to test... */
        for (int inputSize=MINSIZE; inputSize<=MAXSIZE; inputSize = inputSize + SIZEINC) {
            /* repeat for desired number of trials (for a specific sizebean.getCurrentThreadCpuTime() of input)... */;
            //System.out.println(Arrays.toString(testList));
            for (long trial = 0; trial < numberOfTrials; trial++) {
                /* For one trial: */
                /* generate (random?) input data of desired size (a list of N random numbers) */
                testList = createRandomIntegerList(inputSize);
                //System.out.println(Arrays.toString(testList));
                //bubbleSortNumberList(testList);
                /* run the trial */
               /* pos = (Math.random() * numssize) + (Math.random() * numssize);
                //System.out.println("Pos: " + pos);
                if (pos > numssize) {
                    findVal = -1;
                } else {
                    findVal = nums[(int)pos];
                } */
               // System.out.println("Search for: " + findVal);
                //gets number based on randomly generated number based on list length.
                long timeStampBefore = getCpuTime(); /* get a "before" time stamp */
                /* apply the test function to the test input */

                //System.out.println("Found (or not) at: " +searchRandList(testList, findVal));
                bubbleSortNumberList(testList);
                //searchRandList(testList, findVal);
                //searchSortedList(nums, findVal);
                /* get "after" timestamp, calculate trial time */

                long timeStampAfter = getCpuTime();
                long trialTime = timeStampAfter - timeStampBefore;

                totalTime = totalTime + trialTime;

            }
            double averageTime = (double)totalTime / (double)numberOfTrials;
            /* print data for this size of input */
            System.out.println(inputSize + "  " + averageTime + "  " + totalTime);
        }
    }
    /* return index of the searched number if found, or -1 if not found */
    public static void bubbleSortNumberList(long[] list) {
        /*make N passes through the list (N is length of the list) */
        for(int i=0;i<list.length-1;i++) {
            /* for index from 0 to N-1, compare item[index] to next item, swap if needed */
            for(int j=0; j<list.length-1-i;j++) {
                if(list[j]>list[j+1]){
                    long tmp = list[j];
                    list[j] = list[j+1];
                    list[j+1] = tmp;
                }
            }
        }
    }

    public static long[] createRandomIntegerList(int size) {
        long[] newList = new long[size];
        for(int j=0; j<size; j++) {
            newList[j] = (long)(MINVALUE +  Math.random() * (MAXVALUE - MINVALUE));
        }

        return newList;
    }

    public static int searchRandList(long[] list, long num) {
        int val = -1;
        for (int i = 0; i < list.length -1; i++) {
                if(num == list[i]) {
                    val = i;
                    i = list.length - 1;
                }
    }

        return val;
    }

    public static int searchSortedList(long[] list, long num) {
        int val = -1;
        int i = 0;
        int j = list.length - 1;
        int k = (i + j) / 2;

        /*for (int k = (i+j)/2; i < j; i++) {
            if(num == list[k] || num == list[i]) {
                val = i;
                i = j;
            } else if (num > list[k]) {
                k++;
            }
        } */

        while (i <= j){
            if (list[k] < num) {
                i = k + 1;
            } else if (list[k] == num) {
                val = k;
                break;
            } else {
                j = k - 1;
            }
            k = (i + j) / 2;
        }

        return val;
    }

    //Get CPU time in nanoseconds since the program(thread) started.
    /** from: http://nadeausoftware.com/articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking#TimingasinglethreadedtaskusingCPUsystemandusertime **/
    public static long getCpuTime(){
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                bean.getCurrentThreadCpuTime() : 0L;
    }
}
