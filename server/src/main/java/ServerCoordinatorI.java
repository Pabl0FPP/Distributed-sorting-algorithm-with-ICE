import Demo.*;
import com.zeroc.Ice.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerCoordinatorI implements Demo.Coordinator {
    private List<WorkerPrx> workers = new ArrayList<>();
    private List<int[]> partialResults = new ArrayList<>();

    //registra el nodo
    @Override
    public void registerWorker(WorkerPrx worker, Current current) {
        workers.add(worker);
    }


    @Override
    public SortResult sortData(int[] data, Current current) {
        // Obtiene el número de servidores trabajadores registrados
        int numWorkers = workers.size();

        // Crea un pool de hilos de tamaño fijo igual al número de trabajadores
        ExecutorService executor = Executors.newFixedThreadPool(numWorkers);
        // Lista para almacenar los resultados futuros de los trabajadores
        List<Future<SortResult>> futures = new ArrayList<>();

        // Calcula el tamaño del chunk de datos para dividir sin duplicados
        int chunkSize = (data.length + numWorkers - 1) / numWorkers;
        // Itera sobre cada trabajador
        for (int i = 0; i < numWorkers; i++) {
            // Calcula el índice de inicio del chunk para este trabajador
            int start = i * chunkSize;
            // Calcula el índice de fin del chunk, sin exceder el límite del arreglo
            int end = Math.min(start + chunkSize, data.length);
            // Crea un nuevo arreglo con el chunk de datos para este trabajador
            int[] chunk = Arrays.copyOfRange(data, start, end);

            // Envía el chunk de datos al trabajador en un hilo separado
            Future<SortResult> future = executor.submit(() -> {
                // Ordena el chunk de datos utilizando el algoritmo BucketSort
                int[] sortedChunk = bucketSort(chunk);
                // Crea un objeto SortResult con los datos ordenados
                return new SortResult(sortedChunk);
            });
            // Agrega el resultado futuro a la lista de futures
            futures.add(future);
        }

        // Cierra el pool de hilos para evitar nuevas tareas
        executor.shutdown();

        // Itera sobre los resultados futuros de los trabajadores
        for (Future<SortResult> future : futures) {
            try {
                // Obtiene el resultado de cada trabajador
                receiveResult(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Combina los resultados parciales ordenados y retorna el resultado final
        return mergeResults();
    }


    //este metodo muestra en el server la parte de la data que le tocó a cada nodo
    //y ademas agrega el resultado al array de resultados parciales
    private void receiveResult(SortResult result) {
        System.out.println("resultados parciales del nodo: "+Arrays.toString(result.data));
        partialResults.add(result.data);
    }


    //Aqui es donde se llama el mergechunks para que de el resultado final que se muestra al cliente
    private SortResult mergeResults() {
        return mergeChunks(partialResults);
    }


    //este metodo une los resultados parciales de cada nodo
    private SortResult mergeChunks(List<int[]> chunks) {
        int totalLength = chunks.stream().mapToInt(chunk -> chunk.length).sum();
        int[] result = new int[totalLength];
        int index = 0;

        for (int[] chunk : chunks) {
            System.arraycopy(chunk, 0, result, index, chunk.length);
            index += chunk.length;
        }

        Arrays.parallelSort(result); // esta linea ordena los resultados parciales, para formar el resultado final

        return new SortResult(result);
    }



    private int[] bucketSort(int[] data) {

        int maxVal = Integer.MIN_VALUE;
        int minVal = Integer.MAX_VALUE;
        for (int item : data) {
            maxVal = Math.max(maxVal, item);
            minVal = Math.min(minVal, item);
        }

        int bucketRange = (maxVal - minVal) / data.length + 1;

        List<Integer>[] buckets = new List[data.length];
        for (int i = 0; i < data.length; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (int item : data) {
            int index = (item - minVal) / bucketRange;
            buckets[index].add(item);
        }

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