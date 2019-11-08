import java.util.Arrays;

public class MyBigIntegers {

    static String MBIPlus(String n1, String n2) {
        // initialize arrays for length of inputs
        int plusLen1 = n1.length();
        int plusLen2 = n2.length();

        int[] n1Array = new int[plusLen1];
        int[] n2Array = new int[plusLen2];

        // fill array with the number, using substring functions
        for (int i = plusLen1-1; i >= 0; i--) {
            n1Array[i] = Integer.parseInt(n1.substring(i, i+1));
        }

        for (int i = plusLen2-1; i >= 0; i--) {
            n2Array[i] = Integer.parseInt(n2.substring(i, i+1));
        }

        int carry = 0;
        int intResult = 0;
        StringBuilder sb = new StringBuilder();

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

            sb.insert(0, String.valueOf(intResult));

            k--;

        }

        return sb.toString();
    }

    // Minor error on this (sometimes puts a leading 0 in front of result??). Think I have most of the kinks
    // Worked out though...
    static String MBIMult(String n1, String n2) {
        // initialize arrays for length of inputs

        int plusLen1 = n1.length();
        int plusLen2 = n2.length();

        int[] n1Array = new int[plusLen1];
        int[] n2Array = new int[plusLen2];

        // fill array with the number, using substring functions
        for (int i = plusLen1-1; i >= 0; i--) {
            n1Array[i] = Integer.parseInt(n1.substring(i, i+1));
        }

        for (int i = plusLen2-1; i >= 0; i--) {
            n2Array[i] = Integer.parseInt(n2.substring(i, i+1));
        }

        if (plusLen1 < plusLen2) {
            plusLen1 = n2.length();
            plusLen2 = n1.length();
        }

        int carry;
        int count = 0;
        int intResult = 0;
        int[][] resultArray = new int[plusLen2][(plusLen1 + plusLen2)];
        StringBuilder sb = new StringBuilder();

        if (plusLen1 == 1 && plusLen2 == 1) {
            resultArray[0][0] = n1Array[0] * n2Array[0];
        } else {
            for (int i = plusLen2 - 1; i >= 0; i--) {
                int j = i;
                int c = 0;
                while (j > 0) {
                    resultArray[count][c] = 0;
                 j--;
                 c++;
                }
                carry = 0;
                if (i != plusLen2 - 1) {
                    count++;
                }
                for (int k = plusLen1 - 1; k >= 0; k--) {
                    if (n1.length() < n2.length()) {
                        intResult = carry + (n1Array[i] * n2Array[k]);
                    } else {
                        intResult = carry + (n1Array[k] * n2Array[i]);
                    }
                    if (intResult > 9) {
                        carry = (intResult - intResult % 10) / 10;
                        intResult = intResult % 10;
                    } else {
                        carry = 0;
                    }

                    resultArray[count][c+k+1] = intResult;

                    if (k == 0 && carry > 0) {
                        resultArray[count][k+c] = carry;
                    }

                }
            }
        }

          count = plusLen2-1;
          int checkCount = count;
          carry = 0;
          int tmp = plusLen2 + plusLen1;
          int plusLen = tmp;
          int[] tmpResult = new int[tmp];

      while(plusLen > 0) {

          if (plusLen1 == 1 && plusLen2 == 1) {
              sb.insert(0, resultArray[0][0]);
              break;
          } else if (plusLen1 == 1 || plusLen2 == 1) {
              sb.insert(0,resultArray[0][plusLen-1]);
              if (plusLen == 1) {
                  break;
              }
          }
          else if (count == checkCount) {
              intResult = resultArray[count][plusLen - 1] + resultArray[count - 1][plusLen - 1] + carry;
          } else {
              intResult = tmpResult[plusLen-1] + resultArray[count - 1][plusLen - 1] + carry;
          }

          if (intResult > 9) {
              carry = 1;
              intResult = intResult%10;
          } else {
              carry = 0;
          }

          if (count != 1) {
              tmpResult[plusLen - 1] = intResult;
          } else {
              sb.insert(0, intResult);
          }

          plusLen--;
          if (plusLen <= 0) {
              count--;
              if (count != 0) {
                  plusLen = tmp;
              }
          }
      }


        return sb.toString();
    }
}
