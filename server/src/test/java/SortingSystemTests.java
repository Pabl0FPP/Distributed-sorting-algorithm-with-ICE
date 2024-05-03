import com.zeroc.Ice.*;
import Demo.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class SortingSystemTests {
    private ServerCoordinatorI coordinator;
    private List<WorkerPrx> workers;


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

    @Test
    public void testWorkerRegistration() {
        assertEquals(4, coordinator.workers.size());
    }

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

    @Test
    public void testChunkSorting() {
        int[] data = generateRandomArray(100);
        SortResult result = coordinator.sortData(data, null);
        for (int[] chunk : coordinator.partialResults) {
            assertArraySorted(chunk);
        }
    }

    @Test
    public void testResultMerging() {
        int[] data = generateRandomArray(1000);
        SortResult result = coordinator.sortData(data, null);
        assertArraySorted(result.data);
    }

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

    private void assertArraySorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            assertTrue(array[i] <= array[i + 1]);
        }
    }

    private int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);
        }
        return array;
    }


}