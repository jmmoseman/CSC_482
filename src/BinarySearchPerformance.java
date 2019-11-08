import java.lang.management.ManagementFactory;

import java.lang.management.ThreadMXBean;

import java.io.*;


public class BinarySearchPerformance {



    static ThreadMXBean bean = ManagementFactory.getThreadMXBean( );



    /* define constants */

    static long MAXVALUE =  2000000000;

    static long MINVALUE = -2000000000;

    static int numberOfTrials = 5000;

    static int MAXINPUTSIZE  = 150;

    static int MININPUTSIZE  =  1;

    // static int SIZEINCREMENT =  10000000; // not using this since we are doubling the size each time 



    static String ResultsFolderPath = "/home/jeremy/Results/"; // pathname to results folder

    static FileWriter resultsFile;

    static PrintWriter resultsWriter;





    public static void main(String[] args) {

        // run the whole experiment at least twice, and expect to throw away the data from the earlier runs, before java has fully optimized
       System.out.println("Running first full experiment...");
        runFullExperiment("BigIntMult-Exp1-ThrowAway.txt");
        System.out.println("Running first full experiment...");
        runFullExperiment("BigIntMult-Exp2.txt");
        System.out.println("Running first full experiment...");
        runFullExperiment("BigIntMult-Exp3.txt");


      /*  long[] testList = {-1,3,2,-5,2,2,50,-20,-30};

        ThreeSum test = new ThreeSum();

        System.out.println("Total Triples: " + test.countFastest(testList, testList.length)); */

    }



    static void runFullExperiment(String resultsFileName){



        try {

            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);

            resultsWriter = new PrintWriter(resultsFile);

        } catch(Exception e) {

            System.out.println("*****!!!!!  Had a problem opening the results file "+ResultsFolderPath+resultsFileName);

            return; // not very foolproof... but we do expect to be able to create/open the file... 

        }



        ThreadCpuStopWatch BatchStopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials 

        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial 



        resultsWriter.println("#Calculation  AverageTime"); // # marks a comment in gnuplot data

        resultsWriter.flush();

        /*

        long[] testList = createRandomIntegerList(20);

        System.out.println("Presorted Small List: " + Arrays.toString(testList));

        //  System.out.println(Arrays.toString(testList));
        MergeSort test = new MergeSort();

        test.sort(testList,0, 20 - 1);

        System.out.println("MergeSorted Small List: " + Arrays.toString(testList));

         */

        /*long[] testList = {-1,3,2,-5,2,2,50,-20,-30};

        ThreeSum test = new ThreeSum();

        System.out.println("Total Triples: " + test.countFastest(testList, testList.length)); */

        //long[] testList;

        /* for each size of input we want to test: in this case starting small and doubling the size each time */

        for(String inputSize="3",inputSize2 = "4"; inputSize.length()<=MAXINPUTSIZE; inputSize+= String.valueOf((int)(Math.random() * 9))) {
            // progress message...

            System.out.println("Running test for calculation "+inputSize+" + " + inputSize2+ " ... ");



            /* repeat for desired number of trials (for a specific size of input)... */

            long batchElapsedTime = 0;

            // generate a list of randomly spaced integers in ascending sorted order to use as test input 

            // In this case we're generating one list to use for the entire set of trials (of a given input size) 

            // but we will randomly generate the search key for each trial 

           // System.out.print("    Presorted list...");

            //long[] testList = createSortedntegerList(inputSize);

         //   System.out.print("    MergeSorted list...");

            //System.out.println(Arrays.toString(testList));
            //System.out.println("...done.");

            System.out.print("    Running trial batch...");



            /* force garbage collection before each batch of trials run so it is not included in the time */

            System.gc();





            // instead of timing each individual trial, we will time the entire set of trials (for a given input size) 

            // and divide by the number of trials -- this reduces the impact of the amount of time it takes to call the 

            // stopwatch methods themselves

           // testList = createIntegerList(inputSize);

            MyBigIntegers test1 = new MyBigIntegers();

            BatchStopwatch.start(); // comment this line if timing trials individually



            // run the tirals 

            for (long trial = 0; trial < numberOfTrials; trial++) {

                // generate a random key to search in the range of a the min/max numbers in the list 

                //long testSearchKey = (long) (0 + Math.random() * (testList[testList.length-1]));

                /* force garbage collection before each trial run so it is not included in the time */

                // System.gc();


              //  testList = createRandomIntegerList(inputSize);

            //    System.out.println("Unsorted List: " + Arrays.toString(testList));
            //    System.out.println("List Sorted? " + verifySorted(testList));

                test1.MBIMult("86336","792522341");

          //       System.out.println("Sorted List: " + Arrays.toString(testList));
           //      System.out.println("List Sorted? " + verifySorted(testList));


                //TrialStopwatch.start(); // *** uncomment this line if timing trials individually

                /* run the function we're testing on the trial input */

                //long foundIndex = binarySearch(testSearchKey, testList);

                // batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime(); // *** uncomment this line if timing trials individually

            }

            batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if timing trials individually 

            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double)numberOfTrials; // calculate the average time per trial in this batch



            /* print data for this size of input */

           // resultsWriter.printf("%12s %12s %15.2f \n", inputSize, inputSize2, averageTimePerTrialInBatch); // might as well make the columns look nice
            resultsWriter.printf("%12s %15.2f \n", test1.MBIPlus(inputSize,inputSize2), averageTimePerTrialInBatch); // For big ints!
            // modified for easier importing to excel...
            //resultsWriter.printf("%15.2f \n", averageTimePerTrialInBatch);


            resultsWriter.flush();

            System.out.println(" ....done.");
            inputSize2+= String.valueOf((int)(Math.random() * 9));

        }

    }



    /* return index of the searched number if found, or -1 if not found */

    public static int binarySearch(long key, long[] list) {

        int i = 0;

        int j= list.length-1;

        if (list[i] == key) return i;

        if (list[j] == key) return j;

        int k = (i+j)/2;

        while(j-i > 1){

            //resultsWriter.printf("%d %d %d %d %d %d\n",i,k,j, list[0], key, list[list.length-1]); resultsWriter.flush(); 

            if (list[k]== key) return k;

            else if (list[k] < key) i=k;

            else j=k;

            k=(i+j)/2;

        }

        return -1;

    }

    public static long[] createRandomIntegerList(int size) {
        long[] newList = new long[size];
        for(int j=0; j<size; j++) {
            newList[j] = (long)(MINVALUE +  Math.random() * (MAXVALUE - MINVALUE));
        }

        return newList;
    }


    public static long[] createIntegerList (int numssize) {

        long[] nums = new long[numssize];
        for (int i = 0; i < nums.length; i++)
            nums[i] = i;

        return nums;

    }

    public static boolean verifySorted(long[] arr) {
        //Naive method I guess, haha...
        int tf = 0;

        for (int i = 1; i < (arr.length - 1); i++) {
            if (arr[i] < arr[i-1]){
                tf = 1;
            }
        }

        if (tf == 0) {
            return true;
        } else {
            return false;
        }

    }

    public static long[] createSortedntegerList(int size) {

        long[] newList = new long[size];

        newList[0] = (long) (10 * Math.random());

        for (int j = 1; j < size; j++) {

            newList[j] = newList[j - 1] + (long) (10 * Math.random());

            //if(j%100000 == 0) {resultsWriter.printf("%d  %d <<<\n",j,newList[j]);  resultsWriter.flush();}
        }

        return newList;

    }

} 