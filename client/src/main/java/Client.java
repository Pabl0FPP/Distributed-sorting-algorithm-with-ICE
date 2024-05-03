import com.zeroc.Ice.*;
import Demo.*;

import java.io.*;
import java.io.InputStream;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;

public class Client {

    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args, "config.client")) {
            Demo.CoordinatorPrx coordinator = Demo.CoordinatorPrx.checkedCast(
                    communicator.propertyToProxy("Coordinator.Proxy")).ice_twoway().ice_timeout(-1).ice_secure(false);
            if (coordinator == null) {
                throw new Error("Invalid proxy");
            }

            String filePath = "input/input.txt";

            int[] data = readArrayFromFile(filePath);

            if (data != null) {
                long startTime = System.currentTimeMillis();

                SortResult sortedData = coordinator.sortData(data);

                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;

                System.out.println("Tiempo de ejecución: " + elapsedTime + " milisegundos");

                // Imprimir el resultado ordenado
                System.out.println("arreglo ordenado:");
                for (int value : sortedData.data) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    private static int[] readArrayFromFile(String filePath) {
        List<Integer> dataList = new ArrayList<>();
        try (InputStream inputStream = Client.class.getClassLoader().getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: " + filePath);
            }
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split(" ");
                for (String token : tokens) {
                    dataList.add(Integer.parseInt(token));
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }

        int[] array = new int[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            array[i] = dataList.get(i);
        }
        return array;
    }

}