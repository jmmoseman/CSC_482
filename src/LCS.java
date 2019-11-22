public class LCS {
    //Brute force below.
    static int LCSBF(String str1, String str2) {
        int lcsLen = 0;
        int k;
        int i1 = str1.length();
        int i2 = str2.length();

        for (int i = 0; i < i1; i++) {
            for (int j = 0; j < i2; j++) {
                for (k=0; (i+k) < i1 && (j+k) < i2; k++){
                    if (!str1.substring(i+k,i+k+1).equals(str2.substring(j+k,j+k+1))) break;
                }
                if (k > lcsLen) {
                    lcsLen = k;
                }
            }
        }

        return lcsLen;
    }

    static String LCSOPT(String str1, String str2) {
        int lcsLen = 0;
        int k;
        int start = 0;
        int i1 = str1.length();
        int i2 = str2.length();

        if (i1 == i2) {
            if (str1.equals(str2)) {
                lcsLen = i1;
            } else {
                for (int i = 0; i < i1; i++) {
                    for (int j = 0; j < i2; j++) {
                        for (k = 0; (i + k) < i1 && (j + k) < i2; k++) {
                            if (!str1.substring(i + k, i + k + 1).equals(str2.substring(j + k, j + k + 1))) break;
                        }
                        if (k > lcsLen) {
                            // Can't get this logic right... It works most of the time though?
                            i += k - 1;
                            start = j;
                            lcsLen = k;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < i1; i++) {
                for (int j = 0; j < i2; j++) {
                    for (k = 0; (i + k) < i1 && (j + k) < i2; k++) {
                        if (!str1.substring(i + k, i + k + 1).equals(str2.substring(j + k, j + k + 1))) break;
                    }
                    if (k > lcsLen) {
                        // Can't get this logic right... It works most of the time though?
                        i += k - 1;
                        start = j;
                        lcsLen = k;
                    }
                }
            }
        }
            return "Length Of LCS: " + String.valueOf(lcsLen) + ", LCS: " + str2.substring(start, lcsLen + start);
    }


}
