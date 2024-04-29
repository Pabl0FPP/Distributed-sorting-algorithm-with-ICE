
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
        if(data.length == 1){
            return data;
        }else {
            int mid = data.length/2;

            int[] part1 = partition(data, mid,0);
            int[] part2 = partition(data,(mid+1),(mid+1));

            int[] result1 = mergeSort(part1);
            int[] result2 = mergeSort(part2);

            return merge(result1,result2);
        }

    }

    private int[] partition(int[] a, int mid,int start){
        int[] part = new int[mid];
        for (int i = start; i < mid; i++) {
            part[i] = a[i];
        }
        return part;
    }

    private int[] merge(int[] part1,int[] part2){

        int[] mergedResult = new int[part1.length+part2.length];
        for (int i = 0; i < mergedResult.length; i++) {
            mergedResult[i] = Math.min(part1[i], part2[i]);
        }

        return mergedResult;
    }

}

