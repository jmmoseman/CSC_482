public class BubbleySort {

    void sort(long[] list) {
        /*make N passes through the list (N is length of the list) */
        for (int i = 0; i < list.length - 1; i++) {
            /* for index from 0 to N-1, compare item[index] to next item, swap if needed */
            for (int j = 0; j < list.length - 1 - i; j++) {
                if (list[j] > list[j + 1]) {
                    long tmp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = tmp;
                }
            }

        }
    }
}
