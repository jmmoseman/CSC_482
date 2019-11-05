import java.util.Arrays;

public class MyBigIntegers {

    static String MBIPlus(String n1, String n2){
        int[] n1Array = new int[n1.length()];
        int[] n2Array = new int[n2.length()];

        String result = "";

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
        for (int i = plusLen1-1, k = plusLen2-1; i >= 0 && k >=0; i--) {
            intResult = n1Array[i] + n2Array[k] + carry;

            if (intResult > 9) {
                carry = 1;
                intResult = intResult%10;
            } else {
                carry = 0;
            }

            StringBuilder sb = new StringBuilder(result);

            sb.insert(0, String.valueOf(intResult));

            result = sb.toString();

            k--;

        }


        return result;
    }
}
