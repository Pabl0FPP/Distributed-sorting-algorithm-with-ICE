/*
import Demo.*;
import com.zeroc.Ice.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkerI implements Demo.Worker {

    //está clase actualmente no se está usando ya que en el Servercoordinator definí el bucketsort



    @Override
    public SortResult sort(int[] data, Current current) {
        int[] sortedData = bucketSort(data);
        return null;
    }

    private int[] bucketSort(int[] data) {
        // Encuentra el valor máximo y mínimo en el arreglo de entrada
        int maxVal = Integer.MIN_VALUE;
        int minVal = Integer.MAX_VALUE;
        for (int item : data) {
            maxVal = Math.max(maxVal, item);
            minVal = Math.min(minVal, item);
        }

        // Calcula el rango de cada bucket
        int bucketRange = (maxVal - minVal) / data.length + 1;

        // Crea un arreglo de listas para las cubetas
        List<Integer>[] buckets = new List[data.length];
        for (int i = 0; i < data.length; i++) {
            buckets[i] = new ArrayList<>();
        }

        // Coloca cada elemento en su respectiva bucket
        for (int item : data) {
            int index = (item - minVal) / bucketRange;
            buckets[index].add(item);
        }

        // Ordena cada bucket y combina los resultados
        int index = 0;
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
            for (int item : bucket) {
                data[index++] = item;
            }
        }

        return data;
    }
}

 */