
import Demo.*;
import com.zeroc.Ice.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkerI implements Demo.Worker {

    //está clase actualmente no se está usando ya que en el Servercoordinator definí el bucketsort



    @Override
    public int[] sort(int[] data, Current current) {
        return mergeSort(data);
    }

    private int[] mergeSort(int[] data) {
        if (data.length <= 1) {
            return data;
        } else {
            int mid = data.length / 2;

            int[] part1 = partition(data, mid, 0);
            int[] part2 = partition(data, data.length - mid, mid);

            int[] result1 = mergeSort(part1);
            int[] result2 = mergeSort(part2);

            return merge(result1, result2);
        }
    }

    private int[] partition(int[] a, int size, int start) {
        int[] part = new int[size];
        for (int i = 0; i < size; i++) {
            part[i] = a[start + i];
        }
        return part;
    }

    private int[] merge(int[] part1, int[] part2) {
        int[] mergedResult = new int[part1.length + part2.length];
        int i = 0, j = 0, k = 0;

        while (i < part1.length && j < part2.length) {
            if (part1[i] < part2[j]) {
                mergedResult[k++] = part1[i++];
            } else {
                mergedResult[k++] = part2[j++];
            }
        }

        while (i < part1.length) {
            mergedResult[k++] = part1[i++];
        }

        while (j < part2.length) {
            mergedResult[k++] = part2[j++];
        }

        return mergedResult;
    }


}

