import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class MyBigIntegers {

    static String MBIPlus(String n1, String n2) {
        // initialize arrays for length of inputs
        int plusLen1 = n1.length();
        int plusLen2 = n2.length();

        int[] n1Array = new int[plusLen1];
        int[] n2Array = new int[plusLen2];

        // fill array with the number, using substring functions
        for (int i = plusLen1 - 1; i >= 0; i--) {
            n1Array[i] = Integer.parseInt(n1.substring(i, i + 1));
        }

        for (int i = plusLen2 - 1; i >= 0; i--) {
            n2Array[i] = Integer.parseInt(n2.substring(i, i + 1));
        }

        int carry = 0;
        int intResult = 0;
        StringBuilder sb = new StringBuilder();

        // "gradeschool" algorithm
        for (int i = plusLen1 - 1, k = plusLen2 - 1; i >= 0 || k >= 0; i--) {
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
                intResult = intResult % 10;
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

    // I was tempted to go through with writing my own version of the subtraction algorithm. But
    // I just found this instead... https://www.geeksforgeeks.org/difference-of-two-large-numbers/
    // (I'm a bit tired out and lazy atm...)
    // Maybe not comparable (to my mult and plus algorithms) as I used integer arrays instead of this method.

    // Returns true if str1 is smaller than str2.
    static boolean isSmaller(String str1, String str2)
    {
        // Calculate lengths of both string
        int n1 = str1.length(), n2 = str2.length();
        if (n1 < n2)
            return true;
        if (n2 < n1)
            return false;

        for (int i = 0; i < n1; i++)
            if (str1.charAt(i) < str2.charAt(i))
                return true;
            else if (str1.charAt(i) > str2.charAt(i))
                return false;

        return false;
    }

    // Function for find difference of larger numbers
    static String MBIDiff(String str1, String str2)
    {
        // Before proceeding further, make sure str1
        // is not smaller
        if (isSmaller(str1, str2))
        {
            String t = str1;
            str1 = str2;
            str2 = t;
        }

        // Take an empty string for storing result
        String str = "";

        // Calculate length of both string
        int n1 = str1.length(), n2 = str2.length();

        // Reverse both of strings
        str1 = new StringBuilder(str1).reverse().toString();
        str2 = new StringBuilder(str2).reverse().toString();

        int carry = 0;

        // Run loop till small string length
        // and subtract digit of str1 to str2
        for (int i = 0; i < n2; i++)
        {
            // Do school mathematics, compute difference of
            // current digits
            int sub = ((int)(str1.charAt(i)-'0') -
                    (int)(str2.charAt(i)-'0')-carry);

            // If subtraction is less then zero
            // we add then we add 10 into sub and
            // take carry as 1 for calculating next step
            if (sub < 0)
            {
                sub = sub + 10;
                carry = 1;
            }
            else
                carry = 0;

            str += (char)(sub + '0');
        }

        // subtract remaining digits of larger number
        for (int i = n2; i < n1; i++)
        {
            int sub = ((int)(str1.charAt(i) - '0') - carry);

            // if the sub value is -ve, then make it positive
            if (sub < 0)
            {
                sub = sub + 10;
                carry = 1;
            }
            else
                carry = 0;

            str += (char)(sub + '0');
        }

        // reverse resultant string
        return new StringBuilder(str).reverse().toString();
    }

    // Minor error on this (sometimes puts a leading 0 in front of result??). Think I have most of the kinks
    // Worked out though...
    // This thing is pretty dumb, but it does the logic (gradeschool multiplication) right I suppose...
    static String MBIMult(String n1, String n2) {
        // initialize arrays for length of inputs
        int plusLen1 = n1.length();
        int plusLen2 = n2.length();

        int[] n1Array = new int[plusLen1];
        int[] n2Array = new int[plusLen2];

        // fill array with the number, using substring functions
        for (int i = plusLen1 - 1; i >= 0; i--) {
            n1Array[i] = Integer.parseInt(n1.substring(i, i + 1));
        }


        for (int i = plusLen2 - 1; i >= 0; i--) {
            n2Array[i] = Integer.parseInt(n2.substring(i, i + 1));
        }

        //Make "plusLen1" always be the longer one.
        if (plusLen1 < plusLen2) {
            plusLen1 = n2.length();
            plusLen2 = n1.length();
        }

        int carry;
        int count = 0;
        int intResult = 0;
        // 2d array for the different "layers" of results.
        int[][] resultArray = new int[plusLen2][(plusLen1 + plusLen2)];
        StringBuilder sb = new StringBuilder();

        if (plusLen1 == 1 && plusLen2 == 1) {
            // Just multiply the single numbers.
            resultArray[0][0] = n1Array[0] * n2Array[0];
        } else {
            for (int i = plusLen2 - 1; i >= 0; i--) {
               carry = 0;
                if (i != plusLen2 - 1) {
                    // Increase to next result "layer"
                    count++;
                }
                for (int k = plusLen1 - 1; k >= 0; k--) {
                    //
                    if (n1.length() < n2.length()) {
                        //make sure the right array of numbers is being referenced.
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

                    // offset (I) so when adding later, the "right" values will be there. (0,00,000...)
                    resultArray[count][i + k + 1] = intResult;

                    //add last carry (if there) at "end" of "number".
                    if (k == 0 && carry > 0) {
                        resultArray[count][k + i] = carry;
                    }

                }
            }
        }

        count = plusLen2 - 1;
        int checkCount = count;
        carry = 0;
        int tmp = plusLen2 + plusLen1;
        int plusLen = tmp;
        //For storing intermediate results.
        int[] tmpResult = new int[tmp];

        while (plusLen > 0) {
            // Several conditions up top to handle small numbers.
            if (plusLen1 == 1 && plusLen2 == 1) {
                sb.insert(0, resultArray[0][0]);
                break;
            } else if (plusLen1 == 1 || plusLen2 == 1) {
                sb.insert(0, resultArray[0][plusLen - 1]);
                if (plusLen == 1) {
                    break;
                }
            } else if (count == checkCount) {
                //if it's not the initial pass, go to the tmp array (running result) instead.
                intResult = resultArray[count][plusLen - 1] + resultArray[count - 1][plusLen - 1] + carry;
            } else {
                intResult = tmpResult[plusLen - 1] + resultArray[count - 1][plusLen - 1] + carry;
            }

            if (intResult > 9) {
                carry = 1;
                intResult = intResult % 10;
            } else {
                carry = 0;
            }

            if (count != 1) {
                tmpResult[plusLen - 1] = intResult;
            } else {
                // if we're almost done with the "layers", put final result into this string builder.
                sb.insert(0, intResult);
            }

            plusLen--;
            if (plusLen <= 0) {
                //count down (and go to next "layer") once we've finished this one.
                count--;
                if (count != 0) {
                    // if we're not done, reset this counter.
                    plusLen = tmp;
                }
            }
        }

        return sb.toString();
    }

    //https://introcs.cs.princeton.edu/java/99crypto/Karatsuba.java.html

    static BigInteger MBIMultFast(BigInteger x, BigInteger y) {

        // cutoff to brute force
        int N = Math.max(x.bitLength(), y.bitLength());
        if (N <= 2000) { //This is probably best with EXTREMELY large numbers...
            return x.multiply(y);                // optimize this parameter.
        }

        // number of bits divided by 2, rounded up
        N = (N / 2) + (N % 2);

        // x = a + 2^N b,   y = c + 2^N d
        BigInteger b = x.shiftRight(N);
        BigInteger a = x.subtract(b.shiftLeft(N));
        BigInteger d = y.shiftRight(N);
        BigInteger c = y.subtract(d.shiftLeft(N));

        // compute sub-expressions
        BigInteger ac = MBIMultFast(a, c);
        BigInteger bd = MBIMultFast(b, d);
        BigInteger abcd = MBIMultFast(a.add(b), c.add(d));

        return ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(N)).add(bd.shiftLeft(2 * N));
    }

    //https://www.geeksforgeeks.org/program-for-nth-fibonacci-number/ this site again...

    static long fibFormula(int n)
    {

        double phi = (1 + Math.sqrt(5)) / 2;
        return (long)Math.round(Math.pow(phi, n)
                / Math.sqrt(5));
    }

    // Same logic as above (???). But results are slightly different.
    // This gave the same results too, lol: https://stackoverflow.com/questions/35822235/fibonacci-sequence-for-n-46-java
    // Code there to handle smaller numbers better. Who cares about them......

    static BigDecimal fibFormulaBig(int n)
    {
        BigDecimal phi = new BigDecimal((1 + Math.sqrt(5)) / 2);
        BigDecimal sqrt5 = new BigDecimal(Math.sqrt(5));

        return phi.pow(n).divide(sqrt5,0,RoundingMode.FLOOR);
    }

    //https://www.geeksforgeeks.org/large-fibonacci-numbers-java/ Not that this is terribly complicated though...

    static BigInteger fibLoopBig(int n)
    {
        BigInteger a = BigInteger.valueOf(0);
        BigInteger b = BigInteger.valueOf(1);
        BigInteger c = BigInteger.valueOf(1);
        for (int j=2 ; j<=n ; j++)
        {
            c =  a.add(b);
            a = b;
            b = c;
        }

        return (a);
    }

    // https://www.nayuki.io/res/fast-fibonacci-algorithms/FastFibonacci.java
    // https://www.nayuki.io/page/fast-fibonacci-algorithms
    // Trying "doubling method" below too for fun!

     /* Fast matrix method. Easy to describe, but has a constant factor slowdown compared to doubling method.
            * [1 1]^n   [F(n+1) F(n)  ]
            * [1 0]   = [F(n)   F(n-1)].
            */
    static BigInteger fibMatrixBig(int n) {
        BigInteger[] matrix = {BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO};
        return matrixPow(matrix, n)[1];
    }

    // Computes the power of a matrix. The matrix is packed in row-major order.
    static BigInteger[] matrixPow(BigInteger[] matrix, int n) {
        if (n < 0)
            throw new IllegalArgumentException();
        BigInteger[] result = {BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE};
        while (n != 0) {  // Exponentiation by squaring
            if (n % 2 != 0)
                result = matrixMultiply(result, matrix);
            n /= 2;
            matrix = matrixMultiply(matrix, matrix);
        }
        return result;
    }

    // Multiplies two matrices.
    static BigInteger[] matrixMultiply(BigInteger[] x, BigInteger[] y) {
        return new BigInteger[] {
                multiply(x[0], y[0]).add(multiply(x[1], y[2])),
                multiply(x[0], y[1]).add(multiply(x[1], y[3])),
                multiply(x[2], y[0]).add(multiply(x[3], y[2])),
                multiply(x[2], y[1]).add(multiply(x[3], y[3]))
        };
    }

    static BigInteger fastFibonacciDoubling(int n) {
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;
        int m = 0;
        for (int bit = Integer.highestOneBit(n); bit != 0; bit >>>= 1) {
            // Loop invariant: a = F(m), b = F(m+1)
            assert a.equals(slowFibonacci(m));
            assert b.equals(slowFibonacci(m+1));

            // Double it
            BigInteger d = multiply(a, b.shiftLeft(1).subtract(a));
            BigInteger e = multiply(a, a).add(multiply(b, b));
            a = d;
            b = e;
            m *= 2;
            assert a.equals(slowFibonacci(m));
            assert b.equals(slowFibonacci(m+1));

            // Advance by one conditionally
            if ((n & bit) != 0) {
                BigInteger c = a.add(b);
                a = b;
                b = c;
                m++;
                assert a.equals(slowFibonacci(m));
                assert b.equals(slowFibonacci(m+1));
            }
        }
        return a;
    }

    static BigInteger slowFibonacci(int n) {
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;
        for (int i = 0; i < n; i++) {
            BigInteger c = a.add(b);
            a = b;
            b = c;
        }
        return a;
    }

    // Multiplies two BigIntegers. This function makes it easy to swap in a faster algorithm like Karatsuba multiplication.
    static BigInteger multiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    static BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

}
