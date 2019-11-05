import java.util.Arrays;

public class MyBigIntegers {

    static String MBIPlus(String n1, String n2){
        // initialize arrays for length of inputs
        int[] n1Array = new int[n1.length()];
        int[] n2Array = new int[n2.length()];

        String result = "";

        // fill array with the number, using substring functions
        for (int i = n1.length()-1; i >= 0; i--) {
            n1Array[i] = Integer.parseInt(n1.substring(i, i+1));
        }

        for (int i = n2.length()-1; i >= 0; i--) {
            n2Array[i] = Integer.parseInt(n2.substring(i, i+1));
        }

        int plusLen1 = n1.length();
        int plusLen2 = n2.length();

        int carry = 0;
        int intResult = 0;

        // "gradeschool" algorithm
        for (int i = plusLen1-1, k = plusLen2-1; i >= 0 || k >=0; i--) {
            // ^^ cancel loop at either end of array 1 or 2

            // if 1 value is larger than the other, just add that + the initial carry that may be leftover
            // otherwise add both
            if (i < 0) {
                intResult = n2Array[k] + carry;
            } else if (k < 0) {
                intResult = n1Array[i] + carry;
            } else {
                intResult = n1Array[i] + n2Array[k] + carry;
            }

            // calculate carry value and "truncate" initial result if greater than 9
            if (intResult > 9) {
                carry = 1;
                intResult = intResult%10;
            } else {
                carry = 0;
            }

            // because I did this backwards (well it's about the only way to do it??)
            // I used this stringbuilder to add the calculated values together.

            StringBuilder sb = new StringBuilder(result);

            sb.insert(0, String.valueOf(intResult));

            result = sb.toString();

            k--;

        }

        return result;
    }
}
