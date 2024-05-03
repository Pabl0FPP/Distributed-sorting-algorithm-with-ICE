import com.zeroc.Ice.*;
import Demo.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Esta clase contiene una serie de pruebas unitarias utilizando JUnit y Mockito
 * para verificar el correcto funcionamiento del sistema de ordenamiento distribuido.
 */
public class SortingSystemTests {
    private ServerCoordinatorI coordinator;
    private List<WorkerPrx> workers;

    /**
     * Configuración inicial.
     * Antes de ejecutar cada prueba, se ejecuta este método, el cual crea una instancia
     * del ServerCoordinatorI y registra 4 nodos trabajadores simulados utilizando
     * mocks de WorkerPrx.
     */
    @BeforeEach
    public void setUp() {
        coordinator = new ServerCoordinatorI();
        workers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            WorkerPrx worker = mock(WorkerPrx.class);
            workers.add(worker);
            coordinator.registerWorker(worker, null);
        }
    }

    /**
     * Verifica que los nodos trabajadores se registren correctamente en el coordinador.
     * Comprueba que la cantidad de nodos trabajadores registrados en el coordinador
     * coincida con la cantidad de mocks creados en la configuración inicial (4 en este caso).
     */
    @Test
    public void testWorkerRegistration() {
        assertEquals(4, coordinator.workers.size());
    }

    /**
     * Verifica que la división de datos en chunks se realice correctamente.
     * Genera un arreglo aleatorio de 100 elementos, llama al método sortData del
     * coordinador y verifica que la suma de los tamaños de los chunks sea igual
     * al tamaño del arreglo original.
     */
    @Test
    public void testDataDivision() {
        int[] data = generateRandomArray(100);
        SortResult result = coordinator.sortData(data, null);
        int totalLength = 0;
        for (int[] chunk : coordinator.partialResults) {
            totalLength += chunk.length;
        }
        assertEquals(data.length, totalLength);
    }

    /**
     * Verifica que cada chunk de datos se ordene correctamente.
     * Genera un arreglo aleatorio de 100 elementos, llama al método sortData del
     * coordinador y verifica que cada chunk de datos en los resultados parciales
     * esté ordenado ascendentemente.
     */
    @Test
    public void testChunkSorting() {
        int[] data = generateRandomArray(100);
        SortResult result = coordinator.sortData(data, null);
        for (int[] chunk : coordinator.partialResults) {
            assertArraySorted(chunk);
        }
    }

    /**
     * Verifica que la combinación de los resultados parciales se realice correctamente.
     * Genera un arreglo aleatorio de 1000 elementos, llama al método sortData del
     * coordinador y verifica que el resultado final (result.data) esté ordenado
     * ascendentemente.
     */
    @Test
    public void testResultMerging() {
        int[] data = generateRandomArray(1000);
        SortResult result = coordinator.sortData(data, null);
        assertArraySorted(result.data);
    }

    /**
     * Verifica que el sistema pueda manejar correctamente un arreglo vacío.
     * Llama al método sortData del coordinador con un arreglo vacío y verifica
     * que el resultado sea también un arreglo vacío.
     */
    @Test
    public void testEmptyArray() {
        int[] data = new int[0];
        SortResult result = coordinator.sortData(data, null);
        assertArrayEquals(new int[0], result.data);
    }


    /**
     * Verifica que el sistema pueda manejar múltiples solicitudes de ordenamiento
     * concurrentes.
     * Crea 10 hilos que realizan solicitudes de ordenamiento simultáneamente al
     * método sortData del coordinador con arreglos aleatorios de 1000 elementos.
     * Verifica que todos los resultados estén ordenados correctamente.
     */
    @Test
    public void testConcurrentRequests() throws InterruptedException, ExecutionException {
        int numRequests = 10;
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numRequests; i++) {
            Thread thread = new Thread(() -> {
                int[] data = generateRandomArray(1000);
                SortResult result = coordinator.sortData(data, null);
                assertArraySorted(result.data);
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    /**
     * Verifica que un arreglo esté ordenado ascendentemente.
     *
     * @param array El arreglo a verificar.
     */
    private void assertArraySorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            assertTrue(array[i] <= array[i + 1]);
        }
    }

    /**
     * Genera un arreglo aleatorio de tamaño size con valores entre 0 y 999.
     *
     * @param size El tamaño del arreglo a generar.
     * @return El arreglo aleatorio generado.
     */
    private int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);
        }
        return array;
    }
}